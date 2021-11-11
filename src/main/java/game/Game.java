package game;

import model.actions.Move;
import model.environment.Board;
import model.environment.Square;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private final Board board = new Board(Board.StartingOption.CLASSIC);
    private final List<Move> history = new LinkedList<>();

    private PieceColor nextMover = PieceColor.WHITE;

    public PieceColor play() {
        PieceColor winner;
        do {
            askMove();
            nextMover = nextMover.getOpponent();
            winner = getWinner();
        } while (winner == null);
        return winner;
    }

    private PieceColor getWinner() {
        Map<PieceColor, List<Piece>> counts = board.getPieces().stream()
                .collect(Collectors.groupingBy(Piece::getColor));
        for (PieceColor pieceColor : counts.keySet()) {
            if (counts.get(pieceColor).isEmpty()) return pieceColor.getOpponent();
        }
        return null;
    }

    public void askMove() {
        // ask move to mover
        history.add(null);
    }
}
