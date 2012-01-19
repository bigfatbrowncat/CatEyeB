#include <stdio.h>
#include <string.h>

#include <libraw.h>
#include <jpeglib.h>
#include <bitmaps.h>

#include <com/cateye/core/native_/RawImageDescription.h>

/*

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
*/
