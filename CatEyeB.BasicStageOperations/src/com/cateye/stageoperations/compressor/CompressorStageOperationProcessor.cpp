#include <com/cateye/stageoperations/compressor/CompressorStageOperationProcessor.h>
#include <colorlib.h>
#include <bitmaps.h>
#include <jni.h>
#include <math.h>
#include <mem.h>

#include <vector>

using namespace std;

#define DEBUG_INFO printf("%d\n", __LINE__);fflush(stdout);

template <typename T> struct counted_link
{
	T* link;
	int counter;
};

template <typename T> class arr2
{
private:
	counted_link<T>* data;
	int width, height;
public:
	int getWidth() const { return width; }
	int getHeight() const { return height; }
	T* getData() { return data; }

	inline const T& operator () (int i, int j) const
	{
		if (i >= width)
		{
			DEBUG_INFO
			throw 1;
		}
		if (j >= height)
		{
			DEBUG_INFO
			throw 1;
		}

		if (i < 0)
		{
			DEBUG_INFO
			throw 1;
		}
		if (j < 0)
		{
			DEBUG_INFO
			throw 1;
		}

		return data->link[j * width + i];
	}
	inline T& operator () (int i, int j)
	{
		if (i >= width)
		{
			DEBUG_INFO
			throw 1;
		}
		if (j >= height)
		{
			DEBUG_INFO
			throw 1;
		}

		if (i < 0)
		{
			DEBUG_INFO
			throw 1;
		}
		if (j < 0)
		{
			DEBUG_INFO
			throw 1;
		}


		return data->link[j * width + i];
	}

	arr2<T>(const arr2<T>& other)
	{
		width = other.width;
		height = other.height;
		data = other.data;
		data->counter ++;
	}

	arr2<T>& operator = (const arr2<T>& other)
	{
		if (this != &other)
		{
			data->counter --;
			if (data->counter == 0)
			{
				delete [] data->link;
				delete data;
			}

			width = other.width;
			height = other.height;
			data = other.data;
			data->counter ++;
		}
		return *this;
	}

	arr2(T* src_data, int width, int height) : width(width), height(height)
	{
		this->data = new counted_link<T>;
		if (this->data == NULL)
		{
			DEBUG_INFO
			throw 1;
		}
		this->data->link = new T[width * height];
		if (this->data->link == NULL)
		{
			DEBUG_INFO
			throw 1;
		}
		memcpy(this->data->link, src_data, width * height * sizeof(T));
	}

	arr2(int width, int height) : width(width), height(height)
	{
		this->data = new counted_link<T>;
		if (this->data == NULL)
		{
			DEBUG_INFO
			throw 1;
		}
		DEBUG_INFO
		this->data->link = new T[width * height];
		if (this->data->link == NULL)
		{
			DEBUG_INFO
			throw 1;
		}
		DEBUG_INFO
	}
	virtual ~arr2()
	{
		data->counter --;
		if (data->counter == 0)
		{
			delete [] data->link;
			delete data;
		}
	}
};

arr2<float> Upsample2(const arr2<float> Q, int new_w, int new_h)
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

