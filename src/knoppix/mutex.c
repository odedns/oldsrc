/**
 * Name: Oded Nissan 059183384
 * This file contains mutex related functions
 * to support locking of critical sections.
 *
 */ 

#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

#include "mutex.h"

/**
 * generate a key for the semaphore.
 */ 
key_t generate_key()
{
	const char *path = "/tmp";
	int id = 998;
	key_t key;

	if(-1 == (key = ftok(path,id))) {
		perror("ftok");
		return(key);
	}
	return(key);
	
}

/**
 * create a mutex by creating a binary semaphore.
 * returns the semaphore id.
 */ 
int mutex_create()
{
	int rv;
	int semid;
	key_t key;
	struct sembuf sem;

	key = generate_key();
	semid = semget(key,1,IPC_CREAT| 0666);
	if(-1 == semid) {
		perror("semget");
	}
	sem.sem_num = 0;
	sem.sem_op = 1;
	sem.sem_flg = 0;
	rv = semop(semid,&sem,1);
	if(-1 == rv) {
		perror("semop");
	}
	return(semid);
}

/**
 * lock the mutex specified by semid.
 * this is done by decrementing the binary semaphore.
 * returns the return code.
 */ 
int mutex_lock(int semid)
{
	int rv;
	struct sembuf sem;
	sem.sem_num = 0;
	sem.sem_op = -1;
	sem.sem_flg = 0;
	rv = semop(semid,&sem,1);
	
	return(rv);
		
}

/**
 * unlock the mutex specified by semid.
 * This is done by decrementing the value of the binary 
 * semaphore.
 * returns the return code.
 */ 
int mutex_unlock(int semid)
{
	int rv;
	struct sembuf sem;
	sem.sem_num = 0;
	sem.sem_op = 1;
	sem.sem_flg = 0;
	rv = semop(semid,&sem,1);
	
	return(rv);

}

/**
 * destroy the binary semaphore
 * in order to clean up.
 */ 
int mutex_destroy(int semid)
{
	int rv;

	rv = semctl(semid,1,IPC_RMID);
	
	return(rv);
}

