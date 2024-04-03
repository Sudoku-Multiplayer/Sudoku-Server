package io.github.himanshusajwan911.sudokuserver.util;

import java.util.Random;

public class Util {

	/**
	 * Generates a random integer within the specified range.
	 *
	 * @param lowerBound the lower bound (inclusive) of the range.
	 * @param upperBound the upper bound (exclusive) of the range.
	 * 
	 * @return a random integer within the specified range.
	 */
	public static int getRandomInt(int lowerBound, int upperBound) {

		Random random = new Random();

		return random.nextInt(upperBound - lowerBound) + lowerBound;
	}

	public static int[][] clone2DArray(int[][] source) {
		int rowLength = source.length;

		int[][] newArray = new int[rowLength][];

		for (int i = 0; i < rowLength; ++i) {
			int columnLength = source[i].length;
			int[] newRow = new int[columnLength];

			for (int j = 0; j < columnLength; ++j) {
				newRow[j] = source[i][j];
			}

			newArray[i] = newRow;
		}

		return newArray;
	}

}
