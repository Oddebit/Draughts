package model.environment;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Direction {
    NORTH_WEST(new Pair<>(Cardinal.NORTH, Cardinal.WEST),-5),
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
        return evenRowIncrement - (Board.getRow(position) % 2);
    }

    public Pair<Cardinal, Cardinal> getCardinalPair() {
        return cardinalPair;
    }

    public static List<Direction> getOppositeMoves(List<Cardinal> cardinals) {
        return Arrays.stream(Direction.values())
                .filter(direction -> cardinals.isEmpty() || !direction.cardinalPair.containsAll((cardinals)))
                .collect(Collectors.toList());
    }
}
