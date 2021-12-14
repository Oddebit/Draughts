package model.moves;

import model.environment.Board;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import model.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MarchTest {

    @Test
    void listMarches() {
        Board board = new Board(Board.StartingOption.EMPTY);
        List<March> marches;

        Pawn pawn = new Pawn(PieceColor.BLACK);
        board.movePiece(pawn, 5);
        marches = March.listMarches(board, pawn);
        System.out.println(marches);
        Assertions.assertEquals(1, marches.size());

        board.removePiece(pawn);
        Queen queen = new Queen(PieceColor.WHITE);
        board.movePiece(queen, 27);
        marches = March.listMarches(board, queen);
        System.out.println(marches);
        Assertions.assertEquals(13, marches.size());
    }
}