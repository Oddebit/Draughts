package apps.cvc;

import model.environment.Board;
import model.environment.Square;
import model.moves.Move;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.util.Arrays;
import java.util.LinkedList;

public class BoardWriter {

    public static Integer[] writeBoard(Board board, Move move) {
        Piece mover = move.getMover();
        Square to = board.getSquare(move.getTo());
        LinkedList<Piece> taken = move.listTaken();

        LinkedList<Integer> input = new LinkedList<>();
        for (Square square : board.getSquares()) {
            Piece piece = square.getPiece();
            if (piece == null)
                input.addAll(Arrays.asList(pieceToArray(null)));
            else if (taken.contains(piece) || piece == mover)
                input.addAll(Arrays.asList(pieceToArray(null)));
            else if (square == to)
                input.addAll(Arrays.asList(pieceToArray(mover)));
            else
                input.addAll(Arrays.asList(pieceToArray(piece)));
        }

        input.addAll(Arrays.asList(playerToArray(mover.getColor())));
        return input.toArray(new Integer[0]);
    }

    public static Integer[] pieceToArray(Piece piece) {
        Integer[] array = new Integer[]{0, 0, 0, 0};
        if (piece == null) return array;
        int index = 0;
        index += piece.getColor().ordinal() * 2;
        index += piece.getType().ordinal();
        array[index] = 1;
        return array;
    }

    public static Integer[] playerToArray(PieceColor color) {
        Integer[] array = new Integer[]{0, 0};
        array[color.ordinal()] = 1;
        return array;
    }
}
