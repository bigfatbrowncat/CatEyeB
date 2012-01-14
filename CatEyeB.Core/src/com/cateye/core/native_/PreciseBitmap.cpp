#include <com/cateye/core/native_/PreciseBitmap.h>
#include <bitmaps.h>

JNIEXPORT jint JNICALL Java_com_cateye_core_native_1_PreciseBitmap_Init(JNIEnv * env, jclass cls, jobject obj, jint width, jint height)
{
	// Taking fields
	jfieldID r_id, g_id, b_id, width_id, height_id;
	r_id = env->GetFieldID(cls, "r", "J");
	g_id = env->GetFieldID(cls, "g", "J");
	b_id = env->GetFieldID(cls, "b", "J");
	width_id = env->GetFieldID(cls, "width", "I");
	height_id = env->GetFieldID(cls, "height", "I");

	// Creating the bitmap
	PreciseBitmap pbmp;
	int res = PreciseBitmap_Init(&pbmp, width, height);

	if (res == com_cateye_core_native__PreciseBitmap_BITMAP_RESULT_OK)
	{
		// Setting fields
		env->SetIntField(obj, width_id, width);
		env->SetIntField(obj, height_id, height);
		env->SetLongField(obj, r_id, (jlong)(pbmp.r));
		env->SetLongField(obj, g_id, (jlong)(pbmp.g));
		env->SetLongField(obj, b_id, (jlong)(pbmp.b));
	}

	return res;
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

