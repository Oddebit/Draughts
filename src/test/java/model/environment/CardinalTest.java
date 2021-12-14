package model.environment;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CardinalTest {

    @Test
    void testEdges() {
        IntStream.rangeClosed(1, 5).forEach(n -> {

            List<Cardinal> cardinals = Cardinal.getEdges(n);
            if (n != 5) {
                assertEquals(cardinals.size(), 1, n + " should only touch one edge.");
                assertSame(cardinals.get(0), Cardinal.NORTH, n + " should touch north.");
            }

            int s = n + 45;
            if (s != 46) {
                cardinals = Cardinal.getEdges(s);
                assertEquals(cardinals.size(), 1, s + " should only touch one edge.");
                assertSame(cardinals.get(0), Cardinal.SOUTH, s + " should touch south.");
            }

            int e = 5 + 10 * (n - 1);
            if (e != 5) {
                cardinals = Cardinal.getEdges(e);
                assertEquals(cardinals.size(), 1, e + " should only touch one edge.");
                assertSame(cardinals.get(0), Cardinal.EAST, e + " should touch east.");
            }

            int w = e + 1;
            if (w != 46) {
                cardinals = Cardinal.getEdges(w);
                assertEquals(cardinals.size(), 1, w + " should only touch one edge.");
                assertSame(cardinals.get(0), Cardinal.WEST, w + " should touch west.");
            }
        });
    }
}