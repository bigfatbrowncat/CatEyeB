#include <com/cateye/stageoperations/downsample/DownsampleStageOperationProcessor.h>
#include <colorlib.h>
#include <bitmaps.h>
#include <jni.h>
#include <math.h>
#include <mem.h>

#include <vector>

using namespace std;

#define DEBUG_INFO printf("%d\n", __LINE__);fflush(stdout);

JNIEXPORT void JNICALL Java_com_cateye_stageoperations_downsample_DownsampleStageOperationProcessor_process
  (JNIEnv * env, jobject obj, jobject params, jobject bitmap, jobject listener)
{
	// Getting the class
	jclass cls = env->GetObjectClass(bitmap);

	// Getting field ids
	jfieldID r_id, g_id, b_id, width_id, height_id;
	r_id = env->GetFieldID(cls, "r", "J");
	g_id = env->GetFieldID(cls, "g", "J");
	b_id = env->GetFieldID(cls, "b", "J");
	width_id = env->GetFieldID(cls, "width", "I");
	height_id = env->GetFieldID(cls, "height", "I");

	// Getting the bitmap from JVM
	PreciseBitmap bmp;
	bmp.r = (float*)env->GetLongField(bitmap, r_id);
	bmp.g = (float*)env->GetLongField(bitmap, g_id);
	bmp.b = (float*)env->GetLongField(bitmap, b_id);
	bmp.width = env->GetIntField(bitmap, width_id);
	bmp.height = env->GetIntField(bitmap, height_id);

	// Getting the stage operation parameters
/*	jclass operationClass = env->GetObjectClass(params);
	jfieldID rId, gId, bId;

	rId = env->GetFieldID(operationClass, "r", "D");
	gId = env->GetFieldID(operationClass, "g", "D");
	bId = env->GetFieldID(operationClass, "b", "D");*/

}
