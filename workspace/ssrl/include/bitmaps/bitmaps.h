#ifndef BITMAPS_H_
#define BITMAPS_H_

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

struct Int8Bitmap
{
	int width;
	int height;
	Int8* r;
	Int8* g;
	Int8* b;
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
	FloatBitmap FloatBitmap_Create(int width, int height);
	FloatBitmap FloatBitmap_Copy(FloatBitmap src);
	void FloatBitmap_Destroy(FloatBitmap fb);

	// Int16Bitmap management
	Int16Bitmap Int16Bitmap_Create(int width, int height);
	Int16Bitmap Int16Bitmap_Copy(Int16Bitmap src);
	void Int16Bitmap_Destroy(Int16Bitmap fb);

	// Int8Bitmap management
	Int8Bitmap Int8Bitmap_Create(int width, int height);
	Int8Bitmap Int8Bitmap_Copy(Int8Bitmap src);
	void Int8Bitmap_Destroy(Int8Bitmap fb);

	// Converters
	FloatBitmap FloatBitmap_FromInt16Bitmap(FloatBitmap src);
	Int16Bitmap Int16Bitmap_FromFloatBitmap(Int16Bitmap src);
	FloatBitmap FloatBitmap_FromInt8Bitmap(FloatBitmap src);
	Int16Bitmap Int8Bitmap_FromFloatBitmap(Int16Bitmap src);
}

#endif /* BITMAPS_H_ */
