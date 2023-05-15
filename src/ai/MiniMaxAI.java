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
        logger.infof("AI nextMove calculated score: %s", calculateScore(new Piece[][]{
                {Piece.X, Piece.X, Piece.X},
                {Piece.EMPTY, Piece.O, Piece.EMPTY},
                {Piece.O, Piece.O, Piece.EMPTY},
        }));

        int score = miniMax(field.field, game.computer, 0);
        logger.infof("AI nextMove calculated score: %s", score);
        return nextAIMove;
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

    public int miniMax(Piece[][] fieldState, Piece player, int depth) {
        // check if we reached search depth or if game end state is reached and return score
        if (depth >= maxDepth || GameState.checkIfWon(fieldState)) {
            return calculateScore(fieldState);
        }

        int maxScore = 0;
        int minScore = 0;

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<ChosenField> moves = new ArrayList<>();
        ArrayList<ChosenField> availablePositions = getAllEmptyPositions(game.field.field);

        for (ChosenField availablePosition : availablePositions) {
            Piece[][] possibleField = game.field.field.clone();

            possibleField[availablePosition.row()][availablePosition.col()] = game.computer;
            scores.add(miniMax(possibleField, game.player, depth + 1));

            possibleField[availablePosition.row()][availablePosition.col()] = Piece.EMPTY;
            moves.add(availablePosition);
        }

        nextAIMove = moves.get(0);
        if (player == game.player) {
            maxScore = scores.get(0);

            for (int score : scores) {
                if (score > maxScore) {
                    maxScore = scores.get(score);
                    nextAIMove = moves.get(score);
                }
            }

            return maxScore;
        } else {
            minScore = scores.get(0);

            for (int score : scores) {
                if (score < minScore) {
                    minScore = scores.get(score);
                    nextAIMove = moves.get(score);
                }
            }

            return minScore;
        }
        /*
        var scores = []; //keeps track of score of current board state
        var moves = []; //keeps track of all available moves of current board state.
        var opponent = player == "X" ? "O" : "X";
        var successors = get_available_moves(state); //get's all the available moves from a given board.

        //If a human is playing then we check to see what moves it will take to win the game
        if (player == "X") {
            AI_MOVE = moves[0]; //set AI_Move to the first move
            maxScore = scores[0]; //set max score to the first score
            for (var s in scores) { //iterate through all the scores
                if (scores[s] > maxScore) { //looks for all scores then is greater then max
                    maxScore = scores[s]; //set maxScore to that score
                    AI_MOVE = moves[s]; //set AI_Move to the best possible move
                }
            }
            return maxScore; //return maxScore
        } else {
            AI_MOVE = moves[0]; //Set ai_move and minScore to the first move and score
            minScore = scores[0];
            for (var s in scores) {
                if (scores[s] < minScore) {
                    minScore = scores[s];
                    AI_MOVE = moves[s];
                }
            }
            return minScore;
        }*/
    }
}
