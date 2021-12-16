package apps.pvc.app;

import apps.cvc.MovePlayer;
import game.Match;
import game.io.MatchIO;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import model.environment.Board;
import model.moves.Move;
import model.moves.Roundup;
import model.moves.io.MoveImageIO;
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

    private Move lastMove;
    private List<? extends Move> possibleMoves;
    private Move hovered;

    public PVCDispatcher() {
        match = new Match();
        board = match.getBoard();
        squareSize = 60;

        playerColor = PieceColor.WHITE;
        movePlayer = new MovePlayer(board);

        lastMove = null;
        possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
        new Thread(this).start();
    }

    @Override
    public void run() {
        PieceColor winner = null;
        long lastTime;
        long now;
        while (winner == null) {
            if (possibleMoves.isEmpty()) {
                winner = match.getNextMover();
                continue;
            }
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
            winner = match.getWinner();
        }
        MatchIO.write(match, "pvc");
    }

    @Synchronized
    public void render(Graphics graphics) {
        board.renderSquares(graphics, squareSize);

        possibleMoves.forEach(move -> move.render(graphics, MoveImageIO.State.POSSIBLE));
        if (lastMove != null) {
            lastMove.render(graphics, MoveImageIO.State.LAST);
        }
        try {
            if (hovered != null) {
                hovered.render(graphics, MoveImageIO.State.HOVERED);
                if (hovered.getType() == Move.Type.ROUNDUP) {
                    ((Roundup) hovered).getRoute().forEach(take -> take.render(graphics, MoveImageIO.State.HOVERED));
                }
            }
        } catch (NullPointerException npe) {
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
        lastMove = move;
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
