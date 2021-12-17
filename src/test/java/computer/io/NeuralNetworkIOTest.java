package computer.io;

import utils.nn.NeuralNetwork;
import utils.nn.io.NeuralNetworkIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NeuralNetworkIOTest {

    @Test
    void writeThenRead() {
        NeuralNetwork write = new NeuralNetwork(202, 64, 8, 8, 1);
        NeuralNetworkIO.write(write);
        NeuralNetwork read = NeuralNetworkIO.read(202, 64, 8, 8, 1);

        double[][] wIHWrite = write.getWeights().get(0).getData();
        double[][] wIHRead = read.getWeights().get(0).getData();

        for (int i = 0; i < wIHRead.length; i++) {
            for (int j = 0; j < wIHRead[i].length; j++) {
                Assertions.assertEquals(wIHRead[i][j], wIHWrite[i][j]);
            }
        }
    }
}