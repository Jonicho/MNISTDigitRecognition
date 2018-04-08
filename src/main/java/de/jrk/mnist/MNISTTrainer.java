package de.jrk.mnist;

import de.jrk.mnist.data.MNISTLoader;
import de.jrk.mnist.data.TrainImage;
import de.jrk.neuralnetwork.NeuralNetwork;
import de.jrk.neuralnetwork.training.BackpropagationTrainer;

public class MNISTTrainer {
    private NeuralNetwork nn;
    private BackpropagationTrainer bpt;

    public MNISTTrainer() {
        nn = new NeuralNetwork(784, 128, 64, 10);
        nn.randomize(1);
        bpt = new BackpropagationTrainer(nn);
        bpt.setLearningRate(0.01);
        System.out.println("Loading train set...");
        for (TrainImage tImage : MNISTLoader.loadSet(MNISTLoader.TRAIN_SET_PATH, 6000)) {
            bpt.addTrainingData(tImage.getTrainingData());
        }
        System.out.println("Loading test set...");
        for (TrainImage tImage : MNISTLoader.loadSet(MNISTLoader.TEST_SET_PATH, 1000)) {
            bpt.addValidationData(tImage.getTrainingData());
        }
        if (bpt.getTrainingData().size() == 0 || bpt.getValidationData().size() == 0) {
            System.exit(0);
        }
        System.out.println("Loading done.");
    }

    public void train() {
        System.out.print("Training... ");
        bpt.train();
        System.out.printf("Loss: %f, validation loss: %f%n", bpt.getLoss(), bpt.getValidationLoss());
    }

    public NeuralNetwork getNeuralNetwork() {
        return nn;
    }

    public static void main(String[] args) {
        MNISTTrainer trainer = new MNISTTrainer();
        while (true)
            trainer.train();
    }
}