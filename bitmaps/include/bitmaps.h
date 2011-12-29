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
	LIBBITMAP void PreciseBitmap_Init(PreciseBitmap* bmp, int width, int height);
	LIBBITMAP void PreciseBitmap_Copy(PreciseBitmap* src, PreciseBitmap* res);
	LIBBITMAP void PreciseBitmap_Free(PreciseBitmap* fb);

	// PreviewBitmap management
	LIBBITMAP void PreviewBitmap_Init(PreviewBitmap* bmp, int width, int height);
	LIBBITMAP void PreviewBitmap_Copy(PreviewBitmap* src, PreviewBitmap* res);
	LIBBITMAP void PreviewBitmap_Free(PreviewBitmap* fb);

	// Converters
	LIBBITMAP void PreviewBitmap_FromPreciseBitmap(PreciseBitmap* src, PreviewBitmap* res);
}

#endif /* BITMAPS_H_ */
