package model.moves;

import model.environment.Board;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import model.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RoundupTest {

    @Test
    void getTakes() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Pawn black = new Pawn(PieceColor.BLACK);
        Pawn white = new Pawn(PieceColor.WHITE);
        board.movePiece(black, 18);
        board.movePiece(white, 13);

        List<Take> whiteTakes = Take.listTakes(board, white);
        System.out.println(whiteTakes);
        Assertions.assertEquals(1, whiteTakes.size());
        Assertions.assertTrue(whiteTakes.contains(new Take(white, 13, 22, black)));

        List<Take> blackTakes = Take.listTakes(board, black);
        System.out.println(blackTakes);
        Assertions.assertEquals(1, blackTakes.size());
        Assertions.assertTrue(blackTakes.contains(new Take(black, 18, 9, white)));
    }

    @Test
    void getRoundups() {
        Board board = new Board(Board.StartingOption.EMPTY);

        Pawn white13 = new Pawn(PieceColor.WHITE);
        Pawn white44 = new Pawn(PieceColor.WHITE);
        Pawn black18 = new Pawn(PieceColor.BLACK);
        Pawn black28 = new Pawn(PieceColor.BLACK);
        Pawn black29 = new Pawn(PieceColor.BLACK);
        Pawn black19 = new Pawn(PieceColor.BLACK);
        Pawn black39 = new Pawn(PieceColor.BLACK);

        board.movePiece(white13, 13);
        board.movePiece(white44, 44);
        board.movePiece(black18, 18);
        board.movePiece(black28, 28);
        board.movePiece(black29, 29);
        board.movePiece(black19, 19);
        board.movePiece(black39, 39);

        System.out.println(board);
        List<Roundup> whiteRoundups = Roundup.listBestRoundups(board, PieceColor.WHITE);
        System.out.println("White roundups : " + whiteRoundups);
        List<? extends Move> whiteMoves = Move.listPossibleMoves(board, PieceColor.WHITE);
        System.out.println("White moves : " + whiteMoves);
        List<Roundup> blackRoundups = Roundup.listBestRoundups(board, PieceColor.BLACK);
        System.out.println("Black roundups : " + blackRoundups);
        List<? extends Move> blackMoves = Move.listPossibleMoves(board, PieceColor.BLACK);
        System.out.println("Black moves : " + blackMoves);
    }

    @Test
    void testRoundupsQueen() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Queen whiteQueen = new Queen(PieceColor.WHITE);
        Pawn blackPawn1 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn2 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn3 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn4 = new Pawn(PieceColor.BLACK);
        Pawn whitePawn = new Pawn(PieceColor.WHITE);
        List<Roundup> roundups;

        board.movePiece(whiteQueen, 27);
        board.movePiece(blackPawn1, 13);
        board.movePiece(blackPawn2, 9);

        roundups = Roundup.listRoundups(board, whiteQueen);
        Assertions.assertEquals(0, roundups.size());

        board.movePiece(blackPawn2, 22);

        roundups = Roundup.listRoundups(board, whiteQueen);
        Assertions.assertEquals(2, roundups.size());
    }

    @Test
    void testRoundupsQueen2() {
        Board board = new Board(Board.StartingOption.EMPTY);
        Queen whiteQueen = new Queen(PieceColor.WHITE);
        Pawn blackPawn1 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn2 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn3 = new Pawn(PieceColor.BLACK);
        Pawn blackPawn4 = new Pawn(PieceColor.BLACK);
        List<Roundup> roundups;

        board.movePiece(whiteQueen, 5);
        board.movePiece(blackPawn1, 14);
        board.movePiece(blackPawn2, 21);
        board.movePiece(blackPawn3, 11);
        board.movePiece(blackPawn4, 8);
        board.movePiece(new Pawn(PieceColor.WHITE), 24);
        board.movePiece(new Pawn(PieceColor.WHITE), 38);

        roundups = Roundup.listBestRoundups(board, PieceColor.WHITE);
        System.out.println(board);
        System.out.println(roundups);
        Assertions.assertEquals(4, roundups.size());
    }
}