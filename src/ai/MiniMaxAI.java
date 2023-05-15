package ai;

import engine.Logger;
import game.*;

import java.util.ArrayList;

public class MiniMaxAI extends BaseAI {
    private Logger logger = new Logger("ai.MiniMaxAI");
    private TicTacToe game;
    private int maxDepth;
    private ChosenField nextAIMove;

    // depth defines the difficulty
    public MiniMaxAI(TicTacToe ticTacToe, int maxDepth) {
        this.maxDepth = maxDepth;
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
        MiniMaxResult miniMax = miniMax2(field.field, game.computer, getAllEmptyPositions(field.field).size());
        return new ChosenField(miniMax.row, miniMax.col);
    }

    public ArrayList<ChosenField> getAllEmptyPositions(Piece[][] field) {
        ArrayList<ChosenField> empty = new ArrayList<>();

        for (int row = 0; row < field.length; row++) {
            for (int place = 0; place < field[row].length; place++) {
                if (field[row][place] == Piece.EMPTY) empty.add(new ChosenField(row, place));
            }
        }
        return empty;
    }

    record MiniMaxResult(int row, int col, int score) {}

    public MiniMaxResult miniMax2(Piece[][] fieldState, Piece player, int depth) {
        MiniMaxResult best = new MiniMaxResult(-1, -1, Integer.MAX_VALUE * (player == game.player ? 1 : -1));

        if (depth == 0 || GameState.checkIfWon(fieldState)) {
            return new MiniMaxResult(-1, -1, calculateScore(fieldState));
        }

        for (ChosenField emptyPos : getAllEmptyPositions(fieldState)) {
            int row = emptyPos.row();
            int col = emptyPos.col();

            fieldState[row][col] = player;
            MiniMaxResult score = miniMax2(fieldState, player.getOther(), depth - 1);
            fieldState[row][col] = Piece.EMPTY;

            MiniMaxResult newScore = new MiniMaxResult(row, col, score.score);
            if (player == game.computer) {
                if (newScore.score > best.score()) best = newScore;
            } else {
                if (newScore.score < best.score()) best = newScore;
            }
        }

        return best;
    }
}
