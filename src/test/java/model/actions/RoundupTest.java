package model.actions;

import model.environment.Board;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RoundupTest {

    @Test
    void getTakes(){
        Board board = new Board(Board.StartingOption.EMPTY);
        Pawn black = new Pawn(PieceColor.BLACK);
        Pawn white = new Pawn(PieceColor.WHITE);
        board.movePiece(black, 18);
        board.movePiece(white, 13);

        List<Take> whiteTakes = board.getTakes(white);
        System.out.println(whiteTakes);
        Assertions.assertEquals(1, whiteTakes.size());
        Assertions.assertTrue(whiteTakes.contains(new Take(white, 13, 22, black)));

        List<Take> blackTakes = board.getTakes(black);
        System.out.println(blackTakes);
        Assertions.assertEquals(1, blackTakes.size());
        Assertions.assertTrue(blackTakes.contains(new Take(black, 18, 9, white)));
    }

    @Test
    void getRoundups(){
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

        List<Roundup> whiteRoundups = Roundup.getBestRoundups(board, PieceColor.WHITE);
        System.out.println(whiteRoundups);
        List<? extends Move> whiteMoves = board.getPossibleMoves(PieceColor.WHITE);
        System.out.println(whiteMoves);
        List<Roundup> blackRoundups = Roundup.getBestRoundups(board, PieceColor.BLACK);
        System.out.println(blackRoundups);
        List<? extends Move> blackMoves = board.getPossibleMoves(PieceColor.BLACK);
        System.out.println(blackMoves);
    }
}