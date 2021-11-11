package model.environment;

import model.actions.March;
import model.actions.Move;
import model.actions.Roundup;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void init() {
        System.out.println(new Board(Board.StartingOption.EMPTY));
        System.out.println();
        System.out.println(new Board(Board.StartingOption.CLASSIC));
    }

    @Test
    void getMarches() {
        Board board = new Board(Board.StartingOption.EMPTY);

        Pawn white13 = new Pawn(PieceColor.WHITE);
        Pawn white44 = new Pawn(PieceColor.WHITE);

        board.movePiece(white13, 13);
        board.movePiece(white44, 44);

        List<Roundup> whiteRoundups = Roundup.getBestRoundups(board, PieceColor.WHITE);
        System.out.println("Roundups : " + whiteRoundups);
        List<March> whiteMarches = board.getMarches(PieceColor.WHITE);
        System.out.println("Marches : " + whiteMarches);
        List<? extends Move> whiteMoves = board.getPossibleMoves(PieceColor.WHITE);
        System.out.println("Moves : " + whiteMoves);
    }
}