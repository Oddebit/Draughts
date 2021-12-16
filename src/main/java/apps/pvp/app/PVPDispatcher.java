package apps.pvp.app;

import game.Match;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import model.environment.Board;
import model.moves.Move;
import model.moves.io.MoveImageIO;
import model.pieces.PieceColor;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PVPDispatcher {
    private Graphics graphics;

    private final Match match;
    private final Board board;
    private final int squareSize;

    private Move lastMove;
    private List<? extends Move> possibleMoves;
    private Move hovered;

    public PVPDispatcher() {
        match = new Match();
        board = match.getBoard();
        squareSize = 60;
        lastMove = null;
        possibleMoves = Move.listPossibleMoves(board, PieceColor.WHITE);
    }

    @Synchronized
    public void render(Graphics graphics) {
        board.renderSquares(graphics, squareSize);

        if (lastMove != null) {
            lastMove.render(graphics, MoveImageIO.State.LAST);
        }
        possibleMoves.forEach(move -> {
            if (hovered != move)
                move.render(graphics, MoveImageIO.State.POSSIBLE);
        });
        try {
            if (hovered != null) {
                hovered.render(graphics, MoveImageIO.State.HOVERED);
            }
        } catch (NullPointerException ignored) {
        }
        board.renderPieces(graphics);
    }

    public void click(Point point) {
        getMove(point).ifPresent(move -> {
            match.addMove(move);
            hovered = null;
            possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
            lastMove = move;
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
