package ai;

import engine.Logger;
import game.ChosenField;
import game.Field;
import game.Piece;

import java.util.Random;

public class RandomAI extends BaseAI {
    private final static Logger logger = new Logger("RandomAI");
    private final static Random rng = new Random();

    @Override
    public ChosenField nextMove(Field field, int tick) {
        if (this.piece == Piece.EMPTY) {
            logger.err("AI init failed!");
            System.exit(1);
        }

        int row = rng.nextInt(0, 3);
        int col = rng.nextInt(0, 3);

        logger.infof("ai pos chosen: field[%d][%d]: %s", row, col, field.field[row][col]);
        if (field.positionOccupied(row, col)) {
            return nextMove(field, tick);
        }
        else return new ChosenField(row, col);
    }
}
