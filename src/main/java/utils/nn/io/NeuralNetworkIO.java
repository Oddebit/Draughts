package utils.nn.io;

import utils.nn.Matrix;
import utils.nn.NeuralNetwork;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetworkIO {

    //todo : safe write
    public static void write(NeuralNetwork neuralNetwork) {
        int layers = neuralNetwork.getLayers();
        LinkedList<Matrix> weights = neuralNetwork.getWeights();
        LinkedList<Matrix> biases = neuralNetwork.getBiases();

        String folder = biases.stream()
                .map(Matrix::getRows)
                .map(String::valueOf)
                .limit(layers - 1)
                .reduce("res/nn/", (s1, s2) -> s1 + "_" + s2);

        for (int i = 0; i < layers; i++) {
            write(String.format("%s/w%d.txt", folder, i + 1), weights.get(i));
            write(String.format("%s/b%d.txt", folder, i + 1), biases.get(i));
        }
    }

    private static void write(String file, Matrix matrix) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));
            double[][] data = matrix.getData();
            for (double[] datum : data) {
                for (double v : datum) {
                    writer.write(v + ";");
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork read(int... parameters) {
        int length = parameters.length - 1;
        String folder = Arrays.stream(parameters)
                .limit(length)
                .skip(1)
                .mapToObj(String::valueOf)
                .reduce("res/nn/", (s1, s2) -> s1 + "_" + s2);

        LinkedList<Matrix> weights = new LinkedList<>();
        LinkedList<Matrix> biases = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            weights.add(read(String.format("%s/w%d.txt", folder, i + 1)));
            biases.add(read(String.format("%s/b%d.txt", folder, i + 1)));
        }
        return new NeuralNetwork(weights, biases);
    }

    private static Matrix read(String file) {
        Matrix matrix = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            List<double[]> dataList = new LinkedList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(
                        Arrays.stream(line.split(";"))
                                .mapToDouble(Double::parseDouble)
                                .toArray());
            }
            reader.close();
            double[][] data = dataList.toArray(new double[0][]);
            matrix = Matrix.fromArray(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}
