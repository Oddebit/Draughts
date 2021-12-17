package apps.cvc;

import utils.nn.io.NeuralNetworkIO;
import lombok.Getter;
import model.environment.Board;
import model.moves.Move;
import model.pieces.PieceColor;
import org.javatuples.Pair;

import java.util.*;

@Getter
public class MovePlayer {

    private final Board board;
    private final List<Pair<Integer[], Double[]>> inputsOutputs = new LinkedList<>();
    private final BoardEvaluator boardEvaluator;

    public MovePlayer(Board board) {
        this.board = board;
        this.boardEvaluator = new BoardEvaluator();
    }

    public Move playMove(List<? extends Move> possibleMoves) {
        Pair<Integer[], Double[]> inputOutput = null;
        Move move = null;

        PieceColor toMove = possibleMoves.get(0).getMover().getColor();
        int toWin = toMove.ordinal();
        double closest = toMove.getOpponent().ordinal();

        for (Move possibleMove : possibleMoves) {
            Integer[] input = BoardWriter.writeBoard(board, possibleMove);
            double output = boardEvaluator.evaluateBoard(input);

            if (Math.abs(output - toWin) < Math.abs(closest - toWin)) {
                closest = output;
                inputOutput = new Pair<>(input, new Double[]{output});
                move = possibleMove;
            }
        }
        inputsOutputs.add(inputOutput);
        return move;
    }

    public void reviewGame(PieceColor winner) {
        int toWin = winner.ordinal();
        inputsOutputs.forEach(io -> boardEvaluator.learn(io.getValue0(), io.getValue1(), toWin));
    }

    public void saveNetwork() {
        NeuralNetworkIO.write(boardEvaluator.getNeuralNetwork());
    }
}
