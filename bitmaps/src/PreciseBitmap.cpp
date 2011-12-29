#include "bitmaps.h"

#include <string.h>

void PreciseBitmap_Init(PreciseBitmap* bmp, int width, int height)
{
	bmp->width = width;
	bmp->height = height;

	bmp->r = new float[width * height];
	bmp->g = new float[width * height];
	bmp->b = new float[width * height];
}

void PreciseBitmap_Copy(PreciseBitmap* src, PreciseBitmap* res)
{
	PreciseBitmap_Init(res, src->width, src->height);

	memcpy(res->r, src->r, src->width * src->height * sizeof(float));
	memcpy(res->g, src->g, src->width * src->height * sizeof(float));
	memcpy(res->b, src->b, src->width * src->height * sizeof(float));
}

void PreciseBitmap_Free(PreciseBitmap* fb)
{
	delete [] fb->r;
	delete [] fb->g;
	delete [] fb->b;
}
