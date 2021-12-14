package apps.computer.app;

import apps.player.app.App;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window(int size, App app) {
        Dimension dimension = new Dimension(100, 10);
        JFrame frame = new JFrame("Draughts Training");

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(app);
        frame.setVisible(true);
        frame.requestFocus();
    }
}
