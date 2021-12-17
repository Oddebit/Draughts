package apps.cvc.app;

import utils.nn.io.NeuralNetworkIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CVCPVPDispatcherTest {

    @Test
    void runGame() {
        double bias = NeuralNetworkIO.read().getBiases().get(0).getData()[0][0];
        new CVCDispatcher(0, 10).runGames(1);
        Assertions.assertNotEquals(bias, NeuralNetworkIO.read().getBiases().get(0).getData()[0][0]);
    }
}