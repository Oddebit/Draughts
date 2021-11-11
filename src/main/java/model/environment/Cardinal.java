package model.environment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Cardinal {
    NORTH(n -> Board.getRow(n) == 0),
    SOUTH(n -> Board.getRow(n) == 9),
    WEST(n -> Board.getRow(n) % 2 == 1 && n % 5 == 1),
    EAST(n -> Board.getRow(n) % 2 == 0 && n  % 5 == 0);

    private final Predicate<Integer> isEdgeSquare;

    Cardinal(Predicate<Integer> isEdgeSquare) {
        this.isEdgeSquare = isEdgeSquare;
    }

    public static List<Cardinal> getEdges(int position) {
        return Arrays.stream(Cardinal.values())
                .filter(cardinal -> cardinal.isEdgeSquare.test(position))
                .collect(Collectors.toList());
    }
}