arr2<float> BuildPhi(arr2<float> H, double alpha, double beta, double noise_gate)
{
	int Hw = H.getWidth();
	int Hh = H.getHeight();

	DEBUG_INFO

	vector<int> ww, hh;
	int divides = 0, wt = Hw, ht = Hh;
	ww.push_back(wt); hh.push_back(ht);
	while (wt > 1 && ht > 1 && divides < 5)
	{
		wt /= 2; ht /= 2;
		ww.push_back(wt); hh.push_back(ht);
		divides ++;
	}

	DEBUG_INFO

	// Building H0
	arr2<float> H_cur(Hw, Hh);
	for (int i = 0; i < Hw; i++)
	for (int j = 0; j < Hh; j++)
	{
		H_cur(i, j) = H(i, j);
	}

	DEBUG_INFO

	printf("divides = %d, (%d, %d)\n", divides, Hw, Hh);

	// Building phi_k
	vector<arr2<float> > phi;
	for (int k = 0; k <= divides; k++)		// k is the index of H_cur
	{
		DEBUG_INFO

		printf("%d\n", k); fflush(stdout);

		float avg_grad = 0;

		int w = (int)(Hw / pow(2, k));
		int h = (int)(Hh / pow(2, k));

		printf("%d, %d\n", w, h); fflush(stdout);

		if (k > 0)
		{
			DEBUG_INFO
			// Calculating the new H_cur
			arr2<float> H_cur_new(w, h);
			DEBUG_INFO
			for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
			{
				double Hcn_tmp = H_cur(2 * i, 2 * j);
				int q = 1;

				if (2 * i + 1 < Hw)
				{
					Hcn_tmp += H_cur(2 * i + 1, 2 * j);
					q++;
				}

				if (2 * j + 1 < Hh)
				{
					Hcn_tmp += H_cur(2 * i, 2 * j + 1);
					q++;
				}

				if (2 * i + 1 < Hw && 2 * j + 1 < Hh)
				{
					Hcn_tmp += H_cur(2 * i + 1, 2 * j + 1);
					q++;
				}

				H_cur_new(i, j) = (float)(Hcn_tmp / q);
			}
			DEBUG_INFO
			H_cur = H_cur_new;
		}
		DEBUG_INFO

		// Calculating grad_H_cur
		arr2<float> grad_H_cur_x(w, h), grad_H_cur_y(w, h);
		for (int i = 0; i < w; i++)
		for (int j = 0; j < h; j++)
		{
			if (w == 1 || h == 1)
			{
				grad_H_cur_x(i, j) = 0;
				grad_H_cur_y(i, j) = 0;
			}
			else
			{
				if (i < w - 1 && i > 0)
					grad_H_cur_x(i, j) = (float)((H_cur(i + 1, j) - H_cur(i - 1, j)) / pow(2, k + 1));
				else if (i == 0)
					grad_H_cur_x(i, j) = (float)((H_cur(i + 1, j) - H_cur(i, j)) / pow(2, k));
				else
					grad_H_cur_x(i, j) = (float)((H_cur(i, j) - H_cur(i - 1, j)) / pow(2, k));

				if (j < h - 1 && j > 0)
					grad_H_cur_y(i, j) = (float)((H_cur(i, j + 1) - H_cur(i, j - 1)) / pow(2, k + 1));
				else if (j == 0)
					grad_H_cur_y(i, j) = (float)((H_cur(i, j + 1) - H_cur(i, j)) / pow(2, k));
				else
					grad_H_cur_y(i, j) = (float)((H_cur(i, j) - H_cur(i, j - 1)) / pow(2, k));

				avg_grad += (float)sqrt(grad_H_cur_x(i, j) * grad_H_cur_x(i, j) +
				                        grad_H_cur_y(i, j) * grad_H_cur_y(i, j));
			}
		}
		DEBUG_INFO

		// Calculating phi_k
		avg_grad /= w * h;
		printf("avg_grad = %f\n", avg_grad);
		fflush(stdout);
		arr2<float> phi_k(w, h);
		for (int i = 0; i < w; i++)
		for (int j = 0; j < h; j++)
		{
			double abs_grad_H_cur = sqrt(grad_H_cur_x(i, j) * grad_H_cur_x(i, j) +
			                             grad_H_cur_y(i, j) * grad_H_cur_y(i, j));
			abs_grad_H_cur = 0.001;		// Avoiding zero

			phi_k(i, j) = (float)(pow(abs_grad_H_cur / alpha, beta - 1));

			// nOISE GATE
			float nf_edge = (float)noise_gate * avg_grad;
			phi_k(i, j) *= (float)(1.0 - exp(-pow(abs_grad_H_cur / nf_edge, 0.8)));
		}
		phi.push_back(phi_k);
		DEBUG_INFO
	}
	DEBUG_INFO

	// Building Phi from phi_k
	arr2<float> Phi(phi[phi.size() - 1].getWidth(), phi[phi.size() - 1].getHeight());
	for (int i = 0; i < Phi.getWidth(); i++)
	for (int j = 0; j < Phi.getHeight(); j++)
	{
		Phi(i, j) = 1;
	}
	DEBUG_INFO

	for (int k = divides; k >= 0; k--)
	{
		DEBUG_INFO
		int w = phi[k].getWidth();
		int h = phi[k].getHeight();
		// Multiplying
		DEBUG_INFO
		printf("Phi: %d, %d\n", Phi.getWidth(), Phi.getHeight());
		for (int i = 0; i < w; i++)
		for (int j = 0; j < h; j++)
		{
			//printf("i = %d, j = %d, w = %d, h = %d\n", i, j, w, h);fflush(stdout);
			Phi(i, j) *= phi[k](i, j);
		}

		if (k > 0)
		{
			Phi = Upsample2(Phi, ww[k - 1], hh[k - 1]);
		}
	}
	DEBUG_INFO

	// Extracting the correct size
	arr2<float> Phi_cut(Hw, Hh);
	for (int i = 0; i < Hw; i++)
	for (int j = 0; j < Hh; j++)
	{
		Phi_cut(i, j) = Phi(i, j);
	}
	DEBUG_INFO

	return Phi_cut;
}

