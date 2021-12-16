package utils;

import java.awt.*;

public class BoardUtils {

    public static int squareSize = 60;

    public static int getPositionFromPoint(Point point) {
        int row = point.y / squareSize;
        if (0 > row || row > squareSize * 10) return -1;
        int col = point.x / squareSize;
        if (0 > col || col > squareSize * 10) return -1;

        int incr = row % 2 == 0 ? 1 : 0;
        if (col % 2 != incr) return -1;

        return 5 * row + (col / 2) + 1;
    }

    public static Point getPointFromPosition(int position, boolean mid) {
        if (1 > position || position > 50) return null;
        return new Point(getColumn(position) * squareSize + (mid ? squareSize / 2 : 0), getRow(position) * squareSize + (mid ? squareSize / 2 : 0));
    }

    public static int getRow(int position) {
        if (1 > position || position > 50) return -1;
        return --position / 5;
    }

    public static int getColumn(int position) {
        if (1 > position || position > 50) return -1;
        int row = getRow(position);
        int col = --position % 5;
        int incr = row % 2 == 0 ? 1 : 0;

        return 2 * col + incr;
    }

    public static Rectangle getFrame(Point to) {
        return getFrame(to, new Point(to.x + squareSize, to.y + squareSize));
    }

    public static Rectangle getFrame(Point from, Point to) {
        int fromX = from.x;
        int fromY = from.y;
        int toX = to.x;
        int toY = to.y;
        int x = Math.min(fromX, toX);
        int y = Math.min(fromY, toY);
        int w = Math.abs(fromX - toX);
        int h = Math.abs(fromY - toY);
        return new Rectangle(x, y, w, h);
    }
}
