#ifndef BITMAPS_H_
#define BITMAPS_H_

#ifdef WIN32
#define DllDef __declspec(dllexport)
#else
#define DllDef
#endif

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

extern "C"
{
	// PreciseBitmap management
	DllDef PreciseBitmap PreciseBitmap_Create(int width, int height);
	DllDef PreciseBitmap PreciseBitmap_Copy(PreciseBitmap src);
	DllDef void PreciseBitmap_Destroy(PreciseBitmap fb);

	// PreviewBitmap management
	DllDef PreviewBitmap PreviewBitmap_Create(int width, int height);
	DllDef PreviewBitmap PreviewBitmap_Copy(PreviewBitmap src);
	DllDef void PreviewBitmap_Destroy(PreviewBitmap fb);

	// Converters
	DllDef PreviewBitmap PreviewBitmap_FromPreciseBitmap(PreviewBitmap src);
}

#endif /* BITMAPS_H_ */
