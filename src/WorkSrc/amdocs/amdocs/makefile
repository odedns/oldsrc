RM = "rm"
JAVAC = javac

.SUFFIXES: .class .java .c .exe .obj .h .inc .y .l .asm .lib


all: A.class B.class Test.class

.java.class:
	$(JAVAC) $<

clean:
	@echo Cleanning up ...!
	@$(RM) *.class
