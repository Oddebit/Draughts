package computer.io;

import apps.cvc.nn.NeuralNetwork;
import apps.cvc.nn.io.NeuralNetworkIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NeuralNetworkIOTest {

    @Test
    void writeThenRead() {
        NeuralNetwork write = new NeuralNetwork(202, 202, 1);
        NeuralNetworkIO.write(write);
        NeuralNetwork read = NeuralNetworkIO.read();

        double[][] wIHWrite = write.getWeightsIH().getData();
        double[][] wIHRead = read.getWeightsIH().getData();

        for (int i = 0; i < wIHRead.length; i++) {
            for (int j = 0; j < wIHRead[i].length; j++) {
                Assertions.assertEquals(wIHRead[i][j], wIHWrite[i][j]);
            }
        }
    }
}