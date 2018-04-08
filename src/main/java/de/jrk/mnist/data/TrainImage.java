package de.jrk.mnist.data;

import de.jrk.neuralnetwork.Matrix;

public class TrainImage {
	private byte label;
	private short[] pixels;
	private Matrix[] trainingData;

	public TrainImage(byte label, short[] pixels) {
		this.label = label;
		this.pixels = pixels;
		trainingData = new Matrix[2];
		double[] doublePixels = new double[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			doublePixels[i] = pixels[i] / 255.0;
		}
		trainingData[0] = Matrix.from2DArray(doublePixels);
		trainingData[1] = new Matrix(10, 1).map((x, i, j) -> i == label ? 1 : 0);
	}

	public byte getLabel() {
		return label;
	}

	public short[] getPixels() {
		return pixels;
	}

	public Matrix[] getTrainingData() {
		return trainingData;
	}
}