package apps.cvc;

import model.environment.Board;
import model.environment.Square;
import model.moves.Move;
import model.pieces.Piece;

public class BoardCloner {

    public static Board cloneBoard(Board board, Move move) {
        Square[] squares = board.getSquares();

        Board clonedBoard = new Board(Board.StartingOption.EMPTY);
        for (int i = 0; i < squares.length; i++) {
            Piece piece = squares[i].getPiece();
            if (piece != null) {
                clonedBoard.movePiece(Piece.copyOf(piece), i + 1);
            }
        }
        clonedBoard.addMove(Move.copyOf(move));
        return clonedBoard;
    }
}
