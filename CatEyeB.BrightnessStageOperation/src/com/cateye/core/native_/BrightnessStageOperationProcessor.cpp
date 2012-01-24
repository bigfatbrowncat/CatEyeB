#include <com/cateye/core/native_/BrightnessStageOperationProcessor.h>
#include <bitmaps.h>
#include <jni.h>
#include <math.h>

#define DEBUG_INFO printf("%d\n", __LINE__);fflush(stdout);

JNIEXPORT void JNICALL Java_com_cateye_core_native_1_BrightnessStageOperationProcessor_process
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

	// Getting the brightness
	jclass operationClass = env->GetObjectClass(params);
	jfieldID brightnessId;
	brightnessId = env->GetFieldID(operationClass, "brightness", "D");

	double brightness = env->GetDoubleField(params, brightnessId);

	int pixels_per_percent = bmp.width * bmp.height / 100 + 1;		// 1 added for zero exclusion

	for (int i = 0; i < bmp.width * bmp.height; i++)
	{
		double r = bmp.r[i];
		double g = bmp.g[i];
		double b = bmp.b[i];

		r *= brightness;
		g *= brightness;
		b *= brightness;

		bmp.r[i] = r;
		bmp.g[i] = g;
		bmp.b[i] = b;

		// Reporting progress
		/*if (i % pixels_per_percent == 0 && progress_reporter != NULL)
		{
			bool user_answer = (*progress_reporter)((float)i / pixels_per_percent);

			// Checking if the operation canceled
			if (!user_answer)
			{
				return BRIGHTNESS_OPERATION_RESULT_CANCELLED_BY_CALLBACK;
			}
		}*/
	}
}
