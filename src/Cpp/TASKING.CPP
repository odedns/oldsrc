//_A C++ Multitasking Kernel_
// by Tom Green

//[LISTING ONE]


/********************************************/
/*		TASK.HPP                    */
/*		Tom Green                   */
/********************************************/
/* this file contains classes needed to use multi-tasking kernel */
/* include this file in your source code and then link with */
/* task.cpp and timer.asm */

/* this is used when a task is initialized */
/* this is a pointer to a function */
typedef void (*func_ptr)(void);
/* this is used for interrupt handler to call old interrupt handler */
/* this is a far pointer to a function */
typedef void (far *far_func_ptr)(void);

/* this is how the registers will look on the stack for a task */
/* after they have been saved for a task switch. the sp and ss */
/* registers will point to this when a task is started from the */
/* interrupt handler or save_image */

typedef struct task_image{
	unsigned int bp;
	unsigned int di;
	unsigned int si;
	unsigned int ds;
	unsigned int es;
	unsigned int dx;
	unsigned int cx;
	unsigned int bx;
	unsigned int ax;
	unsigned int ip;
	unsigned int cs;
	unsigned int flags;
}task_image;

/* a task object. contains information needed by task_control object */
/* to do task switching and a pointer to the task's workspace (stack) */

class task{
	private:
		friend class task_control;  // task_control object needs access
		friend class signal;        // signal needs access to next_task
		task_image far *stack_ptr;  // task stack ("image") pointer
		unsigned char task_state;   // task state flag
		unsigned char *workspace;   // address of allocated task stack
		task *next_task;            // pointer to next task in queue
	public:
		task(func_ptr func,unsigned int workspace_size); // constructor
		~task();											// destructor
};


/* this is a queue for tasks */
/* it is called signal so user can define a signal for task communication */

class signal{
	private:
		friend class task_control;   // task_control needs access
		task *head;
		task *tail;
		task *get_task_q(void);	     // get next task off of queue
		void put_task_q(task *tptr); // append task to queue
	public:
		signal(void){head=tail=0;};  // constructor
};




/* task_control object */
/* routines and methods to interface with and control tasks */
/* this object will initialize and restore interrupt vectors, */
/* keep track of timer ticks, and switch execution between the */
/* task objects */

class task_control{
	private:
		signal ready_q;			// queue of tasks ready to run
		task *current_task;		// current active task
		task_image far *old_stack_ptr;	// return to this stack when done
		unsigned int task_running;	// task switching enabled flag
		unsigned long timer_ticks;	// 18.2 ticks/second
		unsigned int task_lock;		// lock out task switching
		task_image far *task_switch(task_image far *stk_ptr,
										unsigned int flag,
										signal *sig);
	public:
		task_control(void);                // constructor
		void add_new_task(task *new_task); // add new task object to ready q
		void start_tasks(void);	           // start switching tasks on ready_q
		void stop_tasks(void){task_running=0;};
		unsigned long get_timer_ticks(void){return(timer_ticks);};
		void lock(void){task_lock=1;};	    // current task can not be switched
		void unlock(void){task_lock=0;};    // allow task switching
		void send(signal *sig);             // put task from sig q on ready q
		void wait(signal *sig);             // put task on sig q
		void block(void);                   // task allows next to run
};

//[LISTING TWO]

/********************************************/
/*		TASK.CPP                    */
/*		by Tom Green	            */					
/********************************************/

/* this file implements the methods used by task_control and task */
/* objects */

#include <stdio.h>
#include <stdlib.h>
#include <dos.h>
#include <int.h>
#include "task.hpp"

/* task states */
#define	TASK_INACTIVE	0
#define	TASK_ACTIVE 	1
#define TASK_READY	2
#define	TASK_WAITING	3
#define TASK_ERROR	0xff

/* flags for interface routines */
#define	TASK_TIMER_INTR	0
#define	TASK_SEND		1
#define	TASK_WAIT		2
#define TASK_BLOCK		3

/* system timer interrupt or "timer tick" */
#define TIMER_INT		8

/* routines we need from timer.asm */
unsigned int getCS(void);
extern void timer_handler(void);
extern void save_image(unsigned int flag,signal *sig);

/* global for timer_handler to call old interrupt routine */
far_func_ptr old_timer_handler;

/* this is really ugly. */
/* when constructor for task_control object is called we save the */
/* this pointer for task switch routine to call our task_control object */
/* task_switch. this means we can only have 1 task_control object. sorry */
task_control *gl_tptr;

