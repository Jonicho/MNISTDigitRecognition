package de.jrk.mnist;

import de.jrk.mnist.data.TrainImage;
import de.jrk.neuralnetwork.Matrix;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Gui extends PApplet {
    PGraphics g;
    PImage smallImage;
    MNISTTrainer trainer;

    public static void main(String[] args) {
        PApplet.main("de.jrk.mnist.Gui");
    }

    @Override
    public void setup() {
        new Thread(() -> {
            trainer = new MNISTTrainer();
            while (true)
                trainer.train();
        }).start();
        size(560, 448);
        textSize(20);
        g = createGraphics(448, 448);
        g.beginDraw();
        g.background(0);
        g.stroke(255);
        g.strokeWeight(40);
        g.endDraw();
    }

    @Override
    public void draw() {
        background(0);
        g.beginDraw();
        if (mouseButton == LEFT)
            g.line(mouseX, mouseY, pmouseX, pmouseY);
        else if (mouseButton == RIGHT) {
            g.fill(0);
            g.noStroke();
            g.rect(0, 0, width, height);
            g.stroke(255);
        }
        g.endDraw();
        image(g, 0, 0, 448, 448);
        smallImage = g.get();
        smallImage.resize(28, 28);
        image(smallImage, 448, 0, 112, 112);
        short[] pixels = new short[784];
        smallImage.loadPixels();
        for (int i = 0; i < smallImage.pixels.length; i++) {
            pixels[i] = (short) smallImage.pixels[i];
        }
        TrainImage tImage = new TrainImage((byte) 0, pixels);
        Matrix output = trainer == null ? new Matrix(10, 1)
                : trainer.getNeuralNetwork().feedforward(tImage.getTrainingData()[0]);
        fill(255);
        double max = 0;
        int maxValue = -1;
        for (int i = 0; i < 10; i++) {
            text(String.format("%d: %.4f", i, output.get(i, 0)), 448, i * 20 + 20 + 112);
            if (output.get(i, 0) > max) {
                max = output.get(i, 0);
                maxValue = i;
            }
        }
        text(maxValue, 448, 350);
        stroke(255);
        line(448, 0, 448, 448);
        line(448, 112, 560, 112);
    }
}