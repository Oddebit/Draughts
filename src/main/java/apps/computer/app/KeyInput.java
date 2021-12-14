package apps.computer.app;

import apps.computer.MovePlayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private final Dispatcher dispatcher;

    public KeyInput(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispatcher.stop();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
