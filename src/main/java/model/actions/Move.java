package model.actions;

import lombok.Data;
import model.environment.Square;
import model.pieces.Piece;

@Data
public abstract class Move {

    private final Piece mover;
    private final int from;
    private final int to;
}
