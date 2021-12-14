package apps.cvc.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CVCKeyInput implements KeyListener {

    private final CVCDispatcher dispatcher;

    public CVCKeyInput(CVCDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispatcher.stop();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
