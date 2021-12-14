package model.moves;

import lombok.Data;
import model.environment.Board;
import model.environment.Cardinal;
import model.environment.Direction;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public abstract class Move {

    private final Type type;

    private Image image;
    private Image imageHovered;
    private Rectangle frame;

    private final Piece mover;
    private final int from;
    private final int to;

    public Move(Type type, Piece mover, int from, int to) {
        this.type = type;
        this.mover = mover;
        this.from = from;
        this.to = to;
    }

    public static Move copyOf(Move move) {
        Piece mover = Piece.copyOf(move.getMover());
        switch (move.type) {
            case ROUNDUP:
                Roundup roundup = (Roundup) move;
                return new Roundup(mover, roundup.getRoute());
            case TAKE:
                Take take = (Take) move;
                return new Take(mover, take.getFrom(), take.getTo(), Piece.copyOf(take.getTaken()));
            default:
                March march = (March) move;
                return new March(mover, march.getFrom(), march.getTo());
        }
    }

    public abstract void render(Graphics graphics, boolean hovered);

    public static Map<Direction, LinkedList<Integer>> mapDirections(int position) {
        return Arrays.stream(Direction.values())
                .collect(Collectors
                        .toMap(direction -> direction,
                                direction -> getPositions(position, direction)));
    }

    public static LinkedList<Integer> getPositions(int position, Direction direction) {
        LinkedList<Integer> positions = new LinkedList<>();
        while ((position = Board.getNextPosition(position, direction)) != -1) {
            positions.add(position);
        }
        return positions;
    }

    public static List<Direction> listDirections(int position) {
        return Direction.getOppositeDirections(Cardinal.getEdges(position));
    }

    public static List<? extends Move> listPossibleMoves(Board board, PieceColor pieceColor) {
        List<Roundup> roundups = Roundup.listBestRoundups(board, pieceColor);
        if (roundups.isEmpty()) return March.listMarches(board, pieceColor);
        else return roundups;
    }

    public static Move parseString(String string) {
        return null;
    }

    public enum Type {
        MARCH(March.class),
        TAKE(Take.class),
        ROUNDUP(Roundup.class);

        private final Class<? extends Move> moveClass;

        Type(Class<? extends Move> moveClass) {
            this.moveClass = moveClass;
        }
    }
}
