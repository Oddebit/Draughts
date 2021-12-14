package apps.computer;

import model.environment.Board;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.util.Arrays;
import java.util.LinkedList;

public class BoardWriter {
    private Integer[] input;

    public static Integer[] writeBoard(Board board, PieceColor toMove) {
        LinkedList<Integer> input = new LinkedList<>();
        Arrays.stream(board.getSquares())
                .forEach(square -> input.addAll(Arrays.asList(getArray(square.getPiece()))));
        input.add(toMove.ordinal()*2 - 1);

        return input.toArray(new Integer[0]);
    }

    public static Integer[] getArray(Piece piece) {
        Integer[] array = new Integer[]{0, 0, 0, 0};
        if (piece == null) return array;
        int index = 0;
        index += piece.getColor().ordinal() * 2;
        index += piece.getType().ordinal();
        array[index] = 1;
        return array;
    }
}
