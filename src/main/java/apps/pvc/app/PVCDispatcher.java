package apps.pvc.app;

import apps.cvc.MovePlayer;
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
public class PVCDispatcher implements Runnable {
    private Graphics graphics;

    private Match match;
    private Board board;
    private final int squareSize;

    private final PieceColor playerColor;
    private MovePlayer movePlayer;
    boolean playerHasMoved = false;

    private List<? extends Move> possibleMoves;
    private Move hovered;

    public PVCDispatcher() {
        squareSize = 60;
        playerColor = PieceColor.WHITE;
        match = new Match();
        board = match.getBoard();
        possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
        movePlayer = new MovePlayer(board);
        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime;
        long now;
        while (match.getWinner() == null) {
            if (match.getNextMover() == playerColor) {
                playerHasMoved = false;
                // wait for player to make a move
                while (!playerHasMoved) {
                    lastTime = System.currentTimeMillis();
                    do {
                        now = System.currentTimeMillis();
                    } while (now - lastTime < 500);
                }
            } else {
                // computer makes a move
                Move move = movePlayer.playMove(possibleMoves);
                addMove(move);
            }
        }
    }

    @Synchronized
    public void render(Graphics graphics) {
        board.renderSquares(graphics, squareSize);

        if (match.getNextMover() == playerColor) {
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
        }
        board.renderPieces(graphics);
    }

    public void click(Point point) {
        if (match.getNextMover() != playerColor) return;
        getMove(point).ifPresent(this::addMove);
    }

    public void addMove(Move move) {
        match.addMove(move);
        hovered = null;
        possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
        playerHasMoved = true;
    }

    @Synchronized
    public void hover(Point point) {
        if (match.getNextMover() != playerColor) return;
        getMove(point).ifPresentOrElse(this::setHovered, () -> this.setHovered(null));
    }

    public Optional<? extends Move> getMove(Point point) {
        return possibleMoves.stream()
                .filter(m -> m.getFrame().contains(point))
                .findFirst();
    }
}
