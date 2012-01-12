#include <com/cateye/core/native_/PreciseBitmap.h>

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreciseBitmap_Init
  (JNIEnv *, jclass, jobject, jint, jint)
{
	return com_cateye_core_native__PreciseBitmap_BITMAP_RESULT_OK;
}

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreciseBitmap_Copy
  (JNIEnv *, jclass, jobject, jobject)
{
	return com_cateye_core_native__PreciseBitmap_BITMAP_RESULT_OK;
}


JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreciseBitmap_Free
  (JNIEnv *, jclass, jobject)
{
	return com_cateye_core_native__PreciseBitmap_BITMAP_RESULT_OK;
}

