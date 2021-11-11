package model.actions;

import model.pieces.Piece;

public class March extends Move{
    public March(Piece mover, int from, int to) {
        super(mover, from, to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.toString().equals(toString());    }

    @Override
    public String toString() {
        return getFrom() + "-" + getTo();
    }
}
