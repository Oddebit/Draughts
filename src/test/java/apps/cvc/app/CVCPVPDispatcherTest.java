package apps.cvc.app;

import apps.cvc.nn.io.NeuralNetworkIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CVCPVPDispatcherTest {

    @Test
    void runGame() {
        double bias = NeuralNetworkIO.read().getBiasO().getData()[0][0];
        new CVCDispatcher().runGames(10, 1);
        Assertions.assertNotEquals(bias, NeuralNetworkIO.read().getBiasO().getData()[0][0]);
    }
}