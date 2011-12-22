#include "bitmaps.h"

#include <string.h>

Int16Bitmap Int16Bitmap_Create(int width, int height)
{
	Int16Bitmap res;
	res.width = width;
	res.height = height;

	res.r = new Int16[width * height];
	res.g = new Int16[width * height];
	res.b = new Int16[width * height];

	return res;
}

Int16Bitmap Int16Bitmap_Copy(Int16Bitmap src)
{
	Int16Bitmap res = Int16Bitmap_Create(src.width, src.height);
	memcpy(res.r, src.r, src.width * src.height * sizeof(Int16));
	memcpy(res.g, src.g, src.width * src.height * sizeof(Int16));
	memcpy(res.b, src.b, src.width * src.height * sizeof(Int16));

	return res;
}

void Int16Bitmap_Destroy(Int16Bitmap ib)
{
	delete [] ib.r;
	delete [] ib.g;
	delete [] ib.b;
}

Int16Bitmap Int16Bitmap_From_FloatBitmap(Int16Bitmap src)
{
	Int16Bitmap res = Int16Bitmap_Create(src.width, src.height);
	for (int k = 0; k < src.width * src.height; k++)
	{
		res.r[k] = (Int16)(src.r[k] * 65535);
		res.g[k] = (Int16)(src.g[k] * 65535);
		res.b[k] = (Int16)(src.b[k] * 65535);
	}

	return res;
}
