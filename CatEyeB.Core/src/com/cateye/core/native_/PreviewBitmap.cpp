#include <com/cateye/core/native_/PreviewBitmap.h>

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreviewBitmap_Init
  (JNIEnv *, jclass, jobject, jint, jint)
{
	return com_cateye_core_native__PreviewBitmap_BITMAP_RESULT_OK;
}

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreviewBitmap_Copy
  (JNIEnv *, jclass, jobject, jobject)
{
	return com_cateye_core_native__PreviewBitmap_BITMAP_RESULT_OK;
}

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreviewBitmap_Free
  (JNIEnv *, jclass, jobject)
{
	return com_cateye_core_native__PreviewBitmap_BITMAP_RESULT_OK;
}

