#ifndef PPM_SAVER_H_
#define PPM_SAVER_H_

#include <bitmaps.h>

#ifdef WIN32
	#ifdef BUILDING_PPM_SAVER
		#define PPM_SAVER __declspec(dllexport)
	#else
		#define PPM_SAVER __declspec(dllimport)
	#endif
#else
	#define PPM_SAVER
#endif

extern "C"
{
	PPM_SAVER void SaveImage(char* filename, PreciseBitmap bitmap);
}

#endif /* PPM_SAVER_H_ */
