#ifndef BITMAPS_H_
#define BITMAPS_H_

#ifdef WIN32
#define DllDef __declspec(dllexport)
#else
#define DllDef
#endif

typedef unsigned char Int8;
typedef unsigned short Int16;

struct FloatBitmap
{
	int width;
	int height;
	float* r;
	float* g;
	float* b;
};

struct Int16Bitmap
{
	int width;
	int height;
	Int16* r;
	Int16* g;
	Int16* b;
};

extern "C"
{
	// FloatBitmap management
	DllDef FloatBitmap FloatBitmap_Create(int width, int height);
	DllDef FloatBitmap FloatBitmap_Copy(FloatBitmap src);
	DllDef void FloatBitmap_Destroy(FloatBitmap fb);

	// Int16Bitmap management
	DllDef Int16Bitmap Int16Bitmap_Create(int width, int height);
	DllDef Int16Bitmap Int16Bitmap_Copy(Int16Bitmap src);
	DllDef void Int16Bitmap_Destroy(Int16Bitmap fb);

	// Converters
	DllDef FloatBitmap FloatBitmap_From_Int16Bitmap(FloatBitmap src);
	DllDef Int16Bitmap Int16Bitmap_From_FloatBitmap(Int16Bitmap src);
}

#endif /* BITMAPS_H_ */
