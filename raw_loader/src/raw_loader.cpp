#include <math.h>
#include <stdio.h>

#include <bitmaps.h>
#include <libraw.h>
#include <jpeglib.h>

#include "raw_loader.h"

// ** Private functions **

PreviewBitmap decode_jpeg(unsigned char* buffer, unsigned long size)
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
	PreviewBitmap res = PreviewBitmap_Create(cinfo.output_width, cinfo.output_height);
	res.width = cinfo.output_width;
	res.height = cinfo.output_height;
	for (int i = 0; i < cinfo.output_width * cinfo.output_height; i++)
	{
		res.r[i] = image[3 * i];
		res.g[i] = image[3 * i + 1];
		res.b[i] = image[3 * i + 2];
	}

	/*
	FILE* file = fopen("c:\\test.ppm", "wb");

	fprintf(file, "P6\n%i %i 255\n", cinfo.output_width, cinfo.output_height);
	fwrite(image, 1, cinfo.output_width * cinfo.output_height * cinfo.num_components, file);

	fclose(file);
	*/
	free(image);
	return res;
}

int internal_callback(void *d, enum LibRaw_progress p, int iteration, int expected)
{
	ExtractingProgressReporter* callback = (ExtractingProgressReporter*)d;

	if ((*callback)((float)((log2(p) + (float)iteration / expected) / log2(LIBRAW_PROGRESS_STRETCH)) ))
    	return 0;	// Continue
    else
    	return 1;	// Cancel

}

int convert_libraw_code(int libraw_code)
{
	switch (libraw_code)
	{
	case LIBRAW_SUCCESS:
		return EXTRACTING_RESULT_OK;
    case LIBRAW_UNSUFFICIENT_MEMORY:
    	return EXTRACTING_RESULT_UNSUFFICIENT_MEMORY;
    case LIBRAW_DATA_ERROR:
    	return EXTRACTING_RESULT_DATA_ERROR;
    case LIBRAW_IO_ERROR:
    	return EXTRACTING_RESULT_IO_ERROR;
    case LIBRAW_CANCELLED_BY_CALLBACK:
    	return EXTRACTING_RESULT_CANCELLED_BY_CALLBACK;
    case LIBRAW_BAD_CROP:
    	return EXTRACTING_RESULT_BAD_CROP;
    default:
    	return EXTRACTING_RESULT_UNKNOWN;
	}
}

// ** Public functions **

