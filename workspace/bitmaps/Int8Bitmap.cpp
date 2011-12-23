#include "bitmaps.h"

#include <string.h>

Int8Bitmap Int8Bitmap_Create(int width, int height)
{
	Int8Bitmap res;
	res.width = width;
	res.height = height;

	res.r = new Int8[width * height];
	res.g = new Int8[width * height];
	res.b = new Int8[width * height];

	return res;
}

Int8Bitmap Int8Bitmap_Copy(Int8Bitmap src)
{
	Int8Bitmap res = Int8Bitmap_Create(src.width, src.height);
	memcpy(res.r, src.r, src.width * src.height * sizeof(Int8));
	memcpy(res.g, src.g, src.width * src.height * sizeof(Int8));
	memcpy(res.b, src.b, src.width * src.height * sizeof(Int8));

	return res;
}

void Int8Bitmap_Destroy(Int8Bitmap ib)
{
	delete [] ib.r;
	delete [] ib.g;
	delete [] ib.b;
}

Int8Bitmap Int8Bitmap_From_FloatBitmap(Int8Bitmap src)
{
	Int8Bitmap res = Int8Bitmap_Create(src.width, src.height);
	for (int k = 0; k < src.width * src.height; k++)
	{
		res.r[k] = (Int8)(src.r[k] * 255);
		res.g[k] = (Int8)(src.g[k] * 255);
		res.b[k] = (Int8)(src.b[k] * 255);
	}

	return res;
}
