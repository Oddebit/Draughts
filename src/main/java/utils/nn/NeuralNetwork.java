package utils.nn;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import lombok.Getter;

import java.util.LinkedList;

@Getter
public class NeuralNetwork {

    private final int layers;
    private LinkedList<Matrix> weights = new LinkedList<>();
    private LinkedList<Matrix> biases = new LinkedList<>();
    private final LinkedList<Matrix> parameters = new LinkedList<>();

    private final double learningRate = 0.01;

    public NeuralNetwork(int... parameters) {
        for (int i = 1; i < parameters.length; i++) {
            weights.add(new Matrix(parameters[i], parameters[i - 1]));
            biases.add(new Matrix(parameters[i], 1));
        }
        this.layers = parameters.length - 1;
    }

    public NeuralNetwork(LinkedList<Matrix> weights, LinkedList<Matrix> biases) {
        this.weights = weights;
        this.biases = biases;
        this.layers = biases.size();
    }

    public Double[] predict(double[] Input) {
        parameters.clear();
        parameters.add(Matrix.fromArray(Input));

        for (int i = 0; i < layers; i++) {
            Matrix matrix = Matrix.multiply(weights.get(i), parameters.get(i));
            matrix.add(biases.get(i));
            matrix.sigmoid();

            parameters.add(matrix);
        }
        return parameters.getLast().toArray();
    }

    public void propagateBack(double[] Input, double[] Target) {
        //fixme!!!!
        predict(Input);

        Matrix target = Matrix.fromArray(Target);
        Matrix error = Matrix.subtract(target, parameters.getLast());

        for (int i = layers; i > 0; i--) {
            if (i < layers) {
                Matrix tWeights = Matrix.transpose(weights.get(i));
                error = Matrix.multiply(tWeights, error);
            }
            Matrix gradient = parameters.get(i).dSigmoid();
            gradient.multiply(error);
            gradient.multiply(learningRate);

            Matrix tParameters = Matrix.transpose(parameters.get(i - 1));
            Matrix weightsDelta = Matrix.multiply(gradient, tParameters);

            weights.get(i - 1).add(weightsDelta);
            biases.get(i - 1).add(gradient);

        }
    }
}

