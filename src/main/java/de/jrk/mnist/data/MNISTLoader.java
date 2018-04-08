package de.jrk.mnist.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MNISTLoader {
    public static final String TRAIN_SET_PATH = "data/mnist_train.csv";
    public static final String TEST_SET_PATH = "data/mnist_test.csv";

    public static ArrayList<TrainImage> loadSet(String file, int amount) {
        return loadSet(file, null, amount);
    }

    public static ArrayList<TrainImage> loadSet(String filePath, ArrayList<TrainImage> setList, int amount) {
        try {
            if (setList == null) {
                setList = new ArrayList<TrainImage>();
            }
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Dataset file missing! (" + file.getAbsolutePath() + ") Download: https://pjreddie.com/projects/mnist-in-csv/");
                return setList;
            }
            Scanner s = new Scanner(file);
            setList.clear();
            int k = 0;
            while (s.hasNextLine() && k++ < amount) {
                String[] line = s.nextLine().split(",");
                if (line.length != 785) {
                    throw new IllegalStateException("Line has an illegal amount of values (" + line.length + ")");
                }
                short[] pixels = new short[784];
                for (int i = 0; i < pixels.length; i++) {
                    pixels[i] = Short.parseShort(line[i + 1]);
                }
                setList.add(new TrainImage(Byte.parseByte(line[0]), pixels));
            }
            s.close();
            return setList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}