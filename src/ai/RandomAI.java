package ai;

import engine.Logger;
import game.Piece;

public class RandomAI extends BaseAI {
    private final static Logger logger = new Logger("RandomAI");
    public Piece piece = Piece.EMPTY;

    @Override
    public void initialize(Piece piece) {
        this.piece = piece;
    }

    @Override
    public void nextMove(Piece[] field, int tick) {
        if (this.piece == Piece.EMPTY) {
            logger.err("AI init failed!");
            System.exit(1);
        }
    }
}
