package game;

import lombok.Getter;
import model.environment.Board;
import model.environment.Cardinal;
import model.moves.Move;
import model.pieces.Piece;
import model.pieces.PieceColor;
import model.pieces.Queen;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Match {

    private final Board board;
    private final List<Move> history;

    private PieceColor nextMover = PieceColor.WHITE;

    public Match() {
        this(new Board(Board.StartingOption.CLASSIC), new LinkedList<>());
    }

    public Match(Board board, LinkedList<Move> history) {
        this.board = board;
        this.history = history;
    }

    public void addMove(Move move) {
        board.addMove(move);
        history.add(move);
        Piece mover = move.getMover();
        if (Cardinal.getEdges(move.getTo()).contains(mover.getColor().getForward()))
            board.movePiece(new Queen(mover.getColor()), move.getTo());
        nextMover = nextMover.getOpponent();
    }

    public PieceColor getWinner() {
        Map<PieceColor, List<Piece>> counts = board.getPieces().stream()
                .collect(Collectors.groupingBy(Piece::getColor));
        for (PieceColor color : PieceColor.values()) {
            if (!counts.containsKey(color)) {
                PieceColor winner = color.getOpponent();
                System.out.println(this);
                System.out.println(winner + " wins");
                return color.getOpponent();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            if (i % 2 == 0) string.append("\n").append(1 + i / 2).append(". ");
            string.append(history.get(i)).append(" ");
        }
        return string.toString();
    }

//   public static Game parseString(String string) {
//        List<Pair<Move, Move>> history = new LinkedList<>();
//        PieceColor toMove;
//        String[] data = string.split("\n/d+\\. ");
//        for (String datum : data) {
//            String[] pair = datum.split(" ");
//            history.add(new Pair<>(Move.parseString(pair[0]), Move.parseString(pair[1])));
//        }
//        return new Game(history,toMove);
//    }
}
