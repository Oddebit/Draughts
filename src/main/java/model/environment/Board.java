package model.environment;

import lombok.Getter;
import model.actions.March;
import model.actions.Move;
import model.actions.Roundup;
import model.actions.Take;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class Board {

    private final Square[] squares = new Square[50];

    public Board(StartingOption startingOption) {
        for (int i = 0; i < 50; i++) {
            squares[i] = new Square();
            if (startingOption == StartingOption.EMPTY) continue;
            if (i < 20) movePiece(new Pawn(PieceColor.BLACK), i + 1);
            else if (30 <= i) movePiece(new Pawn(PieceColor.WHITE), i + 1);
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

    public List<? extends Move> getPossibleMoves(PieceColor pieceColor) {
        List<Roundup> roundups = Roundup.getBestRoundups(this, pieceColor);
        if (roundups.isEmpty()) return getMarches(pieceColor);
        else return Roundup.getBestRoundups(this, pieceColor);
    }

    public List<March> getMarches(PieceColor pieceColor) {
        List<March> marches = new ArrayList<>();
        getPieces().stream()
                .filter(piece -> piece.getColor() == pieceColor)
                .map(this::getMarches)
                .forEach(marches::addAll);
        return marches;
    }

    public List<March> getMarches(Piece piece) {
        int position = piece.getPosition();
//                direction, edge into account
        return getDirections(position).stream()
//                forward
                .filter(direction -> direction.getCardinalPair().contains(piece.getColor().getForward()))
//                to marches
                .map(direction -> new March(piece, position, getNextPosition(position, direction, 1)))
//                free
                .filter(march -> getSquare(march.getTo()).getPiece() == null)
                .collect(Collectors.toList());
    }

    public List<Take> getTakes(Piece piece) {
        return getTakes(piece, piece.getPosition(), new ArrayList<>());
    }

    public List<Take> getTakes(Roundup roundup) {
        return getTakes(roundup.getMover(), roundup.getFinalPosition(),
                roundup.getRoute().stream().map(Take::getTaken).collect(Collectors.toList()));
    }

    public List<Take> getTakes(Piece piece, int position, List<Piece> taken) {
        List<Take> takes = new ArrayList<>();
        for (Direction direction : getDirections(position)) {
            int overPosition = getNextPosition(position, direction, 1);
            Square over = getSquare(overPosition);
            int destination = getNextPosition(position, direction, 2);
            Square to = getSquare(destination);
            if (to == null || over == null) continue;
            Piece toTake = over.getPiece();
            if (toTake == null || toTake.getColor() == piece.getColor() || taken.contains(toTake)) continue;
            Take take = new Take(piece, position, destination, toTake);
            takes.add(take);
        }
        return takes;
    }

    public List<Direction> getDirections(int position) {
        return Direction.getOppositeMoves(Cardinal.getEdges(position));
    }

    public Square getSquare(int position) {
        if (1 > position || position > 50) return null;
        return squares[position - 1];
    }

    public static int getRow(int position) {
        return (position - 1) / 5;
    }

    public int getNextPosition(int position, Direction direction, int times) {
        for (int i = 0; i < times; i++) {
            position = getNextPosition(position, direction);
            if (position == -1) return -1;
        }
        return position;
    }

    public int getNextPosition(int position, Direction direction) {
        if (!isInside(position, direction)) return -1;
        return position + direction.getIncrement(position);
    }

    public boolean isInside(int position, Direction direction) {
        return !direction.getCardinalPair().contains(Cardinal.getEdges(position));
    }

    public List<Piece> getPieces() {
        return Arrays.stream(getSquares())
                .map(Square::getPiece)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("  ");
        for (int i = 0; i < squares.length; i++) {
            Piece piece = squares[i].getPiece();
            str.append(String.format(" %s ", piece == null ? "*" : piece.getColor().name().substring(0, 1)));
            int position = i + 1;
            if (position % 2 == 1 && position % 5 == 0) str.append("\n");
            else if (position % 2 == 0 && position % 5 == 0 && position != 50) str.append("  \n  ");
            else str.append("  ");

        }
        return str.toString();
    }

    public enum StartingOption {
        CLASSIC,
        EMPTY
    }
}
