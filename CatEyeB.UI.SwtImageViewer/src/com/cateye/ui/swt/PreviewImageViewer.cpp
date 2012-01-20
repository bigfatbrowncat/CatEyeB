#include "com/cateye/ui/swt/PreviewImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

#define DEBUG_INFO	//printf("%d\n", __LINE__);fflush(stdout);

#define DRAWING_EXCEPTION_CANT_CREATE_BITMAP	"can't create bitmap"
#define DRAWING_EXCEPTION_CANT_CREATE_MEM_DC	"can't create memory device context"

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_PreviewImageViewer_drawImage
    (JNIEnv *env, jclass, jint handle, jobject bitmap, jint crop_left, jint crop_top, jint crop_width, jint crop_height)
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
		PreviewBitmap src;
		src.r = (Int8*)env->GetLongField(bitmap, r_id);
		src.g = (Int8*)env->GetLongField(bitmap, g_id);
		src.b = (Int8*)env->GetLongField(bitmap, b_id);
		src.width = env->GetIntField(bitmap, width_id);
		src.height = env->GetIntField(bitmap, height_id);

		HDC hdc = (HDC)handle;
		HDC hdcMem = CreateCompatibleDC(hdc);
		if (hdcMem != 0)
		{
			HBITMAP bmp = CreateCompatibleBitmap(hdc, crop_width, crop_height);
			if (bmp != 0)
			{
				BITMAPINFOHEADER bi;
				bi.biSize = sizeof(BITMAPINFOHEADER);
				bi.biWidth = (crop_width / 4 + 1) * 4;		// width % 4 should be 0 for BitBlt to work correctly. I don't know why...
				bi.biHeight = crop_height + 1;				// adding last string
				bi.biPlanes = 1;
				bi.biBitCount = 24;
				bi.biCompression = BI_RGB;
				bi.biSizeImage = 0;	// Specifies the size, in bytes, of the image. This may be set to 0 for BI_RGB bitmaps.

				char* bits = new char[bi.biWidth * bi.biHeight * bi.biBitCount / 8];
				int k = 0;
				for (int j = 0; j < bi.biHeight; j++)
				for (int i = 0; i < bi.biWidth; i++)
				{
					int i2 = i + crop_left;
					int j2 = j + crop_top;

					int back_index = (bi.biHeight - j - 1) * bi.biWidth + i;

					if (j2 >= 0 && j2 < src.height && i2 >= 0 && i2 < src.width)
					{
						bits[back_index * 3] = src.b[j2 * src.width + i2];
						bits[back_index * 3 + 1] = src.g[j2 * src.width + i2];
						bits[back_index * 3 + 2] = src.r[j2 * src.width + i2];
					}
					else
					{
						// painting in black
						bits[back_index * 3] = 0;
						bits[back_index * 3 + 1] = 0;
						bits[back_index * 3 + 2] = 0;
					}
				}
				fflush(stdout);

				int lines = SetDIBits(hdcMem, bmp, 0, bi.biHeight, bits, (BITMAPINFO*)&bi, DIB_RGB_COLORS);

				delete[] bits;
				SelectObject(hdcMem, (HGDIOBJ)bmp);
				int ret = BitBlt(hdc, 0, 0, bi.biWidth, bi.biHeight, hdcMem, 0, 0, SRCCOPY);
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

/*#include "com/cateye/ui/swt/PreviewImageViewer.h"
#include <math.h>
#include <bitmaps.h>
#include <jni.h>
#include <windows.h>

#define DEBUG_INFO	printf("%d\n", __LINE__);fflush(stdout);

#define DRAWING_EXCEPTION_CANT_CREATE_BITMAP	"can't create bitmap"
#define DRAWING_EXCEPTION_CANT_CREATE_MEM_DC	"can't create memory device context"
#define DRAWING_EXCEPTION_BITMAP_IS_NULL		"bitmap shouldn't be null"

JNIEXPORT void JNICALL Java_com_cateye_ui_swt_PreviewImageViewer_drawImage
    (JNIEnv *env, jclass, jint handle, jobject bitmap, jint crop_left, jint crop_top, jint crop_width, jint crop_height)
{
	DEBUG_INFO jclass exception_cls = env->FindClass("com/cateye/ui/swt/exceptions/DrawingException");
	DEBUG_INFO
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
		DEBUG_INFO PreviewBitmap src;
		src.r = (Int8*)env->GetLongField(bitmap, r_id);
		src.g = (Int8*)env->GetLongField(bitmap, g_id);
		src.b = (Int8*)env->GetLongField(bitmap, b_id);
		src.width = env->GetIntField(bitmap, width_id);
		src.height = env->GetIntField(bitmap, height_id);

		DEBUG_INFO HDC hdc = (HDC)handle;
		HDC hdcMem = CreateCompatibleDC(hdc);
		if (hdcMem != 0)
		{
			DEBUG_INFO HBITMAP bmp = CreateCompatibleBitmap(hdc, crop_width, crop_height);
			if (bmp != 0)
			{
				DEBUG_INFO BITMAPINFOHEADER bi;
				bi.biSize = sizeof(BITMAPINFOHEADER);
				bi.biWidth = crop_width;
				bi.biHeight = crop_height;
				bi.biPlanes = 1;
				bi.biBitCount = 24;
				bi.biCompression = BI_RGB;
				bi.biSizeImage = 0;	// Specifies the size, in bytes, of the image. This may be set to 0 for BI_RGB bitmaps.

				DEBUG_INFO char* bits = new char[crop_width * crop_height * bi.biBitCount / 8];
				DEBUG_INFO
				int k = 0;
				for (int j = 0; j < crop_height; j++)
				{
					for (int i = 0; i < crop_width; i++)
					{
						int pos = j * src.width + i;
						bits[k * 3] = src.b[pos];
						bits[k * 3 + 1] = src.g[pos];
						bits[k * 3 + 2] = src.r[pos];
						k++;
					}
				}

				//printf("%d, %d, %d, %d", src.)

				DEBUG_INFO int lines = SetDIBits(hdcMem, bmp, 0, crop_height, bits, (BITMAPINFO*)&bi, DIB_RGB_COLORS);
				DEBUG_INFO
				delete[] bits;
				DEBUG_INFO SelectObject(hdcMem, (HGDIOBJ)bmp);
				DEBUG_INFO int ret = BitBlt(hdc, 0, 0, crop_width, crop_height, hdcMem, 0, 0, SRCCOPY);
				DEBUG_INFO DeleteObject((HGDIOBJ)bmp);

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
 */

