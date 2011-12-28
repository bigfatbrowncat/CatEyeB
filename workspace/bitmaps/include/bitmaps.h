#ifndef BITMAPS_H_
#define BITMAPS_H_

#ifdef WIN32
	#ifdef BUILDING_LIBBITMAP
		#define LIBBITMAP __declspec(dllexport)
	#else
		#define LIBBITMAP __declspec(dllimport)
	#endif
#else
	#define LIBBITMAP
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
	LIBBITMAP PreciseBitmap PreciseBitmap_Create(int width, int height);
	LIBBITMAP PreciseBitmap PreciseBitmap_Copy(PreciseBitmap src);
	LIBBITMAP void PreciseBitmap_Destroy(PreciseBitmap fb);

	// PreviewBitmap management
	LIBBITMAP PreviewBitmap PreviewBitmap_Create(int width, int height);
	LIBBITMAP PreviewBitmap PreviewBitmap_Copy(PreviewBitmap src);
	LIBBITMAP void PreviewBitmap_Destroy(PreviewBitmap fb);

	// Converters
	LIBBITMAP PreviewBitmap PreviewBitmap_FromPreciseBitmap(PreviewBitmap src);
}

#endif /* BITMAPS_H_ */
