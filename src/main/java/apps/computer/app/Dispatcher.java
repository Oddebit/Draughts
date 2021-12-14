package apps.computer.app;

import apps.computer.MovePlayer;
import game.Match;
import model.environment.Board;
import model.moves.Move;

import java.util.List;
import java.util.function.Predicate;

public class Dispatcher {

    private MovePlayer movePlayer;
    boolean play;

    public Dispatcher(int epochs) {
        play = true;
        runGames(epochs);
    }

    public void runGames(int epochs) {
        Predicate<Integer> keepGoing = epochs <= 0 ? n -> play :
                n -> n < epochs && play;
        for (int i = 0; keepGoing.test(i); i++) {
            System.out.println("-".repeat(19));
            System.out.printf("***   Game %2d   ***", i + 1);
            long lastTime = System.currentTimeMillis();
            runGame();
            long t = System.currentTimeMillis() - lastTime;
            long min = t / 60000;
            long sec = t / 1000 - min * 60;
            System.out.printf("Finished in %d''%d'%n%n", min, sec);
        }
        stop();
    }

    public void runGame() {
        Match match = new Match();
        Board board = match.getBoard();
        this.movePlayer = new MovePlayer(board);

        do {
            List<? extends Move> possibleMoves = Move.listPossibleMoves(board, match.getNextMover());
            Move move = movePlayer.playMove(possibleMoves);
            match.addMove(move);
        } while (match.getWinner() == null);
    }

    public void stop() {
        if (play) play = false;
        else movePlayer.getBoardEvaluator().stop();
    }
}
