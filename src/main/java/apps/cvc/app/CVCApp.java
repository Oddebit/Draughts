package apps.cvc.app;

import java.awt.*;

public class CVCApp extends Canvas{

    private final CVCDispatcher dispatcher;

    public CVCApp() {
        dispatcher = new CVCDispatcher(0.1, 1_000);
        this.addKeyListener(new CVCKeyInput(dispatcher));

        dispatcher.runGames(0);
    }

    public static void main(String[] args) {
        new CVCApp();
    }
}
