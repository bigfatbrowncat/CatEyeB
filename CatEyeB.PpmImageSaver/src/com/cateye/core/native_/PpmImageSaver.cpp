#include <com/cateye/core/native_/PpmImageSaver.h>

#include <stdio.h>
#include <math.h>

inline double luminosity(double r, double g, double b)
{
	return sqrt(r * r + g * g + b * b);
}

/*
 * limit_a should be in range 2..4
 */
/*void SaveImage(char* filename, PreciseBitmap* bitmap, double limit_a)
{
	FILE* file = fopen(filename, "wb");

	unsigned char* buf = new unsigned char[bitmap->width * bitmap->height * 3];
	for (int i = 0; i < bitmap->width * bitmap->height; i++)
	{
		double r = bitmap->r[i];
		double g = bitmap->g[i];
		double b = bitmap->b[i];

		double lum = luminosity(r, g, b);
		double lum_lim = 1 - pow(limit_a, -lum);

		buf[3 * i] = (unsigned char)(r * lum_lim / lum * 255);
		buf[3 * i + 1] = (unsigned char)(g * lum_lim / lum * 255);
		buf[3 * i + 2] = (unsigned char)(b * lum_lim / lum * 255);
	}

	fprintf(file, "P6\n%i %i 255\n", bitmap->width, bitmap->height);
	fwrite(buf, 1, bitmap->width * bitmap->height * 3, file);

	delete [] buf;

	fclose(file);
}
*/

JNIEXPORT void JNICALL Java_com_cateye_core_native_1_PpmImageSaver_SaveImage
  (JNIEnv *, jclass, jstring, jobject, jdouble)
{
}
