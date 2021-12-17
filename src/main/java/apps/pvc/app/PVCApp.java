package apps.pvc.app;

import apps.pvp.app.Window;
import model.pieces.PieceColor;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class PVCApp extends Canvas implements Runnable {

    private Thread thread;
    private boolean running;
    private final PVCDispatcher dispatcher;

    public PVCApp() {
        dispatcher = new PVCDispatcher(PieceColor.values()[new Random().nextInt(1)]);
        new Window(dispatcher.getSquareSize() * 10, this);
        PVCMouseInput mouseInput = new PVCMouseInput(dispatcher);
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
        long lastTime;
        long now;
        while (running) {
            lastTime = System.currentTimeMillis();
            do {
                now = System.currentTimeMillis();
            } while (now - lastTime < 100);
            BufferStrategy bufferStrategy = getBufferStrategy();
            Graphics graphics = getBufferStrategy().getDrawGraphics();
            dispatcher.render(graphics);
            graphics.dispose();
            bufferStrategy.show();
        }
    }

    public static void main(String[] args) {
        new PVCApp();
    }
}

