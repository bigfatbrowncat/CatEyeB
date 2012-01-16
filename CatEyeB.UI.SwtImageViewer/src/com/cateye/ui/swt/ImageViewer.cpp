#include "com/cateye/ui/swt/ImageViewer.h"
#include <math.h>

#include <jni.h>
#include <windows.h>

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_ImageViewer_drawControl
    (JNIEnv *env, jclass that, jint handle, jint x, jint y, jint width, jint height)
{
	PAINTSTRUCT ps;
	HDC hdc;
	HWND hwnd = (HWND)handle;
	hdc = BeginPaint(hwnd, &ps);
	TextOut(hdc, 0, 0, "Hello, Windows!", 15);
	EndPaint(hwnd, &ps);
}
