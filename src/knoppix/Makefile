CC = gcc
CFLAGS = -Wall
BIN = agents
OBJ = agent.o  mutex.o

agents: $(OBJ)
	$(CC) $(CFLAGS) -o $@ $(OBJ)
agent.o: agent.c agent.h
	$(CC) $(CFLAGS) -c agent.c
mutex.o: mutex.c mutex.h
	$(CC) $(CFLAGS) -c mutex.c
clean:
	rm -f $(BIN) *.o core account *~
