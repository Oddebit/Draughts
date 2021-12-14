package apps.cvc;

import apps.cvc.nn.NeuralNetwork;
import apps.cvc.nn.io.NeuralNetworkIO;
import lombok.Getter;

@Getter
public class BoardEvaluator {

    private final NeuralNetwork neuralNetwork;

    public BoardEvaluator() {
        this.neuralNetwork = NeuralNetworkIO.read();
    }

    public double evaluateBoard(Integer[] input) {
        return neuralNetwork.predict(intToDouble(input))[0];
    }

    public void learn(Integer[] input, Double[] output, double winner) {
        neuralNetwork.propagateBack(intToDouble(input), doubleToDouble(output), new double[]{winner});
    }

    public void stop() {
        NeuralNetworkIO.write(neuralNetwork);
    }

    public static double[] intToDouble(Integer[] input) {
        double[] transformedInput = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            transformedInput[i] = input[i];
        }
        return transformedInput;
    }

    public static double[] doubleToDouble(Double[] input) {
        double[] transformedInput = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            transformedInput[i] = input[i];
        }
        return transformedInput;
    }
}