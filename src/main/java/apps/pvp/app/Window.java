package apps.pvp.app;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window(int size, Canvas app) {
        Dimension dimension = new Dimension((int) (size * 1.02), (int) (size * 1.06));
        JFrame frame = new JFrame("Draughts");

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);
        frame.setResizable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(app);
        frame.setVisible(true);
        frame.requestFocus();
    }
}
