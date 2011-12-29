#include "brightness_operation.h"

int Process(PreciseBitmap* bmp, double brightness,
             BrightnessOperationProgressReporter* progress_reporter)
{
	int pixels_per_percent = bmp->width * bmp->height / 100 + 1;		// 1 added for zero exclusion
	for (int i = 0; i < bmp->width * bmp->height; i++)
	{
		bmp->r[i] *= brightness;
		bmp->g[i] *= brightness;
		bmp->b[i] *= brightness;

		// Reporting progress
		if (i % pixels_per_percent == 0 && progress_reporter != NULL)
		{
			bool user_answer = (*progress_reporter)((float)i / pixels_per_percent);

			// Checking if the operation canceled
			if (!user_answer)
			{
				return BRIGHTNESS_OPERATION_RESULT_CANCELLED_BY_CALLBACK;
			}
		}

	}
	return BRIGHTNESS_OPERATION_RESULT_OK;
}
