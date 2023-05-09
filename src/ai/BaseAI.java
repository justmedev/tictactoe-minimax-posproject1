package ai;

import game.ChosenField;
import game.Field;
import game.Piece;

public abstract class BaseAI {
    public Piece piece = Piece.EMPTY;

    public void initialize(Piece piece) {
        this.piece = piece;
    }

    public abstract ChosenField nextMove(Field field, int tick);
}
