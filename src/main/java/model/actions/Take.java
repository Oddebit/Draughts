package model.actions;

import lombok.Getter;
import model.pieces.Piece;

@Getter
public class Take extends Move {
    private final Piece taken;

    public Take(Piece mover, int from, int to, Piece taken) {
        super(mover, from, to);
        this.taken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.toString().equals(toString());
    }

    @Override
    public String toString() {
        return getFrom() + "x" + getTo();
    }
}
