package model.environment;

import lombok.Getter;
import model.moves.Move;
import model.moves.Roundup;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.PieceColor;
import utils.BoardUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class Board {

    private Square[] squares = new Square[50];

    public Board(StartingOption startingOption) {
        for (int i = 0; i < 50; i++) {
            squares[i] = new Square();
            if (startingOption == StartingOption.EMPTY) continue;
            if (i < 20) movePiece(new Pawn(PieceColor.BLACK), i + 1);
            else if (30 <= i) movePiece(new Pawn(PieceColor.WHITE), i + 1);
        }
    }

    public void renderSquares(Graphics graphics, int squareSize) {
        Color black = new Color(0, 102, 0);
        Color white = new Color(205, 205, 255);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Point point = new Point(x * squareSize, y * squareSize);
                int position = BoardUtils.getPositionFromPoint(point);
                if (position != -1) {
                    graphics.setColor(black);
                } else {
                    graphics.setColor(white);
                }
                graphics.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            }
        }
    }

    public void renderPieces(Graphics graphics) {
        int squareSize = BoardUtils.squareSize;
        for (int i = 0; i < squares.length; i++) {
            Point point = BoardUtils.getPointFromPosition(i + 1, false);
            assert point != null;
            int x = point.x;
            int y = point.y;
            Piece piece = squares[i].getPiece();
            if (piece == null) continue;
            graphics.drawImage(piece.getImage(), x, y, squareSize, squareSize, new Frame());
        }
    }

    public void movePiece(Piece piece, int destination) {
        int position = piece.getPosition();
        if (position != 0) {
            getSquare(position).setPiece(null);
        }
        piece.setPosition(destination);
        getSquare(destination).setPiece(piece);
    }

    public void removePiece(Piece piece) {
        int position = piece.getPosition();
        if (position != 0) {
            getSquare(position).setPiece(null);
        }
        piece.setPosition(-1);
    }

    public Square getSquare(int position) {
        if (1 > position || position > 50) return null;
        return squares[position - 1];
    }

    public static int getNextPosition(int position, Direction direction, int times) {
        for (int i = 0; i < times; i++) {
            position = getNextPosition(position, direction);
            if (position == -1) break;
        }
        return position;
    }

    public static int getNextPosition(int position, Direction direction) {
        if (!isInside(position, direction)) return -1;
        return position + direction.getIncrement(position);
    }

    public static boolean isInside(int position, Direction direction) {
        return Move.listDirections(position).contains(direction);
    }

    public List<Piece> getPieces() {
        return Arrays.stream(getSquares())
                .map(Square::getPiece)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("   ");
        for (int i = 0; i < squares.length; i++) {
            Piece piece = squares[i].getPiece();
            str.append(String.format("%s", piece == null ? "*" : piece.getColor().name().substring(0, 1)));
            int position = i + 1;
            if (position % 2 == 1 && position % 5 == 0) str.append("\n");
            else if (position % 2 == 0 && position % 5 == 0 && position != 50) str.append("   \n   ");
            else str.append("     ");
        }
        return str.toString();
    }

    public void addMove(Move move) {
        if (move.getType() == Move.Type.ROUNDUP) {
            Roundup roundup = (Roundup) move;
            roundup.getRoute().forEach(take -> removePiece(take.getTaken()));
        }
        movePiece(move.getMover(), move.getTo());
    }

    public enum StartingOption {
        CLASSIC,
        EMPTY
    }
}
