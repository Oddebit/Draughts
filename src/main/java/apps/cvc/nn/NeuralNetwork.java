package apps.cvc.nn;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import lombok.Getter;

@Getter
public class NeuralNetwork {
    private final Matrix weightsIH;
    private final Matrix biasH;

    private final Matrix weightsHO;
    private final Matrix biasO;

    private final double learningRate = 0.005;

    public NeuralNetwork(int input, int hidden, int output) {
        this.weightsIH = new Matrix(hidden, input);
        this.weightsHO = new Matrix(output, hidden);
        this.biasH = new Matrix(hidden, 1);
        this.biasO = new Matrix(output, 1);
    }

    public NeuralNetwork(Matrix weightsIH, Matrix biasH, Matrix weightsHO, Matrix biasO) {
        this.weightsIH = weightsIH;
        this.biasH = biasH;
        this.weightsHO = weightsHO;
        this.biasO = biasO;
    }

    public Double[] predict(double[] Input) {
        Matrix input = Matrix.fromArray(Input);
        Matrix hidden = Matrix.multiply(this.weightsIH, input);
        hidden.add(this.biasH);
        hidden.sigmoid();
        Matrix output = Matrix.multiply(this.weightsHO, hidden);
        output.add(this.biasO);
        output.sigmoid();
        return output.toArray();
    }

    public void propagateBack(double[] Input, double[] Output, double[] Target) {
        Matrix input = Matrix.fromArray(Input);
        Matrix hidden = Matrix.multiply(this.weightsIH, input);
        hidden.add(this.biasH);
        hidden.sigmoid();
        Matrix output = Matrix.fromArray(Output);
        Matrix target = Matrix.fromArray(Target);
        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dSigmoid();
        gradient.multiply(error);
        gradient.multiply(learningRate);
        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix weightsHO_delta = Matrix.multiply(gradient, hidden_T);
        this.weightsHO.add(weightsHO_delta);
        this.biasO.add(gradient);
        Matrix weightsHO_T = Matrix.transpose(this.weightsHO);
        Matrix errorsH = Matrix.multiply(weightsHO_T, error);
        Matrix gradientH = hidden.dSigmoid();
        gradientH.multiply(errorsH);
        gradientH.multiply(learningRate);
        Matrix input_T = Matrix.transpose(input);
        Matrix weightsIH_delta = Matrix.multiply(gradientH, input_T);
        this.weightsIH.add(weightsIH_delta);
        this.biasH.add(gradientH);
    }
}

