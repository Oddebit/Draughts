package apps.pvc.app;

import lombok.RequiredArgsConstructor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@RequiredArgsConstructor
public class PVCMouseInput implements MouseListener, MouseMotionListener {
    private final PVCDispatcher dispatcher;

    @Override
    public void mouseClicked(MouseEvent e) {
        dispatcher.click(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dispatcher.hover(e.getPoint());
    }
}
