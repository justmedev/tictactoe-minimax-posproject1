package ai;

import engine.Logger;
import game.Piece;

public class RandomAI extends BaseAI {
    private final static Logger logger = new Logger("RandomAI");

    @Override
    public void nextMove(Piece[] field, int tick) {
        if (this.piece == Piece.EMPTY) {
            logger.err("AI init failed!");
            System.exit(1);
        }
    }
}
