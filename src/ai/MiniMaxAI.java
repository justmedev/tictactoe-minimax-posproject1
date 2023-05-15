package ai;

import engine.Logger;
import game.*;

public class MiniMaxAI extends BaseAI {
    private static final Logger logger = new Logger("ai.MiniMaxAI");
    private final TicTacToe game;

    // depth defines the difficulty (ignored atm; max difficulty is always on)
    public MiniMaxAI(TicTacToe ticTacToe, int maxDepth) {
        this.game = ticTacToe;
    }

    private int calculateScore(Piece[][] field) {
        WinState aiPiece = WinState.fromPiece(game.computer);
        WinState winState = GameState.getWinState(field);

        if (winState == aiPiece) return 1;
        else if (winState == WinState.fromPiece(game.computer.getOther())) return -1;
        else return 0;
    }

    record MinimaxResult(int row, int col, int score) {}

    public MinimaxResult minimax(Piece[][] fieldState, Piece player, int depth) {
        MinimaxResult best = new MinimaxResult(-1, -1, Integer.MAX_VALUE * (player == game.player ? 1 : -1));

        if (depth == 0 || GameState.checkIfWon(fieldState)) {
            return new MinimaxResult(-1, -1, calculateScore(fieldState));
        }

        for (ChosenField emptyPos : GameState.getAllEmptyPositions(fieldState)) {
            int row = emptyPos.row();
            int col = emptyPos.col();

            fieldState[row][col] = player;
            MinimaxResult score = minimax(fieldState, player.getOther(), depth - 1);
            fieldState[row][col] = Piece.EMPTY;

            MinimaxResult newScore = new MinimaxResult(row, col, score.score);
            if (player == game.computer) {
                if (newScore.score > best.score()) best = newScore;
            } else {
                if (newScore.score < best.score()) best = newScore;
            }
        }

        return best;
    }

    @Override
    public ChosenField nextMove(Field field, int tick) {
        MinimaxResult miniMax = minimax(field.field, game.computer, GameState.getAllEmptyPositions(field.field).size());
        return new ChosenField(miniMax.row, miniMax.col);
    }
}
