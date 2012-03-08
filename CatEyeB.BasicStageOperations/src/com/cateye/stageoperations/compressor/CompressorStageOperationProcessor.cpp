#include <com/cateye/stageoperations/compressor/CompressorStageOperationProcessor.h>
#include <colorlib.h>
#include <bitmaps.h>
#include <jni.h>
#include <math.h>
#include <mem.h>

#define DEBUG_INFO printf("%d\n", __LINE__);fflush(stdout);

template <typename T> class arr2
{
private:
	T* data;
	int width, height;
public:
	int getWidth() const { return width; }
	int getHeight() const { return height; }

	const T& operator () (int i, int j) const
	{
		//printf("c");
		return data[j * width + i];
	}
	T& operator () (int i, int j)
	{
		//printf("n");
		return data[j * width + i];
	}

	arr2<T>& operator = (const arr2<T>& other)
	{
		if (this != &other)
		{
			delete[] data;

			width = other.width;
			height = other.height;
			data = new T[width * height];
			memcpy(data, other.data, width * height * sizeof(T));
		}
		return *this;
	}

	arr2(int width, int height) : width(width), height(height)
	{
		data = new T[width * height];
	}
	virtual ~arr2()
	{
		delete [] data;
	}
};

arr2<float> Upsample2(const arr2<float>& Q, int new_w, int new_h)
{
	int w = Q.getWidth(), h = Q.getHeight();

	// Scaling
	arr2<float> Q2(new_w, new_h);
	for (int i = 0; i < new_w; i++)
	for (int j = 0; j < new_h; j++)
	{
		int i2 = i;
		if (i2 / 2 >= w) i2 = 2 * w - 1;
		int j2 = j;
		if (j2 / 2 >= h) j2 = 2 * h - 1;

		Q2(i, j) = Q(i2 / 2, j2 / 2);
	}

	// Blurring
	arr2<float> Q22(new_w, new_h);
	for (int i = 0; i < new_w; i++)
	for (int j = 0; j < new_h; j++)
	{
		Q22(i, j) = 0;
		float diver = 0;

		float mix_k = 0.5;

		if (i > 0 && j > 0)
		{
			diver += 0.125f * mix_k;
			Q22(i, j) += 0.125f * mix_k * Q2(i - 1, j - 1);
		}

		if (i > 0 && j < new_h - 1)
		{
			diver += 0.125f * mix_k;
			Q22(i, j) += 0.125f * mix_k * Q2(i - 1, j + 1);
		}

		if (i < new_w - 1 && j > 0)
		{
			diver += 0.125f * mix_k;
			Q22(i, j) += 0.125f * mix_k * Q2(i + 1, j - 1);
		}

		if (i < new_w - 1 && j < new_h - 1)
		{
			diver += 0.125f * mix_k;
			Q22(i, j) += 0.125f * mix_k * Q2(i + 1, j + 1);
		}

		if (i > 0)
		{
			diver += 0.25f * mix_k;
			Q22(i, j) += 0.25f * mix_k * Q2(i - 1, j);
		}

		if (i < new_w - 1)
		{
			diver += 0.25f * mix_k;
			Q22(i, j) += 0.25f * mix_k * Q2(i + 1, j);
		}

		if (j > 0)
		{
			diver += 0.25f * mix_k;
			Q22(i, j) += 0.25f * mix_k * Q2(i, j - 1);
		}

		if (j < new_h - 1)
		{
			diver += 0.25f * mix_k;
			Q22(i, j) += 0.25f * mix_k * Q2(i, j + 1);
		}

		diver += 1;
		Q22(i, j) += Q2(i, j);

		Q22(i, j) /= diver;
	}

	return Q22;
}

JNIEXPORT void JNICALL Java_com_cateye_stageoperations_compressor_CompressorStageOperationProcessor_process
  (JNIEnv * env, jobject obj, jobject params, jobject bitmap, jobject listener)
{
	arr2<float> afl(50, 70);
	for (int i = 0; i < afl.getWidth(); i++)
	for (int j = 0; j < afl.getHeight(); j++)
	{
		afl(i, j) = i + j;
	}

	arr2<float> afl2(1, 1);
	//for (int i = 0; i < 2; i++)
	{
		afl2 = Upsample2(afl, 100, 140);
	}

	for (int j = 0; j < 5; j++)
	{
		for (int i = 0; i < 5; i++)
		{
			printf("a[%d, %d] = %f, ", i, j,  afl2(i, j));
		}
		printf("\n");
	}
}
