#ifndef SSRL_H_
#define SSRL_H_

#include <bitmaps.h>

//using namespace std;

#undef DllDef
#ifdef WIN32
# define DllDef  __declspec( dllexport )
#else
# define DllDef
#endif

struct ExtractedRawImage {
	int width;
	int height;
	int bitsPerChannel;
	void* data;
	libraw_processed_image_t* libraw_image;
};

struct ExtractedDescription
{
	PreviewBitmap  thumbnail;

	float       iso_speed;
    float       shutter;
    float       aperture;
    float       focal_len;
    time_t      timestamp;
    unsigned    shot_order;
    unsigned*   gpsdata;
    char*       desc;
    char*       artist;
    char*       camera_maker;
    char*       camera_model;
    int         flip;
};

typedef bool ExtractingProgressReporter(float progress);
typedef bool ExtractingResultReporter(int code);

extern "C"
{
	DllDef int ExtractedRawImage_LoadFromFile(char* filename,
			bool divide_by_2,
			ExtractedRawImage* res,
			ExtractingProgressReporter* progress_reporter,
			ExtractingResultReporter* result_reporter);

	DllDef void ExtractedRawImage_Destroy(ExtractedRawImage* img);

	DllDef ExtractedDescription* ExtractedDescription_Create();
	DllDef int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
	DllDef void ExtractedDescription_Destroy(ExtractedDescription* description);
}


#endif /* SSRL_H_ */
