package model.moves;

import model.environment.Board;
import model.environment.Direction;
import model.pieces.Piece;
import model.pieces.PieceColor;
import utils.BoardUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class March extends Move {

    private static Image image;
    private static Image imageHovered;
    private static Image imageStep;
    private static Image imageStepHovered;

    public March(Piece mover, int from, int to) {
        super(Type.MARCH, mover, from, to);
        Point fromPoint = BoardUtils.getPointFromPosition(from, true);
        Point toPoint = BoardUtils.getPointFromPosition(to, true);
        assert fromPoint != null;
        assert toPoint != null;
        setFrame(BoardUtils.getFrame(fromPoint, toPoint));
    }

    static {
        try {
            image = ImageIO.read(new File("res/img/moves/arrow_no_hover.png"));
            imageHovered = ImageIO.read(new File("res/img/moves/arrow_hover.png"));
            imageStep = ImageIO.read(new File("res/img/moves/line_no_hover.png"));
            imageStepHovered = ImageIO.read(new File("res/img/moves/line_hover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        for (int i = 0; i < Math.abs(dy / squareSize) - 1; i++) {
            graphics.drawImage(hovered ? imageStepHovered : imageStep, fromX, fromY,
                    (int) Math.copySign(2 * squareSize, dx), (int) Math.copySign(2 * squareSize, dy),
                    new Frame());
            fromX += Math.copySign(squareSize, dx);
            fromY += Math.copySign(squareSize, dy);
        }

        graphics.drawImage(hovered ? imageHovered : image,
                pointTo.x - (int) Math.copySign(1.5 * squareSize, dx),
                pointTo.y - (int) Math.copySign(1.5 * squareSize, dy),
                (int) Math.copySign(2 * squareSize, dx), (int) Math.copySign(2 * squareSize, dy),
                new Frame());
    }

    public static List<March> listMarches(Board board, PieceColor pieceColor) {
        List<March> marches = new ArrayList<>();
        board.getPieces().stream()
                .filter(piece -> piece.getColor() == pieceColor)
                .map(piece -> listMarches(board, piece))
                .forEach(marches::addAll);
        return marches;
    }

    public static List<March> listMarches(Board board, Piece piece) {
        int fromPosition = piece.getPosition();

        List<March> marches = new ArrayList<>();
        Map<Direction, LinkedList<Integer>> directions = Move.mapDirections(fromPosition);

        for (Direction direction : directions.keySet()) {
            LinkedList<Integer> toPositions = directions.get(direction);

            if (piece.getType() == Piece.Type.PAWN
                    && !direction.getCardinalPair().contains(piece.getColor().getForward()))
                continue;

            for (int i = 0; i < piece.getRange() && i < toPositions.size(); i++) {
                int toPosition = toPositions.get(i);

                if (board.getSquare(toPosition).getPiece() != null) break;
                marches.add(new March(piece, fromPosition, toPosition));
            }
        }
        return marches;
    }

    @Override
    public LinkedList<Piece> listTaken() {
        return new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.toString().equals(toString());
    }

    @Override
    public String toString() {
        return getFrom() + "-" + getTo();
    }
}
