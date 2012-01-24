#include "com/cateye/ui/swt/PreciseImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

#define DEBUG_INFO	//printf("%d\n", __LINE__);fflush(stdout);

#define DRAWING_EXCEPTION_CANT_CREATE_BITMAP	"can't create bitmap"
#define DRAWING_EXCEPTION_CANT_CREATE_MEM_DC	"can't create memory device context"

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_PreciseImageViewer_drawImage
    (JNIEnv *env, jclass, jint handle, jobject bitmap,
     jint frame_left, jint frame_top, jint frame_width, jint frame_height, jint offset_left, jint offset_top,
     jfloat scale_out)
{
	DEBUG_INFO jclass exception_cls = env->FindClass("com/cateye/ui/swt/exceptions/DrawingException");
	if (bitmap != NULL)
	{
		// Getting the class
		DEBUG_INFO jclass cls = env->GetObjectClass(bitmap);

		// Getting field ids
		DEBUG_INFO jfieldID r_id, g_id, b_id, width_id, height_id;
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
		src.height = env->GetIntField(bitmap, height_id);

		HDC hdc = (HDC)handle;
		HDC hdcMem = CreateCompatibleDC(hdc);
		if (hdcMem != 0)
		{
			HBITMAP bmp = CreateCompatibleBitmap(hdc, frame_width, frame_height);
			if (bmp != 0)
			{
				BITMAPINFOHEADER bi;
				bi.biSize = sizeof(BITMAPINFOHEADER);
				bi.biWidth = (frame_width / 4 + 1) * 4;		// width % 4 should be 0 for SetDIBits to work correctly. I don't know why...
				bi.biHeight = frame_height + 1;				// adding last string
				bi.biPlanes = 1;
				bi.biBitCount = 24;
				bi.biCompression = BI_RGB;
				bi.biSizeImage = 0;	// Specifies the size, in bytes, of the image. This may be set to 0 for BI_RGB bitmaps.

				char* bits = new char[bi.biWidth * bi.biHeight * bi.biBitCount / 8];
				int k = 0;
				for (int j = 0; j < bi.biHeight; j++)
				for (int i = 0; i < bi.biWidth; i++)
				{
					int i2 = i + offset_left;
					int j2 = j + offset_top;

					int back_index = (bi.biHeight - j - 1) * bi.biWidth + i;

					float r = 0, g = 0, b = 0;
					float pixels = 0;

					int jpmin = (int)(j2 * scale_out), jpmax = (int)((j2 + 1) * scale_out);
					int ipmin = (int)(i2 * scale_out), ipmax = (int)((i2 + 1) * scale_out);

					if (ipmin == ipmax) ipmax = ipmin + 1;
					if (jpmin == jpmax) jpmax = ipmin + 1;

					for (int jp = jpmin; jp < jpmax; jp ++)
					for (int ip = ipmin; ip < ipmax; ip ++)
					{
						if (jp >= 0 && jp < src.height && ip >= 0 && ip < src.width)
						{
							b += src.b[jp * src.width + ip];
							g += src.g[jp * src.width + ip];
							r += src.r[jp * src.width + ip];
						}
						else
						{
							// painting in black
							// don't add anything
						}
						pixels ++;
					}

					bits[back_index * 3] = (Int8)(b / pixels * 255);
					bits[back_index * 3 + 1] = (Int8)(g / pixels * 255);
					bits[back_index * 3 + 2] = (Int8)(r / pixels * 255);
				}
				fflush(stdout);

				int lines = SetDIBits(hdcMem, bmp, 0, bi.biHeight, bits, (BITMAPINFO*)&bi, DIB_RGB_COLORS);

				delete[] bits;
				SelectObject(hdcMem, (HGDIOBJ)bmp);
				int ret = BitBlt(hdc, frame_left, frame_top, bi.biWidth, bi.biHeight, hdcMem, 0, 0, SRCCOPY);
				DeleteObject((HGDIOBJ)bmp);
			}
			else
			{
				env->ThrowNew(exception_cls, DRAWING_EXCEPTION_CANT_CREATE_BITMAP);
			}
			DeleteDC(hdcMem);
		}
		else
		{
			env->ThrowNew(exception_cls, DRAWING_EXCEPTION_CANT_CREATE_MEM_DC);
		}
	}
	else
	{
		env->ThrowNew(exception_cls, DRAWING_EXCEPTION_CANT_CREATE_MEM_DC);
	}

}
