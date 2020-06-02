#include <stdio.h>
#include <unistd.h>
#include <signal.h>

#define MAXLINE (120)

static void sig_pipe(int);

void pipeCmd(char *proc, char **args,char **cmds);

void main(void)
{
	char *cmds[] = { "open localhost\n","user odedn odedn0012\n",
		"dir\n",NULL};
		/* commands array */
	char *args[] = {"ftp","-n",NULL };
	/* commands line parameters */

	pipeCmd("/bin/ftp",args,cmds);

}

static void sig_pipe(int signo)
{
	printf("SIGPIPE caught\n");
	exit(1);
}


void pipeCmd(char *proc, char **args,char **cmds)
{
	int	n, fd1[2], fd2[2];
	int i = 0;
	pid_t	pid;
	char	line[MAXLINE];

	if (signal(SIGPIPE, sig_pipe) == SIG_ERR)
		perror("signal error");

	if (pipe(fd1) < 0 || pipe(fd2) < 0)
		perror("pipe error");

	if ( (pid = fork()) < 0) {
		perror("fork error");
		exit(1);
	}

	if (pid > 0) {
		/* parent */
		close(fd1[0]);
		close(fd2[1]);

		sleep(2);

		/*
		 * loop on commands array and
		 * write input for child process
		 * through pipe.
		 */
		while(cmds[i] != NULL) {
			printf("writing cmd: %s\n",cmds[i]);
			n = write(fd1[1],cmds[i],strlen(cmds[i]));
			if(n < 0) {
				perror("write");
			}
			++i;
		}
		close(fd1[1]);

		
		printf("reading child output\n");
		/*
		 * read child process output
		 */
		n = read(fd2[0],line,MAXLINE);
		while(n > 0) {
			printf("got : %s\n",line);
			n = read(fd2[0],line,MAXLINE);
		}
		close(fd2[0]);
	} else {
		/* child */
		close(fd1[1]);
		close(fd2[0]);
		if (fd1[0] != STDIN_FILENO) {
			if (dup2(fd1[0], STDIN_FILENO) != STDIN_FILENO)
				perror("dup2 error to stdin");
			close(fd1[0]);
		}
		if (fd2[1] != STDOUT_FILENO) {
			if (dup2(fd2[1], STDOUT_FILENO) != STDOUT_FILENO)
				perror("dup2 error to stdout");
			close(fd2[1]);
		}
		printf("executing program");
		if (execv(proc,args) < 0) {
			perror("execl error");
		}

	}
		

}
