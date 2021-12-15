package model.pieces;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Data
public abstract class Piece {

    private static HashMap<PieceColor, HashMap<Type, Image>> images = new HashMap<>();
    private Image image;

    private final Type type;
    private final int range;
    private final PieceColor color;

    private int position;

    static {
        try {
            for (PieceColor color : PieceColor.values()) {
                images.put(color, new HashMap<>());
                for (Type type : Type.values()) {
                    images.get(color).put(type, ImageIO.read(new File(String.format("res/img/pieces/%s_%s.png",
                            color.name().toLowerCase(), type.name().toLowerCase()))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Piece(Type type, int range, PieceColor color) {
        this.image = images.get(color).get(type);
        this.type = type;
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