//typedef void (*SolutionReporter) (float progress, arr2<float> solution);

bool SolvePoissonNeiman(arr2<float> I0, arr2<float> rho, int steps_max, float stop_dpd);

arr2<float> SolvePoissonNeimanMultiLattice(arr2<float> rho, int steps_max, float stop_dpd)
{
	DEBUG_INFO
	// Making lower resolutions
	int W = rho.getWidth();
	int H = rho.getHeight();

	vector<int> ww, hh;
	DEBUG_INFO

	int divides = 0, wt = W, ht = H;
	ww.push_back(wt); hh.push_back(ht);
	while (wt > 1 && ht > 1 && divides < 5)
	{
		wt /= 2; ht /= 2;
		ww.push_back(wt); hh.push_back(ht);
		divides ++;
	}

	vector<arr2<float> > Rho;
	Rho.push_back(rho);

	DEBUG_INFO
	for (int p = 1; p <= divides; p++)
	{
		int w = ww[p - 1];
		int h = hh[p - 1];

		arr2<float> rho_new(ww[p], hh[p]);
		for (int i = 0; i < w; i++)
		for (int j = 0; j < h; j++)
		{
			if (i / 2 < ww[p] && j / 2 < hh[p])
				rho_new(i / 2, j / 2) += 0.25f * Rho[p - 1](i, j);
		}

		Rho.push_back(rho_new);
	}
	DEBUG_INFO

	arr2<float> I(Rho[divides].getWidth(), Rho[divides].getHeight());
	float old_progress = 0;
	for (int p = divides; p >= 0; p--)
	{
		DEBUG_INFO
		SolvePoissonNeiman(I, Rho[p], steps_max, stop_dpd);
		/* Delegate was here:
		 *
		 * delegate (float progress, float[,] solution)
				{
					if (callback != null)
					{
						float complete_effort = 0;
						for (int q = divides; q > p; q--)
							complete_effort += (float)ww[q] * hh[q];

						float full_effort = complete_effort;
						for (int q = p; q >= 0; q--)
							full_effort += (float)ww[q] * hh[q];

						float new_progress = (complete_effort + ww[p] * hh[p] * progress) / full_effort;
						if (new_progress > old_progress) old_progress = new_progress;

						callback(old_progress, solution);
					}
		*/

		if (p > 0)
		{
			I = Upsample2(I, ww[p - 1], hh[p - 1]);
		}
	}

	DEBUG_INFO
	return I;
}

