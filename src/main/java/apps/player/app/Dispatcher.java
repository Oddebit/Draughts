package apps.player.app;

import game.Match;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import model.environment.Board;
import model.moves.Move;
import model.moves.Roundup;
import model.pieces.PieceColor;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Dispatcher {
    private Graphics graphics;

    private final Match match;
    private final Board board;
    private final int squareSize;

    private List<? extends Move> possibleMoves;
    private Move hovered;

    public Dispatcher() {
        this.match = new Match();
        this.board = match.getBoard();
        this.squareSize = 60;
        possibleMoves = Move.listPossibleMoves(board, PieceColor.WHITE);
    }

    @Synchronized
    public void render(Graphics graphics) {
        board.renderSquares(graphics, squareSize);

        possibleMoves.forEach(move -> move.render(graphics, false));
        try {
            if (hovered != null) {
                hovered.render(graphics, true);
                if (hovered.getType() == Move.Type.ROUNDUP) {
                    ((Roundup) hovered).getRoute().forEach(take -> take.render(graphics, true));
                }
            }
        } catch (NullPointerException npe) {
        }
        board.renderPieces(graphics);
    }

    public void click(Point point) {
        getMove(point).ifPresent(move -> {
            match.addMove(move);
            hovered = null;
            possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
        });
    }

    @Synchronized
    public void hover(Point point) {
        getMove(point).ifPresentOrElse(this::setHovered, () -> this.setHovered(null));
    }

    public Optional<? extends Move> getMove(Point point) {
        return possibleMoves.stream()
                .filter(m -> m.getFrame().contains(point))
                .findFirst();
    }
}
