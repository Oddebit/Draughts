package model.moves;

import lombok.Getter;
import model.environment.Board;
import model.environment.Square;
import model.moves.io.MoveImageIO;
import model.pieces.Piece;
import model.pieces.PieceColor;
import utils.BoardUtils;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Getter
public class Roundup extends Move {

    private final LinkedList<Take> route;

    public Roundup(Piece mover, LinkedList<Take> route) {
        super(Type.ROUNDUP, mover, route.getFirst().getFrom(), route.getLast().getTo());
        this.route = new LinkedList<>();
        route.forEach(take -> this.route.add((Take) Move.copyOf(take)));
    }

    public Roundup(Piece mover, Take take) {
        super(Type.ROUNDUP, mover, take.getFrom(), take.getTo());

        this.route = new LinkedList<>();
        this.route.add(take);
        setFrame(route.getLast().getFrame());
    }

    public Roundup(Roundup roundup, Take take) {
        this(roundup.getMover(), take);

        this.route.clear();
        this.route.addAll(roundup.route);
        this.route.add(take);
    }

    public int getFinalPosition() {
        return route.getLast().getTo();
    }

    public static List<Roundup> listBestRoundups(Board board, PieceColor pieceColor) {
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
        List<Roundup> toTry = Take.listTakes(board, piece).stream()
                .map(take -> new Roundup(piece, take))
                .collect(toList());
        while (!toTry.isEmpty()) {
            List<Roundup> toTryNext = new ArrayList<>();
            for (Roundup roundup : toTry) {
                List<Roundup> newRoundups = Take.listTakes(board, roundup).stream()
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
    public LinkedList<Piece> listTaken() {
        return this.route.stream().map(Take::getTaken).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String toString() {
        return route.stream()
                .map(Take::getTo)
                .map(String::valueOf)
                .reduce(String.valueOf(route.get(0).getFrom()), (a, b) -> a + "x" + b);
    }

    @Override
    public void render(Graphics graphics, MoveImageIO.State state) {
        Point pointTo = BoardUtils.getPointFromPosition(getTo(), false);

        assert pointTo != null;
        graphics.drawImage(MoveImageIO.getImage(MoveImageIO.Icon.CROSS, state), pointTo.x, pointTo.y,
                BoardUtils.squareSize, BoardUtils.squareSize, new Frame());
        if (state != MoveImageIO.State.POSSIBLE)
            route.forEach(take -> take.render(graphics, state));

    }
}
