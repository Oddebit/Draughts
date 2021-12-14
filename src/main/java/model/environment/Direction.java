package model.environment;

import org.javatuples.Pair;
import utils.BoardUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH_WEST(new Pair<>(Cardinal.NORTH, Cardinal.WEST), -5),
    NORTH_EAST(new Pair<>(Cardinal.NORTH, Cardinal.EAST), -4),
    SOUTH_WEST(new Pair<>(Cardinal.SOUTH, Cardinal.WEST), +5),
    SOUTH_EAST(new Pair<>(Cardinal.SOUTH, Cardinal.EAST), +6);

    private final Pair<Cardinal, Cardinal> cardinalPair;
    private final int evenRowIncrement;

    Direction(Pair<Cardinal, Cardinal> cardinalPair, int evenRowIncrement) {
        this.cardinalPair = cardinalPair;
        this.evenRowIncrement = evenRowIncrement;
    }

    public int getIncrement(int position) {
        return evenRowIncrement - (BoardUtils.getRow(position) % 2);
    }

    public Pair<Cardinal, Cardinal> getCardinalPair() {
        return cardinalPair;
    }

    public static List<Direction> getOppositeDirections(List<Cardinal> cardinals) {
        List<Direction> allDirections = Arrays.asList(Direction.values());
        if (cardinals.isEmpty()) return allDirections;

        List<Direction> oppositeDirections = new ArrayList<>();
        for (Direction direction : allDirections) {
            boolean add = true;
            for (Cardinal cardinal : cardinals) {
                if (direction.cardinalPair.contains(cardinal)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                oppositeDirections.add(direction);
            }
        }

        return oppositeDirections;
    }
}
