package model.moves;

import lombok.Getter;
import model.environment.Board;
import model.environment.Direction;
import model.environment.Square;
import model.pieces.Piece;
import utils.BoardUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Take extends Move {
    private static Image imageHovered;
    private final Piece taken;

    static {
        try {
            imageHovered = ImageIO.read(new File("res/img/moves/line_hover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Take(Piece mover, int from, int to, Piece taken) {
        super(Type.TAKE, mover, from, to);
        Point fromPoint = BoardUtils.getPointFromPosition(from, true);
        Point toPoint = BoardUtils.getPointFromPosition(to, true);
        assert fromPoint != null;
        assert toPoint != null;
        setFrame(BoardUtils.getFrame(fromPoint, toPoint));
        this.taken = taken;
    }

    public static List<Take> listTakes(Board board, Piece piece) {
        return listTakes(board, piece, piece.getPosition(), new ArrayList<>());
    }

    public static List<Take> listTakes(Board board, Roundup roundup) {
        return listTakes(board, roundup.getMover(), roundup.getFinalPosition(),
                roundup.getRoute().stream().map(Take::getTaken).collect(Collectors.toList()));
    }

    public static List<Take> listTakes(Board board, Piece taker, int fromPosition, List<Piece> taken) {
        List<Take> takes = new ArrayList<>();
        Map<Direction, LinkedList<Integer>> directions = Move.mapDirections(fromPosition);

        for (Direction direction : directions.keySet()) {
            LinkedList<Integer> toPositions = directions.get(direction);

            for (int i = 1; i < Math.min(taker.getRange() + 1, toPositions.size()); i++) {
                int toPosition = toPositions.get(i);
                Square toSquare = board.getSquare(toPosition);
                if (toSquare.getPiece() != null) continue;

                List<Piece> overPieces = toPositions.subList(0, i).stream()
                        .map(board::getSquare)
                        .map(Square::getPiece)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (overPieces.isEmpty()) continue;
                if (overPieces.size() > 1) break;
                Piece overPiece = overPieces.get(0);
                if (overPiece.getColor() == taker.getColor()) break;
                if (taken.contains(overPiece)) break;

                takes.add(new Take(taker, fromPosition, toPosition, overPiece));
            }
        }
        return takes;
    }

    @Override
    public LinkedList<Piece> listTaken() {
        LinkedList<Piece> taken = new LinkedList<>();
        taken.add(this.taken);
        return taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.toString().equals(toString());
    }

    @Override
    public String toString() {
        return getFrom() + "x" + getTo();
    }

    @Override
    public void render(Graphics graphics, boolean hovered) {
        Point pointFrom = BoardUtils.getPointFromPosition(getFrom(), true);
        Point pointTo = BoardUtils.getPointFromPosition(getTo(), true);
        assert pointFrom != null;
        assert pointTo != null;
        int dx = pointTo.x - pointFrom.x;
        int dy = pointTo.y - pointFrom.y;
        int squareSize = BoardUtils.squareSize;

        int fromX = pointFrom.x - (int) Math.copySign(squareSize / 2d, dx);
        int fromY = pointFrom.y - (int) Math.copySign(squareSize / 2d, dy);
        for (int i = 0; i < Math.abs(dy / squareSize); i++) {
            graphics.drawImage(imageHovered, fromX, fromY,
                    (int) Math.copySign(2 * squareSize, dx), (int) Math.copySign(2 * squareSize, dy),
                    new Frame());
            fromX += Math.copySign(squareSize, dx);
            fromY += Math.copySign(squareSize, dy);
        }
    }
}