bool SolvePoissonNeiman(arr2<float> I0, arr2<float> rho, int steps_max, float stop_dpd)
{
	DEBUG_INFO
	int w = rho.getWidth(), h = rho.getHeight();
	arr2<float> I(w + 2, h + 2);
	arr2<float> Inew(w + 2, h + 2);

	DEBUG_INFO
	// Setting initial values
	for (int i = 0; i < w + 2; i++)
	for (int j = 0; j < h + 2; j++)
	{
		int i1 = i;
		if (i == 0) i1 = 1;
		if (i == w + 1) i1 = w;
		int j1 = j;
		if (j == 0) j1 = 1;
		if (j == h + 1) j1 = h;

		I(i, j) = I0(i1 - 1, j1 - 1);
		Inew(i, j) = I0(i1 - 1, j1 - 1);
	}
	DEBUG_INFO

	float delta = 0; float delta_prev = 10000;
	// object delta_lock = new object();
	for (int step = 0; step < steps_max; step ++)
	{
		// *** Horizontal iterations ***

		float my_delta = 0;

		for (int i = 1; i < w + 1; i++)
		{
			// Run, Thomas, run!
			float alpha[h + 3];
			float beta[h + 3];

			alpha[1] = 0.25f; beta[1] = 0.25f * (I(i + 1, 0) + Inew(i - 1, 0));
			for (int j = 1; j < h + 2; j++)
			{
				alpha[j + 1] = 1.0f / (4 - alpha[j]);
				float Fj;
				if (j < h + 1)
					Fj = I(i + 1, j) + Inew(i - 1, j) - 2 * rho(i - 1, j - 1);
				else
					Fj = I(i + 1, j) + Inew(i - 1, j);

				beta[j + 1] = (Fj + beta[j]) / (4.0f - alpha[j]);
			}

			Inew(i, h + 1) = beta[h + 2];

			for (int j = h; j >= 0; j--)
			{
				double iold = I(i, j);
				Inew(i, j) = alpha[j + 1] * Inew(i, j + 1) + beta[j + 1];
				my_delta += (float)fabs(Inew(i, j) - iold);
			}
		}

		delta += my_delta;

		// Restoring Neiman boundary conditions after horizontal iterations
		for (int i = 0; i < w + 2; i++)
		{
			Inew(i, 0) = Inew(i, 1);
			Inew(i, h + 1) = Inew(i, h);
		}
		for (int j = 0; j < h + 2; j++)
		{
			Inew(0, j) = Inew(1, j);
			Inew(w + 1, j) = Inew(w, j);
		}


		// Controlling the constant after horizontal iterations
		float m = 0;
		for (int i = 0; i < w + 2; i++)
		for (int j = 0; j < h + 2; j++)
		{
			m += Inew(i, j);
		}
		m /= (w+2) * (h+2);

		for (int i = 0; i < w + 2; i++)
		for (int j = 0; j < h + 2; j++)
		{
			I(i, j) = Inew(i, j) - m;
		}

		for (int i = 1; i < w + 1; i++)
		for (int j = 1; j < h + 1; j++)
		{
			I0(i - 1, j - 1) = I(i, j);
		}

		delta /= (float)sqrt(w * h);
		float dpd = fabs((delta - delta_prev) / delta);

		// This formula is found experimentally
		float progress = (float)fmin(pow(stop_dpd / (dpd + 0.000001), 0.78), 0.999);

		printf("layer progress: %f\n", progress);
		fflush(stdout);


		if (dpd < stop_dpd)
		{
			printf("!");
			fflush(stdout);
			return true;
		}

		delta_prev = delta;
		delta = 0;
	}

	return false;
}

