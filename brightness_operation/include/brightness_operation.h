#ifndef BRIGHTNESS_OPERATION_H_
#define BRIGHTNESS_OPERATION_H_

#include <bitmaps.h>

#ifdef WIN32
	#ifdef BUILDING_BRIGHTNESS_OPERATION
		#define BRIGHTNESS_OPERATION __declspec(dllexport)
	#else
		#define BRIGHTNESS_OPERATION __declspec(dllimport)
	#endif
#else
	#define BRIGHTNESS_OPERATION
#endif

#ifndef NULL
#define NULL	0
#endif

typedef bool BrightnessOperationProgressReporter(float progress);
typedef void BrightnessOperationResultReporter(int code, PreciseBitmap* res);

#define BRIGHTNESS_OPERATION_RESULT_OK							0
#define BRIGHTNESS_OPERATION_RESULT_CANCELLED_BY_CALLBACK		1

extern "C"
{
	BRIGHTNESS_OPERATION void Process(PreciseBitmap* bmp, double brightness,
			BrightnessOperationProgressReporter* progress_reporter,
			BrightnessOperationResultReporter* result_reporter);
}

#endif /* BRIGHTNESS_OPERATION_H_ */
