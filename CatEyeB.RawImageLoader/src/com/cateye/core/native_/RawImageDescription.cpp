#include <com/cateye/core/native_/RawImageDescription.h>

JNIEXPORT void JNICALL Java_com_cateye_core_native_1_RawImageDescription_loadFromFile
	(JNIEnv * env, jobject obj, jstring filename)
{
	printf("Hello!\n");
	/* This code segment is OK. */
/*
	const jchar *c_str = env->GetStringCritical(filename, 0);
	if (c_str == NULL) {
		// TODO: error should be thrown
	}
	printf("%s\n", c_str);
	//DrawString(c_str);
	env->ReleaseStringCritical(filename, c_str);
*/

}
/*
    jbyte *str;
    str = env->GetStringUTFChars(filename, NULL);
    if (str == NULL) {
		return NULL;

    }*/
//    printf("%s", str);
//    (*env)->ReleaseStringUTFChars(env, prompt, str);

//    We assume here that the user does not type more than
//    127 characters
//    scanf("%s", buf);
//    return env->NewStringUTF(env, buf);
//}