/* constructor for a new task. workspace will be the stack space for */
/* the task. when the timer interrupt happens the tasks "image" */
/* is saved on the stack for use later and the task_image *stack_ptr */
/* will point to this image */

task::task(func_ptr func,unsigned int workspace_size)
{
	task_image *ptr;
	
	/* get stack or "workspace" for task */
	if((workspace=(unsigned char *)malloc(workspace_size))==NULL){
		task_state=TASK_ERROR;	// do not let this one run
		return;
	}

	/* now we must set up the starting "image" of the task registers */
	/* ptr will point to the register image to begin task */
	ptr=(task_image *)(workspace+workspace_size-sizeof(task_image));

	/* now save the pointer to the register image */
	stack_ptr=MK_FP(getDS(),(unsigned int)ptr);

	ptr->ip=(unsigned int)func; // offset of pointer to task code
	ptr->cs=getCS();            // segment of pointer to task, compiler bug
	ptr->ds=getDS();
	ptr->flags=0x200;	    // flags, interrupts on
	task_state=TASK_INACTIVE;   // task is inactive
	next_task=0;

/* destructor for a task object */

task::~task(void)
{
	free(workspace);
}

/* get the next task off of a task queue */

task *signal::get_task_q(void)
{
	task *temp;
	
	temp=head;
	if(head)
		head=head->next_task;
	return(temp);
}

/* append a task to the end of a task queue */

void signal::put_task_q(task *tptr)
{
	if(head)
		tail->next_task=tptr;
	else
		head=tptr;
	tail=tptr;
	tptr->next_task=0;
}

/* constructor for task_control */
/* inits private stuff for task control */

task_control::task_control(void)
{
	gl_tptr=this;
	task_running=0;
	current_task=0;
	timer_ticks=0L;
	task_lock=0;
}

/* adds a task to the task ready_q */
/* call this routine after creating a task object */

void task_control::add_new_task(task *new_task)
{
	if(new_task->task_state!=TASK_ERROR){
		new_task->task_state=TASK_READY;
		ready_q.put_task_q(new_task);
	}
}

/* call to start up tasks after you have created some */
/* and added them to the ready_q */

void task_control::start_tasks(void)
{
	unsigned int offset,segment;

	task_running=1;
	/* get address of old timer interrupt handler */
	int_getvector(TIMER_INT,&offset,&segment);
	old_timer_handler=(far_func_ptr)(MK_FP(segment,offset));
	/* set up our new timer interrupt handler */
	int_setvector(TIMER_INT,(unsigned int)timer_handler,getCS());
	/* tasks will now start running */
	while(task_running)
		; // do nothing, trick to wait for tasks to start up
	/* falls through to here when multi-tasking is turned off */
}

/* gets the next task off of sig queue and puts it */
/* on the ready_q. this suspends operation of the calling */
/* task which is also put on the ready queue */

void task_control::send(signal *sig)
{
	save_image(TASK_SEND,sig);
}

/* puts the calling task on the sig queue to wait for a signal */

void task_control::wait(signal *sig)
{
	save_image(TASK_WAIT,sig);
}

/* this causes the current task to be placed on the ready queue */
/* and a switch to the next task on the ready_q */

void task_control::block(void)
{
	save_image(TASK_BLOCK,(signal *)0);
}

/* this routine is called to do a task switch. it is */
/* passed a task_image far * to the current stack or task "image". */
/* also pass a flag (described above) and a signal pointer if needed. */
/* a task_image * to the "image" of the next task is returned */

task_image far *task_control::task_switch(task_image far *stk_ptr,
												signal *sig)
{
	task_image far *temp;
	task *tptr;

	if(flag==TASK_TIMER_INTR)  // increment clock if it is a timer interrupt
		timer_ticks++;

	/* this saves a pointer to stack when we first start multi-tasking */
	/* allows us to return to where start_tasks was called */
	if(!current_task){  // no current task so save state for restoring
		old_stack_ptr=stk_ptr;	// save stack pointer
		current_task=ready_q.get_task_q(); // set up a current task
	}

	/* we have an active task, so do task switch if we can */
	if(current_task->task_state==TASK_ACTIVE){
		current_task->stack_ptr=stk_ptr;  // save stack pointer
		current_task->task_state=TASK_READY; // task is ready to go
		/* do not allow task switching if tasks are locked and */
		/* it is timer interrupt */
		if(!task_lock || flag!=TASK_TIMER_INTR){
			/* check and see what caused task_switch to be called */
			switch(flag){
				case TASK_WAIT:
					current_task->task_state=TASK_WAITING;
					sig->put_task_q(current_task);
					break;
				case TASK_SEND:
					if((tptr=sig->get_task_q())!=0)
						ready_q.put_task_q(tptr);
					// fall through
				case TASK_BLOCK:
				case TASK_TIMER_INTR:
					current_task->task_state=TASK_READY;
					/* put old task on ready queue */
					ready_q.put_task_q(current_task);
					break;
			}
			/* get next task to go */
			current_task=ready_q.get_task_q();
		}
	}

	/* if we are still multi-tasking, get task ready to run */
	if(task_running){
		current_task->task_state=TASK_ACTIVE;
		temp=current_task->stack_ptr;	// get stack pointer of task
	}
	/* multi-tasking has stopped, get ready to return where we started */
	else{					// someone called stop_tasks
		int_setvector(TIMER_INT,FP_OFF(old_timer_handler),
						FP_SEG(old_timer_handler));
		temp=old_stack_ptr;	// get back original stack
	}
	/* return far pointer to stack_image to do task switch */
	return(temp);
}

//[LISTING THREE]

;*****************************************************************************
;	TIMER.ASM
;	by Tom Green
;	Timer interrupt handler
;	Timer interrupt handler calls original handler first and then calls the
;	task_control object task switcher. a pointer to the stack "image"
;	of the new task is returned by the task switcher.
;	getCS
;	returns current code segment
;	save_image
;	saves "image" of task as if interrupt had happened and then calls the
;	task_control object task switcher. a pointer to the stack "image"
;	of the new task is returned by the task switcher.
;*****************************************************************************

	.MODEL SMALL
	.8086

	.DATA

extrn _old_timer_handler:dword
extrn _gl_tptr:word
extrn __task_control_task_switch:near

	.CODE

;*****************************************************************************
;	unsigned int getCS(void) - returns current code segment.
;	this is needed because of compiler bug. when a function is cast
;	to a far function, and you try to get the segment, DS is returned
;	instead of CS.
;*****************************************************************************
_getCS	proc	near
public	_getCS
		mov		ax,cs
		ret
_getCS	endp
		
;*****************************************************************************
;	timer_handler - this replaces the MS-DOS timer tick interrupt (8H).
;	this routine saves everything on stack, calls original timer interrupt
;	handler, and then calls task_control object task switcher.
;*****************************************************************************
_timer_handler	proc	near
public	_timer_handler
		push	ax				;save everything
		push	bx
		push	cx
		push	dx
		push	es
		push	ds
		push	si
		push	di
		push	bp
		mov		bp,dgroup
		mov	ds,bp	        ;get our data segment back
		pushf
		call	dword ptr dgroup:_old_timer_handler  ;call original handler
		sti
		xor		dx,dx
		mov		ax,ss
		mov		bx,sp
		push	dx	        ;push 0 for last 2 parameters
		push	dx		;meaning timer interrupt
		push	ax
		push	bx
		mov		dx,_gl_tptr   ;push hidden pointer for C++ object
		push	dx
;stack is now set up for call to task_control object task_switch
		cli                   ;turn off interrupts for task switch
		call	__task_control_task_switch
;no need to clean up the stack because it will change
		sti
		mov		ss,dx	;new ss returned in dx
		mov		sp,ax	;new sp returned in ax
		pop		bp	;restore registers
		pop		di
		pop		si
		pop		ds
		pop		es
		pop		dx
		pop		cx
		pop		bx
		pop		ax
		iret
_timer_handler	endp

;*****************************************************************************
;	void save_image(unsigned int flag,signal *sig) - send, wait, block
;	etc. all call through here to save the "image" of the task. this
;	code simulates what will happen with an interrupt by saving the task
;	image on the stack. the flag passed is passed on to the task_control
;	object task switcher so it knows if it was called by the timer
;	interrupt handler, send, wait, block, etc. the second parameter
;	is a signal pointer which is used by send and wait and is passed
;	through to the task switcher.
;*****************************************************************************
_save_image		proc	near
public	_save_image
;since this is a C call we can destroy some registers (ax bx cx dx),
;so now we will set up the stack as if an interrupt call had happened.
;leave parameters on stack, because calling routine will adjust on
;return. bx and cx will have the parameters that were passed.
		pop		ax	;get return address offset on stack
		pop		bx	;get first parameter off stack
		pop		cx	;get second parameter off stack
		push	cx		;put them back on stack
		push	bx
		pushf			;save flags for iret
		mov		dx,cs	;get code segment
		push	dx		;save code segment for return address
		push	ax		;push saved return address offset
		push	ax		;save everything
		push	bx
		push	cx
		push	dx
		push	es
		push	ds
		push	si
		push	di
		push	bp
		sti
		mov		ax,sp	;stack pointer parameter
		push	cx		;second parameter passed
		push	bx		;first parameter passed
		mov		bx,ss
		push	bx		;far pointer to stack, parameter passed
		push	ax
		mov		ax,_gl_tptr  ;push hidden pointer for C++ object
		push	ax
;stack is now set up for call to task_control object task_switch
		cli			  ;turn off interrupts for task switch
		call	__task_control_task_switch
;no need to clean up the stack because it will change
		sti
		mov		ss,dx		;new ss returned in dx
		mov		sp,ax		;new sp returned in ax
		pop		bp		;restore registers
		pop		di
		pop		si
		pop		ds
		pop		es
		pop		dx
		pop		cx
		pop		bx
		pop		ax
		iret
_save_image		endp

		end

[LISTING FOUR]

/********************************************/
/*		TASKDEMO.HPP                */
/*		by Tom Green                */
/********************************************/

/* this file is a demonstration of how to use the C++ multi-tasking */
/* kernel. 5 tasks are run and the various means of task switching */
/* and communication are shown */

/* you must have the Zortech C++ compiler version 1.5 and linker and */
/* Microsoft MASM 5.xx to compile this code. */
/* type "ztc taskdemo task timer" and the ztc.com will take */
/* care of compiling, assembling, and linking */

#include <stdio.h>
#include <disp.h>
#include "task.hpp"

void task0(void);
void task1(void);
void task2(void);
void task3(void);
void task4(void);

/* our task_control object (just 1 please) */
task_control tasker;

void main(void)
{
	/* task objects */
	task t0((func_ptr)task0,1024);
	task t1((func_ptr)task1,1024);
	task t2((func_ptr)task2,1024);
	task t3((func_ptr)task3,1024);
	task t4((func_ptr)task4,1024);

	/* add task objects to our task_control object ready q */
	tasker.add_new_task(&t0);
	tasker.add_new_task(&t1);
	tasker.add_new_task(&t2);
	tasker.add_new_task(&t3);
	tasker.add_new_task(&t4);

	/* use zortech display package */
	disp_open();	
	disp_move(0,0);
	disp_eeop();

	/* start tasks up and wait for them to finish */
	tasker.start_tasks();

	disp_move(0,0);
	disp_eeop();
	disp_close();
}

static unsigned long counter[]={0L,0L,0L,0L,0L};
static signal sig;

/* task 0 prints the values of the counters for the other 4 tasks. */
/* lock is used to prevent task switching while the screen is being */
/* updated. when the task is finished, block is called to transfer */
/* control to the next task on the ready q */

void task0(void)
{
	while(1){
		/* disable task switching */
		tasker.lock();
		disp_move(5,10);
		disp_printf("Task 1  %lx",counter[1]);
		disp_move(5,50);
		disp_printf("Task 2  %lx",counter[2]);
		disp_move(15,10);
		disp_printf("Task 3  %lx",counter[3]);
		disp_move(15,50);
		disp_printf("Task 4  %lx",counter[4]);
		/* if key pressed then stop the kernel and return */
		if(kbhit())
			tasker.stop_tasks();
		/* re-enable task switching */
		tasker.unlock();
		/* let next task run */
		tasker.block();
	}
}

/* tasks 1 and 2 just update counters. these tasks will run until */
/* a timer interrupt occurs, so they get a very large chunk of time */
/* to run, so the counters increase rapidly */

void task1(void)
{
	while(1){
		counter[1]++;
	}
}

void task2(void)
{
	while(1){
		counter[2]++;
	}
}

/* task 3 waits for a signal from task 4 each time the counter is */
/* incremented. when a task waits, it is put on a signal q and the */
/* next task on the ready q is run. this means task 3 and 4 counters */
/* will increment very slowly. in task 4 when a signal is sent, the */
/* task signal q is checked for a task to put on the ready q. the task */
/* sending the signal is then placed on the ready q */

void task3(void)
{
	while(1){
		counter[3]++;
		/* wait for a signal from task 4 */
		tasker.wait(&sig);
	}
}

void task4(void)
{
	while(1){
		counter[4]++;
		/* send signal to task 3 */
		tasker.send(&sig);
	}
}



