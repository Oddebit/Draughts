package model.environment;

import utils.BoardUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Cardinal {
    NORTH(position -> BoardUtils.getRow(position) == 0, -1),
    SOUTH(position -> BoardUtils.getRow(position) == 9, 1),
    WEST(position -> BoardUtils.getColumn(position) == 0, 0),
    EAST(position -> BoardUtils.getColumn(position) == 9, 0);

    private final Predicate<Integer> isEdgeSquare;
    private final int vertical;

    Cardinal(Predicate<Integer> isEdgeSquare, int vertical) {
        this.isEdgeSquare = isEdgeSquare;
        this.vertical = vertical;
    }

    public static List<Cardinal> getEdges(int position) {
        return Arrays.stream(Cardinal.values())
                .filter(cardinal -> cardinal.isEdgeSquare.test(position))
                .collect(Collectors.toList());
    }

    public int getVertical() {
        return vertical;
    }
}