void ExtractedRawImage_LoadFromFile(char* filename,
			bool divide_by_2,
			ExtractingProgressReporter* progress_reporter,
			ExtractingResultReporter* result_reporter)
{
	//res->data = 0;	// data = 0 means "error during processing"

	LibRaw& RawProcessor = *(new LibRaw());

	RawProcessor.set_progress_handler(internal_callback, (void*)progress_reporter);

	RawProcessor.imgdata.params.gamm[0] = RawProcessor.imgdata.params.gamm[1] =
	                                      RawProcessor.imgdata.params.no_auto_bright = 1;
	RawProcessor.imgdata.params.output_bps = 16;
	RawProcessor.imgdata.params.highlight  = 9;
	RawProcessor.imgdata.params.threshold  = (float)200;

	if (divide_by_2)
	{
		RawProcessor.imgdata.params.half_size         = 1;
		RawProcessor.imgdata.params.four_color_rgb    = 1;
	}

	int ret = RawProcessor.open_file(filename, 1024 * 1024 * 1024);
	if (LIBRAW_FATAL_ERROR(ret))
	{
		if (result_reporter != NULL) (*result_reporter)(convert_libraw_code(ret), NULL);
		return;
	}
	if (LIBRAW_FATAL_ERROR(ret = RawProcessor.unpack()))
	{
		if (result_reporter != NULL) (*result_reporter)(convert_libraw_code(ret), NULL);
		return;
	}
	if (LIBRAW_FATAL_ERROR(ret = RawProcessor.dcraw_process()))
	{
		if (result_reporter != NULL) (*result_reporter)(convert_libraw_code(ret), NULL);
		return;
	}

    libraw_processed_image_t *image = RawProcessor.dcraw_make_mem_image(&ret);
    if (image == 0)
    {
    	if (result_reporter != NULL) (*result_reporter)(EXTRACTING_RESULT_UNSUFFICIENT_MEMORY, NULL);
    	return;
    }

    PreciseBitmap* res = new PreciseBitmap();
    *res = PreciseBitmap_Create(image->width, image->height);

    if (image->bits == 8)
    {
    	for (int i = 0; i < res->width * res->height; i++)
    	{
    		res->r[i] = ((float)image->data[3 * i]) / 255;
    		res->g[i] = ((float)image->data[3 * i + 1]) / 255;
    		res->b[i] = ((float)image->data[3 * i + 2]) / 255;
    	}
    }
    else if (image->bits == 16)
    {
    	unsigned short* pus = (unsigned short*)image->data;
    	for (int i = 0; i < res->width * res->height; i++)
    	{
    		res->r[i] = ((float)pus[3 * i]) / 65535;
    		res->g[i] = ((float)pus[3 * i + 1]) / 65535;
    		res->b[i] = ((float)pus[3 * i + 2]) / 65535;
    	}
    }
    else
    {
    	PreciseBitmap_Destroy(*res);
    	if (result_reporter != NULL) (*result_reporter)(EXTRACTING_RESULT_UNSUPPORTED_FORMAT, NULL);
    	return;
    }

	RawProcessor.recycle(); // just for show this call

	if (result_reporter != NULL) (*result_reporter)(EXTRACTING_RESULT_OK, res);
	delete res;
	delete &RawProcessor;
}

ExtractedDescription* ExtractedDescription_Create()
{
	ExtractedDescription* res = new ExtractedDescription();
	return res;
}

int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res)
{
	LibRaw& RawProcessor = *(new LibRaw());

	int ret = RawProcessor.open_file(filename, 1024 * 1024 * 1024);
	if (ret != LIBRAW_SUCCESS)
	{
		fprintf(stderr,"Cannot open the data buffer: %s\n", libraw_strerror(ret));
		return 1; // no recycle b/c open_file will recycle itself
	}
	if ((ret = RawProcessor.unpack_thumb()) != LIBRAW_SUCCESS)
	{
		fprintf(stderr,"Cannot unpack the thumbnail data: %s\n", libraw_strerror(ret));
		return 2;
	}
    libraw_processed_image_t *image = RawProcessor.dcraw_make_mem_thumb(&ret);
    if (image == 0)
    {
    	return 3;
    }

    // Extracting the picture

    if (image->type == LIBRAW_IMAGE_JPEG)
    {
    	PreviewBitmap decoded = decode_jpeg(image->data, image->data_size);
    }
    else
    {
    	// TODO: implement plain bitmap decoding
    	throw 1;
    }

    /*res->thumbnail_data = image->data;
    res->data_size = image->data_size;
    res->libraw_image = image;
    res->is_jpeg = image->type == LIBRAW_IMAGE_JPEG;
    */

    res->flip = RawProcessor.imgdata.sizes.flip;	// 0 - no rotation; 3 - 180-deg rotation; 5 - 90-deg counterclockwise, 6 - 90-deg clockwise

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
    memcpy(res->gpsdata, RawProcessor.imgdata.other.gpsdata, 32 * sizeof(unsigned int));


	RawProcessor.recycle(); // just for show this call

	LibRaw::dcraw_clear_mem(image);
	delete &RawProcessor;

	return 0;
}

void ExtractedDescription_Destroy(ExtractedDescription* description)
{
	delete[] description->artist;
	delete[] description->desc;
	delete[] description->gpsdata;
	PreviewBitmap_Destroy(description->thumbnail);
	delete description;
}
