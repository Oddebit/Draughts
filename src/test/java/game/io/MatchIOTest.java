package game.io;

import game.Match;
import model.moves.March;
import model.moves.Take;
import model.pieces.Pawn;
import model.pieces.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchIOTest {

//    @Test
//    void write() {
//        Match match = new Match();
//        match.addMove(new March(new Pawn(PieceColor.BLACK), 1, 6));
//        MatchIO.write(match, "pvc");
//    }

    @Test
    void read() {
        System.out.println(MatchIO.read("2021_12_15_09_09_56", "pvc"));
    }
}