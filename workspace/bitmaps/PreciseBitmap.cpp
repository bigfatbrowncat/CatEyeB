#include "bitmaps.h"

#include <string.h>

PreciseBitmap PreciseBitmap_Create(int width, int height)
{
	PreciseBitmap res;
	res.width = width;
	res.height = height;

	res.r = new float[width * height];
	res.g = new float[width * height];
	res.b = new float[width * height];

	return res;
}

PreciseBitmap PreciseBitmap_Copy(PreciseBitmap src)
{
	PreciseBitmap res = PreciseBitmap_Create(src.width, src.height);
	memcpy(res.r, src.r, src.width * src.height * sizeof(float));
	memcpy(res.g, src.g, src.width * src.height * sizeof(float));
	memcpy(res.b, src.b, src.width * src.height * sizeof(float));

	return res;
}

void PreciseBitmap_Destroy(PreciseBitmap fb)
{
	delete [] fb.r;
	delete [] fb.g;
	delete [] fb.b;
}

PreciseBitmap PreciseBitmap_FromInt16Bitmap(PreciseBitmap src)
{
	PreciseBitmap res = PreciseBitmap_Create(src.width, src.height);
	for (int k = 0; k < src.width * src.height; k++)
	{
		res.r[k] = (float)(src.r[k]) / 65535;
		res.g[k] = (float)(src.g[k]) / 65535;
		res.b[k] = (float)(src.b[k]) / 65535;
	}

	return res;
}
