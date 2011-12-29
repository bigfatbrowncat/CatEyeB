#include "bitmaps.h"

#include <string.h>

void PreviewBitmap_Init(PreviewBitmap* bmp, int width, int height)
{
	bmp->width = width;
	bmp->height = height;

	bmp->r = new Int8[width * height];
	bmp->g = new Int8[width * height];
	bmp->b = new Int8[width * height];
}

void PreviewBitmap_Copy(PreviewBitmap* src, PreviewBitmap* res)
{
	PreviewBitmap_Init(res, src->width, src->height);

	memcpy(res->r, src->r, src->width * src->height * sizeof(Int8));
	memcpy(res->g, src->g, src->width * src->height * sizeof(Int8));
	memcpy(res->b, src->b, src->width * src->height * sizeof(Int8));
}

void PreviewBitmap_Free(PreviewBitmap* fb)
{
	delete [] fb->r;
	delete [] fb->g;
	delete [] fb->b;
}

void PreviewBitmap_FromPreciseBitmap(PreciseBitmap* src, PreviewBitmap* res)
{
	PreviewBitmap_Init(res, src->width, src->height);

	for (int k = 0; k < src->width * src->height; k++)
	{
		res->r[k] = (Int8)(src->r[k] * 255);
		res->g[k] = (Int8)(src->g[k] * 255);
		res->b[k] = (Int8)(src->b[k] * 255);
	}
}
