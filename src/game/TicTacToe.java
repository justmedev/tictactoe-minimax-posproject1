package game;

import ai.BaseAI;
import ai.RandomAI;
import engine.Game;
import engine.Logger;
import engine.Renderer;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

enum Player {
    PLAYER_0,
    PLAYER_1;

    public static Player otherPlayer(Player player) {
        return player == PLAYER_0 ? PLAYER_1 : PLAYER_0;
    }
}


public class TicTacToe extends Game {
    private Scanner scanner = new Scanner(System.in);
    private Logger logger = new Logger("game.TicTacToe");
    public Piece[][] field = new Piece[3][3];
    public Renderer renderer = new Renderer();
    public Piece player;
    public Piece computer;
    public BaseAI ai;
    public Player lastPlayer = Player.PLAYER_0;

    public TicTacToe() {
        resetField();
    }

    public void resetField() {
        for (int row = 0; row < field.length; row++) {
            field[row] = new Piece[3];
            Arrays.fill(field[row], Piece.EMPTY);
        }
    }

    @Override
    public void beforeFirstTick() {
        System.out.println("Choose player o/x:");

        if (scanner.hasNext()) {
            String inp = scanner.next().trim().toLowerCase();
            if (inp.equals(Piece.O.getSymbolString())) {
                player = Piece.O;
                computer = Piece.X;
            } else if (inp.equals(Piece.X.getSymbolString())) {
                player = Piece.X;
                computer = Piece.O;
            } else {
                logger.err("You have to either choose x or o!");
                clearConsole();
                beforeFirstTick();
                return;
            }

            System.out.printf("Hello! You are player %s%n", player);
            System.out.println("This is the empty starting field. Rows are A-C and cols are 1-3!");

            ai = new RandomAI();
            ai.initialize(computer);
            nextTick(0); // Execute first game tick after start
        }
    }

    @Override
    public void nextTick(int tick) {
        renderer.render(field);

        ChosenField chosen;
        Piece pieceToPlace;
        if (lastPlayer == Player.PLAYER_0) {
            chosen = getFieldFromPlayer();
            pieceToPlace = player;
        } else {
            chosen = ai.nextMove(field, tick);
            pieceToPlace = computer;
        }
        if (chosen == null) return; // Something went wrong; Panic & Exit
        Piece pieceAtChosen = field[chosen.col()][chosen.row()];

        // Position already occupied
        if (pieceAtChosen != Piece.EMPTY) {
            logger.info("Position already occupied! Try another one.");
            nextTick(tick++);
            return;
        }

        field[chosen.col()][chosen.row()] = pieceToPlace;

        if (checkIfWon()) {
            renderer.render(field);
            logger.info("You won the game!");
            return;
        }

        lastPlayer = Player.otherPlayer(lastPlayer);
        // clearConsole();
        nextTick(tick++);
    }

    public ChosenField getFieldFromPlayer() {
        System.out.println("Enter field: ");

        if (scanner.hasNext()) {
            String inp = scanner.next().trim().toLowerCase();
            if (Pattern.matches("([A-z])([0-9])", inp)) {
                int col = Character.getNumericValue(inp.charAt(0)) - 10;
                int row = Integer.parseInt(Character.toString(inp.charAt(1))) - 1;

                logger.info(col, row);
                return new ChosenField(col, row);
            } else {
                logger.err("Invalid format! First column then row: a1, c3, ...");
                getFieldFromPlayer();
            }
        }
        return null;
    }

    public void clearConsole() {
        System.out.println(System.lineSeparator().repeat(100)); // cross-platform & always works
    }

    public boolean checkIfWon() {
        for (Piece[] row : field) {
            int matchingAmount = 0;
            Piece lastMatch = Piece.EMPTY;

            for (int i = 0; i < row.length; i++) {
                Piece piece = row[i];
                if (i == 0) lastMatch = piece;
                if (lastMatch == piece && piece != Piece.EMPTY) matchingAmount++;
                else matchingAmount = 0;
            }

            if (matchingAmount == 3) {
                return true;
            }
        }

        return false;
    }
}
