package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class BoardUtilsTest {

    @Test
    void getPositionFromPoint() {
        BoardUtils.squareSize = 10;
        Point point;
        int index;

        point = new Point(5, 5);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(-1, index);

        point = new Point(15, 5);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(1, index);

        point = new Point(25, 5);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(-1, index);

        point = new Point(5, 15);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(6, index);

        point = new Point(5, 25);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(-1, index);

        point = new Point(75, 85);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(44, index);

        point = new Point(75, 75);
        index = BoardUtils.getPositionFromPoint(point);
        Assertions.assertEquals(-1, index);
    }

    @Test
    void getPointFromPosition() {
        BoardUtils.squareSize = 10;
        Point point;

        point = BoardUtils.getPointFromPosition(1, false);
        Assertions.assertEquals(10, point.x);
        Assertions.assertEquals(0, point.y);

        point = BoardUtils.getPointFromPosition(6, false);
        Assertions.assertEquals(0, point.x);
        Assertions.assertEquals(10, point.y);

        point = BoardUtils.getPointFromPosition(44, false);
        Assertions.assertEquals(70, point.x);
        Assertions.assertEquals(80, point.y);

        point = BoardUtils.getPointFromPosition(0, false);
        Assertions.assertNull(point);

        point = BoardUtils.getPointFromPosition(51, false);
        Assertions.assertNull(point);
    }
}