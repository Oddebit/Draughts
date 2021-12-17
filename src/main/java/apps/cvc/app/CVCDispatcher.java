package apps.cvc.app;

import apps.cvc.MovePlayer;
import game.Match;
import game.io.MatchIO;
import model.environment.Board;
import model.moves.Move;
import model.pieces.PieceColor;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class CVCDispatcher {

    private MovePlayer movePlayer;
    private final double probabilityRandom;
    private final int savingFrequency;

    private boolean play;

    public CVCDispatcher(double probabilityRandom, int savingFrequency) {
        this.probabilityRandom = probabilityRandom;
        this.savingFrequency = savingFrequency;
        play = true;
    }

    public void runGames(int epochs) {
        long lastTime = System.currentTimeMillis();
        Predicate<Integer> keepGoing = epochs <= 0 ? n -> play :
                n -> n < epochs && play;
        for (int i = 0; keepGoing.test(i); i++) {
            Match match = runGame(i, probabilityRandom);
            if ((i - 1) % savingFrequency == 0) {
                movePlayer.saveNetwork();
            }
        }

        double time = (System.currentTimeMillis() - lastTime)/1000d;
        System.out.printf("%n%nAchieved %d epochs in %.3f sec", epochs, time);
        System.out.printf("%nAverage time : %.3f sec%n", time/epochs);
        movePlayer.saveNetwork();
    }

    public Match runGame(int epoch, double probability) {
        long lastTime = System.currentTimeMillis();
        System.out.printf("%nGame %d", epoch + 1);

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

        movePlayer.reviewGame(winner);
        System.out.printf(" finished in %.3f sec", (System.currentTimeMillis() - lastTime) / 1000d);
        return match;
    }

    public void stop() {
        if (play) play = false;
        else movePlayer.getBoardEvaluator().stop();
        //todo : make it stop now
    }
}
