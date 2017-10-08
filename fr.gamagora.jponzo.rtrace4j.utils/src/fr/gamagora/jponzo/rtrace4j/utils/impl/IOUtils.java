package fr.gamagora.jponzo.rtrace4j.utils.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtils {
	private static String MAGIC_NUMBER = "P3";
	private static String NB_COLORS = "255";

	/**
	 * clamp the given value according to the given boundaries
	 * Warning if the user invert the min & max values, auto correction occurs
	 * @param min the inf boundary
	 * @param max the sup boundary
	 * @param value the value to clamp
	 * @return the clamped given value
	 */
	public static float clamp(float min, float max, float value) {
		//Auto correct boundaries if needed
		if (min > max) {
			float tmp = min;
			min = max;
			max = tmp;
		}
		
		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		}
		return value;
	}
	
	/**
	 * Save image table with the given filname
	 * @param fileName the given filename
	 * @param imgTable the image table to save
	 * @param w the width of image
	 * @param h the heigth of image
	 */
	public static void saveImg(String fileName, int[][][] imgTable, int w, int h) {
		//Ensure consistency
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int[] pixelColor = imgTable[i][j];
				if (pixelColor.length != 3) {
					System.out.println("Inconsistant image Table");
					System.exit(-1);
				} else if (pixelColor[0] < 0
						|| pixelColor[0] > 255) {
					pixelColor[0] = (int) IOUtils.clamp(0, 255, pixelColor[0]);
				} else if (pixelColor[1] < 0
						|| pixelColor[1] > 255) {
					pixelColor[1] = (int) IOUtils.clamp(0, 255, pixelColor[1]);
				} else if (pixelColor[2] < 0
						|| pixelColor[2] > 255) {
					pixelColor[2] = (int) IOUtils.clamp(0, 255, pixelColor[2]);
				}
			}
		}


		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);

			//write header
			bw.write(MAGIC_NUMBER + " \n");
			bw.write(w + " " + h + " \n");
			bw.write(NB_COLORS + " \n");

			//write content
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					bw.write(imgTable[i][j][0] + " " + imgTable[i][j][1] + " " + imgTable[i][j][2] + " \n");
				}
			}
			
			//close file
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
