package game;

import model.environment.Board;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class MatchTest {

    @Test
    void getWinner() {
        Board board = new Board(Board.StartingOption.EMPTY);
        board.movePiece(new Pawn(PieceColor.BLACK), 1);
        Match match = new Match(board, new LinkedList<>());
        System.out.println(match.getBoard());
        Assertions.assertEquals(PieceColor.BLACK, match.getWinner());
    }
}