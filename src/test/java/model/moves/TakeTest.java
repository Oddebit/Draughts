package model.moves;

import model.environment.Board;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import model.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TakeTest {

    @Test
    void listTakesPawn() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Pawn black = new Pawn(PieceColor.BLACK);
        Pawn white = new Pawn(PieceColor.WHITE);

        board.movePiece(black, 26);
        board.movePiece(white, 21);

        Assertions.assertEquals(0, Take.listTakes(board, white).size());
    }

    @Test
    void listTakesQueen() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Queen blackQueen = new Queen(PieceColor.BLACK);
        Pawn whitePawn = new Pawn(PieceColor.WHITE);

        board.movePiece(blackQueen, 27);
        board.movePiece(whitePawn, 43);

        Assertions.assertEquals(1, Take.listTakes(board, blackQueen).size());
    }

    @Test
    void testRoundupsQueen() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Queen whiteQueen = new Queen(PieceColor.WHITE);
        Pawn blackPawn1 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn2 = new Pawn(PieceColor.BLACK);

        board.movePiece(whiteQueen, 27);
        board.movePiece(blackPawn1, 13);
        board.movePiece(blackPawn2, 9);

        List<Take> takes = Take.listTakes(board, whiteQueen);
        Assertions.assertEquals(0, takes.size());
    }
}