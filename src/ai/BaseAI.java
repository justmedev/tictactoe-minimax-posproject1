package ai;

import game.Piece;

public abstract class BaseAI {
    public abstract void initialize(Piece piece);
    public abstract void nextMove(Piece[] field, int tick);

}
