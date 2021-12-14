package apps.cvc.app;

import apps.cvc.MovePlayer;
import game.Match;
import model.environment.Board;
import model.moves.Move;
import model.pieces.PieceColor;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class CVCDispatcher {

    private MovePlayer movePlayer;
    boolean play;

    public CVCDispatcher() {
        play = true;
    }

    public void runGames(int epochs, double probability) {
        Predicate<Integer> keepGoing = epochs <= 0 ? n -> play :
                n -> n < epochs && play;
        for (int i = 0; keepGoing.test(i); i++) {
            System.out.println("-".repeat(19));
            System.out.printf("***   Game %2d   ***", i + 1);
            long lastTime = System.currentTimeMillis();
            runGame(probability);
            System.out.printf("Finished in %.3f sec%n%n", (System.currentTimeMillis() - lastTime) / 1000d);
            if ((i - 1) % 100 == 0)
                movePlayer.saveNetwork();
        }
        movePlayer.saveNetwork();
    }

    public void runGame(double probability) {
        Match match = new Match();
        Board board = match.getBoard();
        this.movePlayer = new MovePlayer(board);
        PieceColor winner = null;

        Random random = new Random();
        do {
            List<? extends Move> possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
            if (possibleMoves.isEmpty()) {
                winner = match.getNextMover();
                continue;
            }
            Move move;
            if (random.nextDouble() < probability)
                move = possibleMoves.get(random.nextInt(possibleMoves.size()));
            else
                move = movePlayer.playMove(possibleMoves);
            match.addMove(move);
        } while (winner == null);
        System.out.println(match);
        movePlayer.reviewGame(winner);
    }

    public void stop() {
        if (play) play = false;
        else movePlayer.getBoardEvaluator().stop();
        //todo : make it stop now
    }
}