void Compress(PreciseBitmap bmp, double curve, double noise_gate, double pressure, double contrast, float epsilon, int steps_max)
{
	arr2<float> r_chan(bmp.r, bmp.width, bmp.height);
	arr2<float> g_chan(bmp.g, bmp.width, bmp.height);
	arr2<float> b_chan(bmp.b, bmp.width, bmp.height);

	DEBUG_INFO

	int Hw = bmp.width;
	int Hh = bmp.height;
	arr2<float> H(Hw, Hh);

	// Calculating logarithmic luminosity
	for (int i = 0; i < Hw; i++)
	for (int j = 0; j < Hh; j++)
	{
		double light = sqrt((r_chan(i, j) * r_chan(i, j) +
		                     g_chan(i, j) * g_chan(i, j) +
		                     b_chan(i, j) * b_chan(i, j)) / 3);

		H(i, j) = (float)(log(light + 0.00001));
	}

	DEBUG_INFO

	arr2<float> grad_H_x(Hw, Hh);
	arr2<float> grad_H_y(Hw, Hh);

	DEBUG_INFO

	// Calculating gradient of H
	for (int i = 1; i < Hw - 1; i++)
	for (int j = 1; j < Hh - 1; j++)
	{
		grad_H_x(i, j) = (float)(H(i + 1, j) - H(i, j));
		grad_H_y(i, j) = (float)(H(i, j + 1) - H(i, j));
	}

	DEBUG_INFO

	// Calculating Phi
	arr2<float> Phi = BuildPhi(H, 0.01 * pressure, contrast, noise_gate);

	DEBUG_INFO

	// Calculating G and div_G
	arr2<float> div_G(Hw, Hh);
	for (int i = 0; i < Hw - 1; i++)
	for (int j = 0; j < Hh - 1; j++)
	{
		float G_x_ij = grad_H_x(i, j) * Phi(i, j);
		float G_y_ij = grad_H_y(i, j) * Phi(i, j);

		float G_x_ip1j = grad_H_x(i + 1, j) * Phi(i + 1, j);
		float G_y_ijp1 = grad_H_y(i, j + 1) * Phi(i, j + 1);

		div_G(i, j) = - (G_x_ij - G_x_ip1j + G_y_ij - G_y_ijp1);
	}

	// Preparing the compressor

	double a, b;
	if (curve > 0)
	{
		a = log(2);
		b = log(1.0 + pow(100, fabs(curve * 1.5)));
	}
	else
	{
		b = log(2);
		a = log(1.0 + pow(100, fabs(curve * 1.5)));
	}
	double p = pow(100, curve * 1.5);

	DEBUG_INFO

	arr2<float> I = SolvePoissonNeimanMultiLattice(div_G, steps_max, epsilon);

	DEBUG_INFO

	double kw = H.getWidth() / I.getWidth();
	double kh = H.getHeight() / I.getHeight();

	// Draw it
	for (int i = 0; i < bmp.width; i++)
	for (int j = 0; j < bmp.height; j++)
	{

		double Lold = exp(H(i, j));

		double Lcomp = log(p * (exp(a * Lold) - 1.0) + 1.0) / b;

		int i1 = (int)(fmin (i / kw, I.getWidth() - 1));
		int j1 = (int)(fmin (j / kh, I.getHeight() - 1));

		double L = exp(I(i1, j1)) * Lcomp;

		//lock (this)
		{
			bmp.r[j * bmp.width + i] = (float)(bmp.r[j * bmp.width + i] * L / (Lold + 0.00001));
			bmp.g[j * bmp.width + i] = (float)(bmp.g[j * bmp.width + i] * L / (Lold + 0.00001));
			bmp.b[j * bmp.width + i] = (float)(bmp.b[j * bmp.width + i] * L / (Lold + 0.00001));
		}
	}

}

JNIEXPORT void JNICALL Java_com_cateye_stageoperations_compressor_CompressorStageOperationProcessor_process
  (JNIEnv * env, jobject obj, jobject params, jobject bitmap, jobject listener)
{
	// Getting the class
	jclass cls = env->GetObjectClass(bitmap);

	// Getting field ids
	jfieldID r_id, g_id, b_id, width_id, height_id;
	r_id = env->GetFieldID(cls, "r", "J");
	g_id = env->GetFieldID(cls, "g", "J");
	b_id = env->GetFieldID(cls, "b", "J");
	width_id = env->GetFieldID(cls, "width", "I");
	height_id = env->GetFieldID(cls, "height", "I");

	// Getting the bitmap from JVM
	PreciseBitmap bmp;
	bmp.r = (float*)env->GetLongField(bitmap, r_id);
	bmp.g = (float*)env->GetLongField(bitmap, g_id);
	bmp.b = (float*)env->GetLongField(bitmap, b_id);
	bmp.width = env->GetIntField(bitmap, width_id);
	bmp.height = env->GetIntField(bitmap, height_id);

	// Getting the stage operation parameters
/*	jclass operationClass = env->GetObjectClass(params);
	jfieldID rId, gId, bId;

	rId = env->GetFieldID(operationClass, "r", "D");
	gId = env->GetFieldID(operationClass, "g", "D");
	bId = env->GetFieldID(operationClass, "b", "D");*/

	Compress(bmp, 0.7, 0.05, 1, 0.75, 0.003f, 20000);
	//Compress(bmp, 0.2, 0.01, 0.05, 0.85, 0.001f, 20000);

}
