#include <windows.h>

#include "mataf_utils_NativeUtils.h"


JNIEXPORT void JNICALL Java_mataf_utils_NativeUtils_beep (JNIEnv *env, jclass c, 
				jint freq, jint duration)
{
	Beep(freq,duration);
}

