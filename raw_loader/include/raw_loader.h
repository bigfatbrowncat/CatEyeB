#ifndef SSRL_H_
#define SSRL_H_

#include <bitmaps.h>

#ifdef WIN32
	#ifdef BUILDING_RAW_LOADER
		#define RAW_LOADER __declspec(dllexport)
	#else
		#define RAW_LOADER __declspec(dllimport)
	#endif
#else
	#define RAW_LOADER
#endif

struct ExtractedDescription
{
	PreviewBitmap* thumbnail;

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

// Values for "code" parameter of ExtractingResultReporter
#define EXTRACTING_RESULT_OK						0
#define EXTRACTING_RESULT_UNSUFFICIENT_MEMORY		1
#define EXTRACTING_RESULT_DATA_ERROR				2
#define EXTRACTING_RESULT_IO_ERROR					3
#define EXTRACTING_RESULT_CANCELLED_BY_CALLBACK		4
#define EXTRACTING_RESULT_BAD_CROP					5
#define EXTRACTING_RESULT_UNSUPPORTED_FORMAT		6
#define EXTRACTING_RESULT_UNKNOWN					100

typedef bool ProgressReporter(float progress);

extern "C"
{
	RAW_LOADER int ExtractedRawImage_LoadFromFile(char* filename, bool divide_by_2,
	                                              PreciseBitmap* res,
	                                              ProgressReporter* progress_reporter);

	RAW_LOADER int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
	RAW_LOADER void ExtractedDescription_Free(ExtractedDescription* description);
}


#endif /* SSRL_H_ */
