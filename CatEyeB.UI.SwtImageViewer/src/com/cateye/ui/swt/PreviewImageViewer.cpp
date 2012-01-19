#include "com/cateye/ui/swt/PreviewImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

#define DEBUG_INFO	//printf("%d\n", __LINE__);fflush(stdout);

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
	HDC hdc = (HDC)handle;
	HDC hdcMem = CreateCompatibleDC(hdc);
	//HANDLE bmp = LoadImage ( NULL, "c:\\1\\anyanyany.bmp", IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE );
	HBITMAP bmp = CreateCompatibleBitmap(hdc, src.width, src.height);
	BITMAPINFOHEADER bi;
	bi.biSize = sizeof(BITMAPINFOHEADER);
	bi.biWidth = src.width;
	bi.biHeight = src.height;
	bi.biPlanes = 1;
	bi.biBitCount = 24;
	bi.biCompression = BI_RGB;
	bi.biSizeImage = 0;	// Specifies the size, in bytes, of the image. This may be set to 0 for BI_RGB bitmaps.

	char* bits = new char[bi.biWidth * bi.biHeight * bi.biBitCount / 8];
	for (int i = 0; i < src.width * src.height; i++)
	{
		bits[i * 3] = src.b[i];
		bits[i * 3 + 1] = src.g[i];
		bits[i * 3 + 2] = src.r[i];
	}

	int ret2 = SetDIBits(hdcMem, bmp, 0, bi.biHeight, bits, (BITMAPINFO*)&bi, DIB_RGB_COLORS);
	SelectObject(hdcMem, (HGDIOBJ)bmp);
	int ret = BitBlt(hdc, 0, 0, width, height, hdcMem, 0, 0, SRCCOPY);
	DeleteDC(hdcMem);
	DeleteObject((HGDIOBJ)bmp);
	delete[] bits;
}
