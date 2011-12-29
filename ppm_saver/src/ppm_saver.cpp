#include "ppm_saver.h"

#include <stdio.h>

PPM_SAVER void SaveImage(char* filename, PreciseBitmap* bitmap)
{
	FILE* file = fopen(filename, "wb");

	unsigned char* buf = new unsigned char[bitmap->width * bitmap->height * 3];
	for (int i = 0; i < bitmap->width * bitmap->height; i++)
	{
		buf[3 * i] = (unsigned char)(bitmap->r[i] * 255);
		buf[3 * i + 1] = (unsigned char)(bitmap->g[i] * 255);
		buf[3 * i + 2] = (unsigned char)(bitmap->b[i] * 255);
	}

	fprintf(file, "P6\n%i %i 255\n", bitmap->width, bitmap->height);
	fwrite(buf, 1, bitmap->width * bitmap->height * 3, file);

	delete [] buf;

	fclose(file);
}
