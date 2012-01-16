package com.cateye.core.native_;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.cateye.core.NativeHeapAllocationException;
import com.cateye.core.native_.PreciseBitmap;

public class PreciseBitmapTests {
	@Test(timeout=5000)
	public void test_precisebitmap_outofmemory()
	{
		final int W = 300, H = 300;
		ArrayList<PreciseBitmap> pbmps = new ArrayList<PreciseBitmap>(); 
		boolean outofmem = false;
		
		try
		{
			do
			{
				PreciseBitmap pbmp = new PreciseBitmap();
				pbmp.alloc(W, H);
				pbmps.add(pbmp);
			}
			while (true);
		}
		catch (NativeHeapAllocationException ex)
		{
			System.out.printf("Out of memory!\n");
		}
		
		System.out.printf("%1$d images %2$dx%3$d allocated before out of memory. Freeing\n", pbmps.size(), W, H);
		
		for (int i = 0; i < pbmps.size(); i++)
			pbmps.get(i).free();
		System.out.printf("Everything's free\n");
	}
	
	@Test
	public void test_precisebitmap_init_and_free()
	{
		System.out.printf("Initializing\n");
		PreciseBitmap pbmp = new PreciseBitmap();
		pbmp.alloc(3000, 2000);
		
		assertEquals(3000, pbmp.width);
		assertEquals(2000, pbmp.height);
		
		System.out.printf("Freeing\n");
		pbmp.free();
	}

	@Test
	public void test_precisebitmap_big_leak()
	{
		final int CYCLES = 100; 
		for (int i = 0; i < CYCLES; i++)
		{
			PreciseBitmap pbmp = new PreciseBitmap();
			pbmp.alloc(1000, 1000);
			
			PreciseBitmap pbmp2 = (PreciseBitmap)pbmp.clone();
			pbmp.free();
			pbmp2.free();

			if (i % (CYCLES / 100) == 0)
				System.out.println((i / (CYCLES / 100)) + "%");
		}
	}
	
	@Test
	public void test_precisebitmap_small_leak()
	{
		try
		{
			final int CYCLES = 100000; 
			for (int i = 0; i < CYCLES; i++)
			{
				PreciseBitmap pbmp = new PreciseBitmap();
				pbmp.alloc(30, 30);
				
				PreciseBitmap pbmp2 = (PreciseBitmap)pbmp.clone();
				pbmp.free();
				pbmp2.free();
				
				if (i % (CYCLES / 100) == 0)
					System.out.println((i / (CYCLES / 100)) + "%");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
