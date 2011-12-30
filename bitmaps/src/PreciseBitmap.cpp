#include "bitmaps.h"

#include <string.h>
#include <new>

int PreciseBitmap_Init(PreciseBitmap* bmp, int width, int height)
{
	try
	{
		bmp->width = width;
		bmp->height = height;

		bmp->r = 0; bmp->g = 0; bmp->b = 0;

		bmp->r = new float[width * height];
		bmp->g = new float[width * height];
		bmp->b = new float[width * height];

		return BITMAP_RESULT_OK;
	}
	catch (std::bad_alloc&)
	{
		if (bmp->r != NULL) delete [] bmp->r;
		if (bmp->g != NULL) delete [] bmp->g;
		if (bmp->b != NULL) delete [] bmp->b;

		return BITMAP_RESULT_OUT_OF_MEMORY;
	}

}

int PreciseBitmap_Copy(PreciseBitmap* src, PreciseBitmap* res)
{
	int init_res = PreciseBitmap_Init(res, src->width, src->height);
	if (init_res != BITMAP_RESULT_OK)
		return init_res;
	else
	{
		memcpy(res->r, src->r, src->width * src->height * sizeof(float));
		memcpy(res->g, src->g, src->width * src->height * sizeof(float));
		memcpy(res->b, src->b, src->width * src->height * sizeof(float));
		return BITMAP_RESULT_OK;
	}
}

int PreciseBitmap_Free(PreciseBitmap* fb)
{
	try
	{
		delete [] fb->r;
		delete [] fb->g;
		delete [] fb->b;
		return BITMAP_RESULT_OK;
	}
	catch (std::exception&)
	{
		return BITMAP_RESULT_INCORRECT_DATA;
	}
}
