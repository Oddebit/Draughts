package model.pieces;

import lombok.Getter;
import model.environment.Cardinal;

import java.awt.*;

@Getter
public enum PieceColor {
    BLACK(Cardinal.SOUTH, Color.BLACK),
    WHITE(Cardinal.NORTH, Color.WHITE);

    private final Cardinal forward;
    private final Color color;

    PieceColor(Cardinal forward, Color color) {
        this.forward = forward;
        this.color = color;
    }

    public PieceColor getOpponent() {
        return values()[(this.ordinal() + 1) % 2];
    }
}
