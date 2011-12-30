#include "brightness_operation.h"
#include <math.h>

int Process(PreciseBitmap* bmp, double brightness,
             BrightnessOperationProgressReporter* progress_reporter)
{
	int pixels_per_percent = bmp->width * bmp->height / 100 + 1;		// 1 added for zero exclusion
	for (int i = 0; i < bmp->width * bmp->height; i++)
	{
		double r = bmp->r[i];
		double g = bmp->g[i];
		double b = bmp->b[i];

		r *= brightness;
		g *= brightness;
		b *= brightness;

		bmp->r[i] = r;
		bmp->g[i] = g;
		bmp->b[i] = b;

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
