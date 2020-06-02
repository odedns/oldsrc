@echo off
gcc -c jext.c
gcc -shared -o jext.dll jext.o
gcc -o test test.c jext.dll
