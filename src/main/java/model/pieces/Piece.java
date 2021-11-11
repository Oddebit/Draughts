package model.pieces;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public abstract class Piece {
    private final PieceColor color;
    private int position;
}
