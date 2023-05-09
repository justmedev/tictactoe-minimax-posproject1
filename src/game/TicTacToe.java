package game;

import ai.BaseAI;
import ai.RandomAI;
import engine.Game;
import engine.Logger;
import engine.Renderer;
import game.errors.PositionOccupied;

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
    public Field field = new Field();
    public Renderer renderer = new Renderer();
    public Piece player;
    public Piece computer;
    public BaseAI ai;
    public Player lastPlayer = Player.PLAYER_0;

    public TicTacToe() {
        field.resetField();
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
        renderer.render(field.field);

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

        try {
            field.place(chosen.row(), chosen.col(), pieceToPlace);
        } catch (PositionOccupied e) {
            logger.infof("chosen: field[%d][%d]: %s", chosen.row(), chosen.col(), field.field[chosen.row()][chosen.col()]);
            logger.info("Client ", Player.otherPlayer(lastPlayer), " tried executing an invalid move! (plyr 1 is human; plyr 0 is a bot)");
            logger.info("Position already occupied! Try another one.");
            System.exit(1);
            nextTick(tick++);
            return;
        }

        if (checkIfWon()) {
            renderer.render(field.field);
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
                int row = Integer.parseInt(Character.toString(inp.charAt(1))) - 1;
                int col = Character.getNumericValue(inp.charAt(0)) - 10;

                return new ChosenField(row, col);
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
        if (checkIfWinningLine(field.field)) return true;
        else if (checkIfWinningDiagonal()) return true;
        else return checkIfWinningLine(swapMatrix(field.field));
    }

    private boolean checkIfWinningDiagonal() {
        Piece checkForPiece = field.field[0][0];
        if (checkForPiece == Piece.EMPTY) return false;
        int matches = 0;
        for (int i = 0; i < field.field.length; i++) {
            Piece p = field.field[i][i];
            if (p == checkForPiece) matches++;
        }

        return matches >= 3;
    }

    private boolean checkIfWinningLine(Piece[][] matrix) {
        for (Piece[] row : matrix) {
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

    private static Piece[][] swapMatrix(Piece[][] pField) {
        int originalTotalRows = pField.length;
        int originalTotalColumns = pField[0].length;

        Piece[][] newMatrix = new Piece[originalTotalColumns][originalTotalRows];

        for(int i=0; i< originalTotalRows; i++){
            for(int j=0; j < originalTotalColumns; j++){
                newMatrix[j][i] = pField[i][j];
            }
        }
        return newMatrix;
    }
}
