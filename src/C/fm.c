#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <signal.h>
#include <fcntl.h>
#include <errno.h>


int openSock(name,port)
char *name;
int port;

{
      int mysock,opt=1;
      struct sockaddr_in sin;
      struct hostent *he;
      he = gethostbyname(name);
      if (he == NULL) {
            printf("No host found..\n");
            exit(0);
      }

      memcpy((caddr_t)&sin.sin_addr,he->h_addr_list[0],he->h_length);
      sin.sin_port = port;

      sin.sin_family = AF_INET;

      mysock = socket(AF_INET,SOCK_STREAM,0);

      opt = connect(mysock,(struct sockaddr *)&sin,sizeof(sin));

      return mysock;

}

/* This allows us to have many people on one TO line, seperated by
   commas or spaces. */

process(s,d)
int d;
char *s;
{
      char *tmp;
      char buf[120];

      tmp = strtok(s," ,");

      while (tmp != NULL) {
            sprintf(buf,"RCPT TO: %s\n",tmp);
            write(d,buf,strlen(buf));
            tmp = strtok(NULL," ,");
      }

}



getAndSendFrom(fd)
int fd;
{
      char from[100];
      char outbound[200];

      printf("You must should specify a From address now.\nFrom: ");
      gets(from);

      sprintf(outbound,"MAIL FROM: %s\n",from);
      write(fd,outbound,strlen(outbound));



}

getAndSendTo(fd)
int fd;
{
      char addrs[100];

      printf("Enter Recipients, with a blank line to end.\n");

      addrs[0] = '_';

      while (addrs[0] != '\0') {
            printf("To: ");
            gets(addrs);
            process(addrs,fd);
      }

}

getAndSendMsg(fd)
int fd;
{
      char textline[90];
      char outbound[103];

      sprintf(textline,"DATA\n");
      write(fd,textline,strlen(textline));


      printf("You may now enter your message.  End with a period\n\n");
      printf("[---------------------------------------------------------]\n");

      textline[0] = '_';

      while (textline[0] != '.') {
            gets(textline);
            sprintf(outbound,"%s\n",textline);
            write(fd,outbound,strlen(outbound));
      }

}


main(argc,argv)
int argc;
char *argv[];
{

      char text[200];
      int file_d;

      /* Get ready to connect to host. */
      printf("SMTP Host: ");
      gets(text);

      /* Connect to standard SMTP port. */
      file_d = openSock(text,25);

      if (file_d < 0) {
            printf("Error connecting to SMTP host.\n");
            perror("smtp_connect");
            exit(0);
      }

      printf("\n\n[+ Connected to SMTP host %s +]\n",text);

      sleep(1);

      getAndSendFrom(file_d);

      getAndSendTo(file_d);

      getAndSendMsg(file_d);

      sprintf(text,"QUIT\n");
      write(file_d,text,strlen(text));

    /* Here we just print out all the text we got from the SMTP
       Host.  Since this is a simple program, we didnt need to do
       anything with it. */

    printf("[Session Message dump]:\n");
      while(read(file_d,text,78) > 0)
            printf("%s\n",text);
      close(file_d);
}
