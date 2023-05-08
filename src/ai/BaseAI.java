package ai;

import game.Piece;

public abstract class BaseAI {
    public Piece piece = Piece.EMPTY;

    public void initialize(Piece piece) {
        this.piece = piece;
    }

    public abstract void nextMove(Piece[] field, int tick);

}
