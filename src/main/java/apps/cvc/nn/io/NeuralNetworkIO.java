package apps.cvc.nn.io;

import apps.cvc.nn.Matrix;
import apps.cvc.nn.NeuralNetwork;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetworkIO {

    //todo : safe write
    public static void write(NeuralNetwork neuralNetwork) {
        write("weightsIH", neuralNetwork.getWeightsIH());
        write("biasesH", neuralNetwork.getBiasH());
        write("weightsHO", neuralNetwork.getWeightsHO());
        write("biasesO", neuralNetwork.getBiasO());
    }

    private static void write(String name, Matrix matrix) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            String.format("res/nn/%s.txt", name)));
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

    public static NeuralNetwork read() {
        return new NeuralNetwork(read("weightsIH"), read("biasesH"), read("weightsHO"), read("biasesO"));
    }

    private static Matrix read(String name) {
        Matrix matrix = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(
                            String.format("res/nn/%s.txt", name)));
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
