#ifndef BITMAPS_H_
#define BITMAPS_H_

typedef unsigned char Int8;

struct PreciseBitmap
{
	int width;
	int height;
	float* r;
	float* g;
	float* b;
};

struct PreviewBitmap
{
	int width;
	int height;
	Int8* r;
	Int8* g;
	Int8* b;
};

// PreciseBitmap management
PreciseBitmap PreciseBitmap_Create(int width, int height);
PreciseBitmap PreciseBitmap_Copy(PreciseBitmap src);
void PreciseBitmap_Destroy(PreciseBitmap fb);

// PreviewBitmap management
PreviewBitmap PreviewBitmap_Create(int width, int height);
PreviewBitmap PreviewBitmap_Copy(PreviewBitmap src);
void PreviewBitmap_Destroy(PreviewBitmap fb);

// Converters
PreviewBitmap PreviewBitmap_FromPreciseBitmap(PreviewBitmap src);

#endif /* BITMAPS_H_ */
