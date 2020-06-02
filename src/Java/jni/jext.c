#include "JExt.h"

/*
 * Class:     JExt
 * Method:    print
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_JExt_print
  (JNIEnv *env, jobject o, jstring js)
{

 const char *s = NULL;
 s = (*env)->GetStringUTFChars(env, js, 0);
 printf("got js = %s\n",s);

  /* Free up the Java string argument so we don't leak memory. */
  (*env)->ReleaseStringUTFChars(env, js, s);

}



void foo()
{
	printf("This is foo\n");
}
