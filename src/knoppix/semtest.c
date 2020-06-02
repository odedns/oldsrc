
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

#include "mutex.h"



void main(void)
{
	int semid;
	pid_t pid;
	
	puts("mutex ..\n");
	semid = mutex_create();	
	printf("key = %d\n",semid);
	if(0 > (pid = fork())) {
		perror("fork");
		exit(1);
	}
	
	if(pid == 0) {
		sleep(10);
		puts("in child ");
		mutex_lock(semid);
		puts("child got lock");
		mutex_unlock(semid);
		puts("child unlock");
	} else {
		puts("in parent");
		mutex_lock(semid);
		puts("paren aquired lock");
		mutex_unlock(semid);
		puts("parent unlock");


	}

	
	/* mutex_destroy(semid); */

}
