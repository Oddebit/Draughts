package apps.player.app;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class App extends Canvas implements Runnable {

    private Thread thread;
    private boolean running;
    private final Dispatcher dispatcher;

    public App() {
        dispatcher = new Dispatcher();
        new Window(dispatcher.getSquareSize() * 10, this);
        MouseInput mouseInput = new MouseInput(dispatcher);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        start();
    }

    public synchronized void start() {
        createBufferStrategy(3);
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            BufferStrategy bufferStrategy = getBufferStrategy();
            Graphics graphics = getBufferStrategy().getDrawGraphics();
            dispatcher.render(graphics);
            graphics.dispose();
            bufferStrategy.show();
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
