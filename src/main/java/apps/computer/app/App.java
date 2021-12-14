package apps.computer.app;

import java.awt.*;

public class App extends Canvas implements Runnable{

    private Thread thread;
    private boolean running;
    private final Dispatcher dispatcher;

    public App() {
        dispatcher = new Dispatcher(60);
        this.addKeyListener(new KeyInput(dispatcher));
        start();
    }

    public synchronized void start() {
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

    }

    public static void main(String[] args) {
        new App();
    }
}
