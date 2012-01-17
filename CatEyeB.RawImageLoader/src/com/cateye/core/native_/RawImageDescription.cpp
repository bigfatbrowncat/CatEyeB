#include <stdio.h>
#include <string.h>

#include <libraw.h>
#include <jpeglib.h>
#include <bitmaps.h>

#include <com/cateye/core/native_/RawImageDescription.h>

#define RAWPROCESSOR_OPEN_BUFFER			1024 * 1024 * 1024		// 1Gb
#define	MESSAGE_LIBRAW_OUT_OF_MEMORY		"Out of memory occured during libraw processing"
#define MESSAGE_LIBRAW_FILE_UNSUPPORTED		"File format is unsupported"
#define	MESSAGE_LIBRAW_DATA_ERROR			"Libraw data error"
#define	MESSAGE_LIBRAW_IO_ERROR				"I/O error during libraw processing"
#define	MESSAGE_LIBRAW_CALLBACK_CANCEL		"Cancelled by callback"
#define	MESSAGE_LIBRAW_BAD_CROP				"Bad crop"
#define	MESSAGE_LIBRAW_UNKNOWN_ERROR		"Unknown problem"

void throw_libraw_exception(JNIEnv * env, int libraw_code)
{
	jclass exception_cls;

	switch (libraw_code)
	{
	case LIBRAW_SUCCESS:
		return;
    case LIBRAW_UNSUFFICIENT_MEMORY:
		exception_cls = env->FindClass("com/cateye/core/NativeHeapAllocationException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_OUT_OF_MEMORY);
		break;
    case LIBRAW_FILE_UNSUPPORTED:
		exception_cls = env->FindClass("com/cateye/core/UnsupportedFormatException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_FILE_UNSUPPORTED);
		break;
    case LIBRAW_DATA_ERROR:
		exception_cls = env->FindClass("com/cateye/core/CorruptedImageException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_DATA_ERROR);
		break;
    case LIBRAW_IO_ERROR:
		exception_cls = env->FindClass("com/cateye/core/BadFileException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_IO_ERROR);
		break;
    case LIBRAW_CANCELLED_BY_CALLBACK:
		exception_cls = env->FindClass("com/cateye/core/LoadingCancelledException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_CALLBACK_CANCEL);
		break;
    case LIBRAW_BAD_CROP:
		exception_cls = env->FindClass("com/cateye/core/BadCropException");
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_BAD_CROP);
		break;
    default:
		exception_cls = env->FindClass("com/cateye/core/UnknownException");
		printf("libraw error %d", libraw_code);
		env->ThrowNew(exception_cls, MESSAGE_LIBRAW_UNKNOWN_ERROR);
		break;
	}
}

void decode_jpeg(PreviewBitmap& res, unsigned char* buffer, unsigned long size)
{
	struct jpeg_decompress_struct cinfo;
	struct jpeg_error_mgr jerr;
	char *image;

	/*Initialize, open the JPEG and query the parameters */
	cinfo.err = jpeg_std_error(&jerr);
	jpeg_create_decompress(&cinfo);

	jpeg_mem_src(&cinfo, buffer, size);

	jpeg_read_header(&cinfo, TRUE);
	jpeg_start_decompress(&cinfo);

	/* allocate data and read the image as RGBRGBRGBRGB */
	image = (char*)malloc(cinfo.output_width * cinfo.output_height * cinfo.num_components);
	for(int i = 0; i < cinfo.output_height; i++)
	{
		char *ptr = image + i * 3 * cinfo.output_width;
		jpeg_read_scanlines(&cinfo, &ptr, 1);
	}

	jpeg_finish_decompress(&cinfo);
	jpeg_destroy_decompress(&cinfo);

	// Decompositing
	PreviewBitmap_Init(res, cinfo.output_width, cinfo.output_height);

	res.width = cinfo.output_width;
	res.height = cinfo.output_height;
	for (int i = 0; i < cinfo.output_width * cinfo.output_height; i++)
	{
		res.r[i] = image[3 * i];
		res.g[i] = image[3 * i + 1];
		res.b[i] = image[3 * i + 2];
	}

	free(image);
}

void decode_plain(PreviewBitmap& res, int width, int height, unsigned char* buffer, unsigned long size)
{
	// Decompositing
	PreviewBitmap_Init(res, width, height);
	for (int i = 0; i < width * height; i++)
	{
		res.r[i] = buffer[3 * i];
		res.g[i] = buffer[3 * i + 1];
		res.b[i] = buffer[3 * i + 2];
	}
}


JNIEXPORT void JNICALL Java_com_cateye_core_native_1_RawImageDescription_loadFromFile
	(JNIEnv * env, jobject obj, jstring filename)
{
	// Getting the classes
	jclass cls = env->GetObjectClass(obj);
	jclass previewBitmap_class = env->FindClass("Lcom/cateye/core/native_/PreviewBitmap;");
	jclass date_class = env->FindClass("Ljava/util/Date;");

	// Getting the methods
	jmethodID previewBitmap_init = env->GetMethodID(previewBitmap_class, "<init>", "()V");
	jmethodID date_init = env->GetMethodID(date_class, "<init>", "(J)V");

	// Getting the field ids
	jfieldID thumbnail_id = env->GetFieldID(cls, "thumbnail", "Lcom/cateye/core/IPreviewBitmap;"),
	         flip_id = env->GetFieldID(cls, "flip", "I"),
	         isoSpeed_id = env->GetFieldID(cls, "isoSpeed", "F"),
	         shutter_id = env->GetFieldID(cls, "shutter", "F"),
	         aperture_id = env->GetFieldID(cls, "aperture", "F"),
	         focalLength_id = env->GetFieldID(cls, "focalLength", "F"),
	         timeStamp_id = env->GetFieldID(cls, "timeStamp", "Ljava/util/Date;"),
	         shotOrder_id = env->GetFieldID(cls, "shotOrder", "I"),
	         description_id = env->GetFieldID(cls, "description", "Ljava/lang/String;"),
	         artist_id = env->GetFieldID(cls, "artist", "Ljava/lang/String;"),
	         cameraMaker_id = env->GetFieldID(cls, "cameraMaker", "Ljava/lang/String;"),
	         cameraModel_id = env->GetFieldID(cls, "cameraModel", "Ljava/lang/String;");

	jobject previewBitmap;
	jobject date = NULL;

    const char* fn;
    long time_con;
    int ret;

    LibRaw* RawProcessor = NULL;
    libraw_processed_image_t *image = NULL;

    fn = env->GetStringUTFChars(filename, NULL);
    if (fn == NULL) {
        printf("Error: NULL string!\n");
		return;
    }

	RawProcessor = new LibRaw();

	ret = RawProcessor->open_file(fn, RAWPROCESSOR_OPEN_BUFFER);
	if (ret != LIBRAW_SUCCESS)
	{
		goto end;
	}

	if ((ret = RawProcessor->unpack_thumb()) != LIBRAW_SUCCESS)
	{
		goto end;
	}

	// Setting fields
	printf("[Native] flip = %d\n", RawProcessor->imgdata.sizes.flip);
	env->SetIntField(obj, flip_id, RawProcessor->imgdata.sizes.flip);	// 0 - no rotation; 3 - 180-deg rotation; 5 - 90-deg counterclockwise, 6 - 90-deg clockwise
	printf("[Native] iso speed = %f\n", RawProcessor->imgdata.other.iso_speed);
	env->SetFloatField(obj, isoSpeed_id, RawProcessor->imgdata.other.iso_speed);
	printf("[Native] shutter = %f\n", RawProcessor->imgdata.other.shutter);
	env->SetFloatField(obj, shutter_id, RawProcessor->imgdata.other.shutter);
	printf("[Native] aperture = %f\n", RawProcessor->imgdata.other.aperture);
	env->SetFloatField(obj, aperture_id, RawProcessor->imgdata.other.aperture);
	printf("[Native] focal length = %f\n", RawProcessor->imgdata.other.focal_len);
	env->SetFloatField(obj, focalLength_id, RawProcessor->imgdata.other.focal_len);
	printf("[Native] shot order = %d\n", RawProcessor->imgdata.other.shot_order);
	env->SetIntField(obj, shotOrder_id, RawProcessor->imgdata.other.shot_order);
	printf("[Native] timestamp = %d\n", RawProcessor->imgdata.other.timestamp / 1000);
	time_con = 1000L * (long)RawProcessor->imgdata.other.timestamp;
	date = env->NewObject(date_class, date_init, time_con);

	fflush(stdout);

	previewBitmap = env->NewObject(previewBitmap_class, previewBitmap_init);

	// Getting bitmap field ids
	jfieldID r_id, g_id, b_id, width_id, height_id;
	r_id = env->GetFieldID(previewBitmap_class, "r", "J");
	g_id = env->GetFieldID(previewBitmap_class, "g", "J");
	b_id = env->GetFieldID(previewBitmap_class, "b", "J");
	width_id = env->GetFieldID(previewBitmap_class, "width", "I");
	height_id = env->GetFieldID(previewBitmap_class, "height", "I");

	PreviewBitmap thumb;

	// Extracting the picture

	image = RawProcessor->dcraw_make_mem_image(&ret);
	if (image == 0)
	{
		goto end;
	}

	printf("%d\n", image->type);

	if (image->type == LIBRAW_IMAGE_JPEG)
	{
		decode_jpeg(thumb, image->data, image->data_size);
	}
	else
	{
		decode_plain(thumb, image->width, image->height, image->data, image->data_size);
	}

	// Setting field values
	env->SetIntField(previewBitmap, width_id, thumb.width);
	env->SetIntField(previewBitmap, height_id, thumb.height);
	env->SetLongField(previewBitmap, r_id, (jlong)(thumb.r));
	env->SetLongField(previewBitmap, g_id, (jlong)(thumb.g));
	env->SetLongField(previewBitmap, b_id, (jlong)(thumb.b));

	env->SetObjectField(obj, thumbnail_id, previewBitmap);
	env->SetObjectField(obj, description_id, env->NewStringUTF(RawProcessor->imgdata.other.desc));
	env->SetObjectField(obj, artist_id, env->NewStringUTF(RawProcessor->imgdata.other.artist));
	env->SetObjectField(obj, cameraMaker_id, env->NewStringUTF(RawProcessor->imgdata.idata.make));
	env->SetObjectField(obj, cameraModel_id, env->NewStringUTF(RawProcessor->imgdata.idata.model));
	env->SetObjectField(obj, timeStamp_id, date);


	/*res->flip = RawProcessor.imgdata.sizes.flip;	// 0 - no rotation; 3 - 180-deg rotation; 5 - 90-deg counterclockwise, 6 - 90-deg clockwise

	// Extracting the main data
	res->camera_maker = RawProcessor.imgdata.idata.make;
	res->camera_model = RawProcessor.imgdata.idata.model;

	// Extracting the other data
	res->aperture = RawProcessor.imgdata.other.aperture;
	res->shutter = RawProcessor.imgdata.other.shutter;
	res->focal_len = RawProcessor.imgdata.other.focal_len;
	res->iso_speed = RawProcessor.imgdata.other.iso_speed;
	res->shot_order = RawProcessor.imgdata.other.shot_order;
	res->timestamp = RawProcessor.imgdata.other.timestamp;

	res->artist = new char[64];
	strcpy(res->artist, RawProcessor.imgdata.other.artist);
	res->desc = new char[512];
	strcpy(res->desc, RawProcessor.imgdata.other.desc);
	res->gpsdata = new unsigned[32];
	memcpy(res->gpsdata, RawProcessor.imgdata.other.gpsdata, 32 * sizeof(unsigned int));*/


	RawProcessor->recycle();   // just for show this call...
	                           // use it if you want to load something else


end:
	if (image != NULL) LibRaw::dcraw_clear_mem(image);
	if (RawProcessor != NULL) delete RawProcessor;
	env->ReleaseStringUTFChars(filename, fn);
	if (ret != LIBRAW_SUCCESS)
	{
		throw_libraw_exception(env, ret);
	}
}

/*
 *
 * Compiled from "RawImageDescription.java"
class com.cateye.core.native_.RawImageDescription extends com.cateye.core.ImageDescription{
protected com.cateye.core.IPreviewBitmap thumbnail;
  Signature: Lcom/cateye/core/IPreviewBitmap;
protected float isoSpeed;
  Signature: F
protected float shutter;
  Signature: F
protected float aperture;
  Signature: F
protected float focalLength;
  Signature: F
protected java.util.Date timestamp;
  Signature: Ljava/util/Date;
protected int shotOrder;
  Signature: I
protected java.lang.String description;
  Signature: Ljava/lang/String;
protected java.lang.String artist;
  Signature: Ljava/lang/String;
protected java.lang.String cameraMaker;
  Signature: Ljava/lang/String;
protected java.lang.String cameraModel;
  Signature: Ljava/lang/String;
protected int flip;
  Signature: I
com.cateye.core.native_.RawImageDescription();
  Signature: ()V
protected void setDate(int);
  Signature: (I)V
public com.cateye.core.IPreviewBitmap getThumbnail();
  Signature: ()Lcom/cateye/core/IPreviewBitmap;
public float getIsoSpeed();
  Signature: ()F
public float getShutter();
  Signature: ()F
public float getAperture();
  Signature: ()F
public float getFocalLength();
  Signature: ()F
public java.util.Date getTimestamp();
  Signature: ()Ljava/util/Date;
public int getShotOrder();
  Signature: ()I
public java.lang.String getDescription();
  Signature: ()Ljava/lang/String;
public java.lang.String getArtist();
  Signature: ()Ljava/lang/String;
public java.lang.String getCameraMaker();
  Signature: ()Ljava/lang/String;
public java.lang.String getCameraModel();
  Signature: ()Ljava/lang/String;
public int getFlip();
  Signature: ()I
public void dispose();
  Signature: ()V
public native void loadFromFile(java.lang.String);
  Signature: (Ljava/lang/String;)V
static {};
  Signature: ()V
}*/

