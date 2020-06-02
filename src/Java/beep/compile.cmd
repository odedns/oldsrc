@echo off
gcc -c NativeUtils.c
gcc -shared -o nativeutils.dll NativeUtils.o
