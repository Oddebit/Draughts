package model.pieces;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Data
public abstract class Piece {

    private final Type type;
    private Image image;
    private final int range;

    private final PieceColor color;
    private int position;

    public Piece(Type type, int range, PieceColor color) {
        this.type = type;
        try {
            this.image = ImageIO.read(new File(String.format("res/pieces/%s_%s.png",
                    color.name().toLowerCase(), type.name().toLowerCase())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.range = range;
        this.color = color;
    }

    public static Piece copyOf(Piece piece) {
        return piece.getType().getPiece(piece.getColor());
    }

    public enum Type {
        PAWN (Pawn.class),
        QUEEN (Queen.class);

        private final Class<? extends Piece> pieceClass;

        Type(Class<? extends Piece> pieceClass) {
            this.pieceClass = pieceClass;
        }

        public Piece getPiece(PieceColor color) {
            try {
                return pieceClass.getDeclaredConstructor(PieceColor.class).newInstance(color);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
