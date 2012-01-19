#include "com/cateye/ui/swt/PreviewImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

void SaveImage(PreviewBitmap bitmap)
{
	FILE* file = fopen("c:\\1\\test.ppm", "wb");

	unsigned char* buf = new unsigned char[bitmap.width * bitmap.height * 3];
	for (int i = 0; i < bitmap.width * bitmap.height; i++)
	{
		Int8 r = bitmap.r[i];
		Int8 g = bitmap.g[i];
		Int8 b = bitmap.b[i];

		if (r != 0)
		{
			printf("%d", r); fflush(stdout);
		}

		if (g != 0)
		{
			printf("%d", g); fflush(stdout);
		}

		if (b != 0)
		{
			printf("%d", b); fflush(stdout);
		}

		buf[3 * i] = r;
		buf[3 * i + 1] = g;
		buf[3 * i + 2] = b;
	}

	fprintf(file, "P6\n%i %i 255\n", bitmap.width, bitmap.height);
	fwrite(buf, 1, bitmap.width * bitmap.height * 3, file);

	delete [] buf;

	fclose(file);
}

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_PreviewImageViewer_drawImage
    (JNIEnv *env, jclass that, jint handle, jobject bitmap, jint x, jint y, jint width, jint height)
{
	// Getting the class
	jclass cls = env->GetObjectClass(bitmap);

	jclass exception_cls;

	// Getting field ids
	jfieldID r_id, g_id, b_id, width_id, height_id;
	r_id = env->GetFieldID(cls, "r", "J");
	g_id = env->GetFieldID(cls, "g", "J");
	b_id = env->GetFieldID(cls, "b", "J");
	width_id = env->GetFieldID(cls, "width", "I");
	height_id = env->GetFieldID(cls, "height", "I");

	// Getting the bitmap from JVM
	PreviewBitmap src;
	src.r = (Int8*)env->GetLongField(bitmap, r_id);
	src.g = (Int8*)env->GetLongField(bitmap, g_id);
	src.b = (Int8*)env->GetLongField(bitmap, b_id);
	src.width = env->GetIntField(bitmap, width_id);
	src.height = env->GetIntField(bitmap, height_id);
	SaveImage(src);

	HDC hdc = (HDC)handle;
	HDC hdcMem = CreateCompatibleDC(hdc);

	//HANDLE bmp = LoadImage ( NULL, "c:\\1\\anyanyany.bmp", IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE );
	HBITMAP bmp = CreateBitmap(src.width, 200, 1, 8, src.g);
	printf("%d\n", bmp); fflush(stdout);
	SelectObject(hdcMem, (HGDIOBJ)bmp);

	int ret = BitBlt(hdc, 0, 0, width, height, hdcMem, 0, 0, SRCCOPY);

	if (ret == 0)
	{
		printf("BitBlt error: %d\n", GetLastError());
		fflush(stdout);
	}

	DeleteDC(hdcMem);
	DeleteObject((HGDIOBJ)bmp);
}
