package model.actions;

import lombok.Getter;
import model.environment.Board;
import model.environment.Square;
import model.pieces.Piece;
import model.pieces.PieceColor;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Getter
public class Roundup extends Move{

    private final LinkedList<Take> route;

    public Roundup(Piece mover, Take take) {
        super(mover, take.getFrom(), take.getTo());

        this.route = new LinkedList<>();
        this.route.add(take);
    }
    public Roundup(Roundup roundup, Take take) {
        super(roundup.getMover(), roundup.getFrom(), take.getTo());

        this.route = roundup.route;
        this.route.add(take);
    }

    public int getFinalPosition() {
        return route.getLast().getTo();
    }

    public static List<Roundup> getBestRoundups(Board board, PieceColor pieceColor) {
        List<Roundup> roundups = new ArrayList<>();
        Arrays.stream(board.getSquares())
                .map(Square::getPiece)
                .filter(Objects::nonNull)
                .filter(piece -> piece.getColor() == pieceColor)
                .map(piece -> listRoundups(board, piece))
                .forEach(roundups::addAll);
        if (roundups.isEmpty()) return new ArrayList<>();
        else return roundups.stream()
                .collect(groupingBy(roundup -> roundup.getRoute().size(), TreeMap::new, toList()))
                .lastEntry().getValue();
    }
    public static List<Roundup> listRoundups(Board board, Piece piece) {
        List<Roundup> toReturn = new ArrayList<>();
        List<Roundup> toTry = board.getTakes(piece).stream()
                .map(take -> new Roundup(piece, take))
                .collect(toList());
        while (!toTry.isEmpty()) {
            List<Roundup> toTryNext = new ArrayList<>();
            for (Roundup roundup : toTry) {
                List<Roundup> newRoundups = board.getTakes(roundup).stream()
                        .map(take -> new Roundup(roundup, take))
                        .collect(toList());
                if (newRoundups.isEmpty()) toReturn.add(roundup);
                else toTryNext.addAll(newRoundups);
            }
            toTry.clear();
            toTry.addAll(toTryNext);
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return route.stream()
                .map(Take::getTo)
                .map(String::valueOf)
                .reduce(String.valueOf(route.get(0).getFrom()), (a, b) -> a + "x" + b);
    }
}
