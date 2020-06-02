
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>


int fast_copy(char *src , char *dest)
{
int fd_in , fd_out;
int ret_code = 0;
struct stat st;
char *m_src , *m_dst;

	if(0 > (fd_in = open(src,O_RDONLY))) {
		perror("open");
		return(-1);
	}

	if(0 > (fd_out = open(dest,O_RDWR | O_CREAT | O_TRUNC, 0600))) {
		perror("open");
		return(-1);
	}


	if(0 > fstat(fd_in,&st)) {
		perror("stat");
		return(-1);
	}

	/* set output size */
	if(-1 == lseek(fd_out,st.st_size -1 , SEEK_SET)) {
		perror("lseek");
		return(-1);
	}

	/* write one byte so we don't get SIGBUS when accessing the memory
		region */
	if(write(fd_out,"",1) != 1 ) {
		perror("write");
		return(-1);
	}


	m_src = mmap(0,st.st_size,PROT_READ, 
              MAP_SHARED , fd_in,0);
	if((caddr_t) -1 == m_src) {
		perror("mmap");
		return(-1);
	}

	m_dst = mmap(0,st.st_size,PROT_READ | PROT_WRITE, 
              MAP_SHARED , fd_out,0);
	if((caddr_t) -1 == m_dst) {
		perror("mmap");
		return(-1);
	}

	/* do the copy */
	memcpy(m_dst,m_src,st.st_size);
	close(fd_in);
	close(fd_out);
	chmod(dest,st.st_mode);

	return(0);
}
	


	
int main(int argc , char **argv)
{


	if(argc < 3) {
		printf("usage : %s <in> <out>\n",argv[0]);

		return(1);
	}

	fast_copy(argv[1],argv[2]);
	return(0);

}

