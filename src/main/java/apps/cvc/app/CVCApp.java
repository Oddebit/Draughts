package apps.cvc.app;

import java.awt.*;

public class CVCApp extends Canvas{

    private final CVCDispatcher dispatcher;

    public CVCApp() {
        int epochs = 8*3600 * 5/2;
        dispatcher = new CVCDispatcher();
        this.addKeyListener(new CVCKeyInput(dispatcher));

        long lastTime = System.currentTimeMillis();
        dispatcher.runGames(epochs, 0.1);
        double time = (System.currentTimeMillis() - lastTime)/1000d;
        System.out.printf("%n%nAchieved %d epochs in %.3f sec", epochs, time);
        System.out.printf("%nAverage time : %.3f sec%n", time/epochs);
    }

    public static void main(String[] args) {
        new CVCApp();
    }
}
