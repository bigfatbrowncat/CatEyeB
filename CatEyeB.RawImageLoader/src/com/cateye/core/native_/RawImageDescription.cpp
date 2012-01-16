#include <com/cateye/core/native_/RawImageDescription.h>

#include <stdio.h>

JNIEXPORT void JNICALL Java_com_cateye_core_native_1_RawImageDescription_loadFromFile
	(JNIEnv * env, jobject obj, jstring filename)
{
    const char* str;
    str = env->GetStringUTFChars(filename, NULL);
    if (str == NULL) {
        printf("Error: NULL string!\n", str);
		return;

    }
    printf("%s\n", str);
    env->ReleaseStringUTFChars(filename, str);
}
