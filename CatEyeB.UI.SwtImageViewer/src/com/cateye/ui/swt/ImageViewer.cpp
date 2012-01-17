#include "com/cateye/ui/swt/ImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_ImageViewer_drawImage
    (JNIEnv *env, jclass that, jint handle, jobject bitmap, jint x, jint y, jint width, jint height)
{
	/*// Getting the class
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
	PreciseBitmap src;
	src.r = (float*)env->GetLongField(bitmap, r_id);
	src.g = (float*)env->GetLongField(bitmap, g_id);
	src.b = (float*)env->GetLongField(bitmap, b_id);
	src.width = env->GetIntField(bitmap, width_id);
	src.height = env->GetIntField(bitmap, height_id);*/

	HDC hdc = (HDC)handle;
	//HDC hdcMem = CreateCompatibleDC(hdc);
	HDC hdcMem = GetDC(NULL);

	/*PreciseBitmap src;
	PreciseBitmap_Init(src, width, height);

	HBITMAP bmp = CreateBitmap(width, height, 0, 2, src.r);
	HGDIOBJ hbmOld = SelectObject(hdcMem, (HGDIOBJ)bmp);*/

	BitBlt(hdc, 0, 0, width, height, hdcMem, 0, 0, SRCCOPY);
	TextOut(hdc, 0, 0, "Hello, Windows!", 15);

	//SelectObject(hdcMem, hbmOld);
	DeleteDC(hdcMem);
}
