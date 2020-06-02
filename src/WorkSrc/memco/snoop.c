#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <net/raw.h>
#include <netinet/if_ether.h>
#include <netinet/in_systm.h>
#include <netinet/ip.h>
#include <netinet/tcp.h>

#define ETHERHDRPAD RAW_HDRPAD(sizeof(struct ether_header))
#define IPPORT_TELNET    23
#define IPPORT_LOGIN     513
#define IPPORT_FTP       21
#define IPPORT_SMTP      25

struct etherpacket {

	struct snoopheader snoop;
	char pad[ETHERHDRPAD];
	struct ether_header ether;
	char data[ETHERMTU];
	};

int s;
struct sockaddr_raw sr;
struct snoopfilter sf;
struct etherpacket ep;
int cc = 6000 ,on = 1;

void print_ether_header(struct ether_header *ether);
void print_snoopheader(struct snoopheader *snoop);
void dump_buff(char *buff, int len);
void print_ip_header(struct ip *IP);
int handle_packet(struct etherpacket *ep);
void print_tcp_header(struct tcphdr *TCPHDR);
char *TCPflags(register u_char flgs);

int main()
{

	if(-1 ==  (s = socket(PF_RAW,SOCK_RAW,RAWPROTO_SNOOP))) {
		perror("socket");
		exit(1);
	}
	
	sr.sr_family = AF_RAW;
	sr.sr_port = 0;
/*
	strncpy(sr.sr_ifname,"ec0",sizeof(sr.sr_ifname));
*/
	memset(sr.sr_ifname,'\0',sizeof(sr.sr_ifname));
	if(0 > bind(s,&sr,sizeof(sr))) {
		perror("bind");
		exit(1);
	}
	
	bzero((char *)&sf,sizeof(sf));

	ioctl(s,SIOCADDSNOOP,&sf);
	
	setsockopt(s,SOL_SOCKET,SO_RCVBUF,(char *) &cc,sizeof(cc));
	ioctl(s,SIOCSNOOPING,&on);

	while(1) {
		cc = read(s,(char *) &ep,sizeof(ep));
		if(0 > cc) {
			perror("read");	
			exit(1);
		}
		handle_packet(&ep);
	}
	exit(0);
}	

	

void print_snoopheader(struct snoopheader *snoop)
{

	printf("snoop_seq = %ld\nsnoop_flags = %d\nsnopp_packetlen = %d\n",
                snoop->snoop_seq,snoop->snoop_flags,snoop->snoop_packetlen);
}

void print_ether_header(struct ether_header *ether)
{
int i;

	printf("ether_dhost = ");
	for(i =0; i < 6; ++i) {
		printf("%02X:",ether->ether_dhost[i]);
	}
	putchar('\n');
	printf("ether_shost = ");
	for(i =0; i < 6; ++i) {
		printf("%02X:",ether->ether_shost[i]);
	}
	putchar('\n');
	
/*
	printf("ether_type = %02X\n",ntohs(ether->ether_type));
	printf("ether_dhost = %s\n",inet_ntoa(ether->ether_dhost));
	printf("ether_shost = %s\n",inet_ntoa(ether->ether_shost));
*/
}

void dump_buff(char *buff, int len)
{
int i = 0;


	while(i < len) {
		if(isprint(buff[i])) {
			printf("%c",buff[i]);
		} else {
			putchar('.');
		}
		++i;
	}
	putchar('\n');
}

void print_ip_header(struct ip *IP)
{

	printf("ip packet\n============\n");

	printf("version = %d\n",IP->ip_v);
	printf("header length = %d\n",(int)(IP->ip_hl * 4 ));
	printf("tos = %02X\n",IP->ip_tos);
	printf("protocol = %02X\n",IP->ip_p);
	printf("source = %s\n",inet_ntoa(IP->ip_src));
	printf("dest = %s\n",inet_ntoa(IP->ip_dst));
	printf("packet_len = %d\n",ntohs(IP->ip_len));

}

int handle_packet(struct etherpacket *ep)
{
int stat = 0, len;
struct ip IP;
struct tcphdr *ptcp;
char *data , *cp;
int packlen = 0;

	

	if(ETHERTYPE_IP != ntohs(ep->ether.ether_type)) 
		return(stat);
	
	packlen = ep->snoop.snoop_packetlen;
	cp = ep->data;
	memcpy(&IP ,ep->data ,sizeof(struct ip));

	if(IP.ip_p != IPPROTO_TCP) 
		return(stat);


	ptcp = (struct tcphdr *)((char *)ep->data + (IP.ip_hl *4));
	if(!(ptcp->th_dport == IPPORT_TELNET ||
	     ptcp->th_dport == IPPORT_LOGIN ||
	     ptcp->th_dport == IPPORT_FTP )) {
		return(stat);
	}

	len = ntohs(IP.ip_len) - (IP.ip_hl *4) - (ptcp->th_off * 4);
	if(!len) 
		return(stat);
	print_ip_header(&IP);
	print_tcp_header(ptcp);
	data = (char *) ep->data + (IP.ip_hl * 4) + (ptcp->th_off * 4);

	printf("data len = %d\n",len);
	dump_buff(data ,len);

	return(stat);
	
}

void print_tcp_header(struct tcphdr *TCPHDR)
{

	printf("src_port = %u\n",TCPHDR->th_sport);
	printf("dest_port = %u\n",TCPHDR->th_dport);
	printf("th_off = %u\n",(int )TCPHDR->th_off * 4);
	printf("seq = %u\n",TCPHDR->th_seq);
	printf("flags = %s\n",TCPflags(TCPHDR->th_flags));

}



char *TCPflags(register u_char flgs)
{ 
static char iobuf[8];
#define SFL(P,THF,C) iobuf[P]=((flgs & THF)?C:'-')
 
  SFL(0,TH_FIN, 'F');
  SFL(1,TH_SYN, 'S');
  SFL(2,TH_RST, 'R');
  SFL(3,TH_PUSH,'P');
  SFL(4,TH_ACK, 'A');
  SFL(5,TH_URG, 'U');
  iobuf[6]=0;
  return(iobuf);
}
 

