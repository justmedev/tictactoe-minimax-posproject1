package ai;

import engine.Logger;
import game.*;

public class MinMaxAI extends BaseAI {
    private Logger logger = new Logger("ai.MinMaxAI");
    private TicTacToe game;

    public MinMaxAI(TicTacToe ticTacToe) {
        this.game = ticTacToe;

        this.ai.initialize(ticTacToe.computer);
    }

    private RandomAI ai = new RandomAI();

    private int calculateScore(Piece[][] field) {
        WinState aiPiece = WinState.fromPiece(game.computer);
        WinState winState = GameState.getWinState(field);

        if (winState == aiPiece) return 1;
        else if (winState == WinState.fromPiece(game.computer.getOther())) return -1;
        else return 0;
    }

    @Override
    public ChosenField nextMove(Field field, int tick) {
        logger.infof("AI nextMove calculated score: %s", calculateScore(field.field));
        return ai.nextMove(field, tick);
    }
}
