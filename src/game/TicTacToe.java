package game;

import ai.BaseAI;
import ai.MiniMaxAI;
import ai.RandomAI;
import engine.Game;
import engine.Logger;
import engine.MenuBuilder;
import engine.Renderer;
import game.errors.PositionOccupied;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

enum Player {
    PLAYER_0,
    PLAYER_1;

    public static Player otherPlayer(Player player) {
        return player == PLAYER_0 ? PLAYER_1 : PLAYER_0;
    }
}

enum Enemy {
    RANDOM_AI,
    MIN_MAX,
    PLAYER_1,
}

public class TicTacToe extends Game {
    //region vars
    private Scanner scanner = new Scanner(System.in);
    private Logger logger = new Logger("game.TicTacToe");
    public Field field = new Field();
    public Renderer renderer = new Renderer();
    public Piece player;
    public Piece computer;
    public BaseAI ai;
    public Player lastPlayer = Player.PLAYER_0;
    public Enemy enemy;
    //endregion

    public TicTacToe() {
        field.resetField();
    }

    //region start game
    @Override
    public void beforeFirstTick() {
        makeMenu();
    }

    public void makeMenu() {
        MenuBuilder menuBuilder = new MenuBuilder();
        menuBuilder.createMenu(new LinkedHashMap<>(Map.of(
                "a\t", "Against dumb ai",
                "b\t", "Against smart ai",
                "c\t", "Two Player Mode"
        ))).display();

        switch (readInput("a, b or c? ").trim().charAt(0)) {
            case 'a' -> startGame(Enemy.RANDOM_AI);
            case 'b' -> startGame(Enemy.MIN_MAX);
            case 'c' -> startGame(Enemy.PLAYER_1);
            default -> {
                System.out.println("Invalid Input!");
                makeMenu();
            }
        }
    }

    public void startGame(Enemy enemy) {
        this.enemy = enemy;
        System.out.println("Choose symbol o/x:");

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
            System.out.println("This is the empty starting field. Rows are A-C and cols are 1-3! Example move: a2");

            if (enemy == Enemy.MIN_MAX) initMiniMaxAI();
            else if (enemy == Enemy.RANDOM_AI) initRandomAI();
            else nextTick(0);
        }
    }

    public void initRandomAI() {
        ai = new RandomAI();
        ai.initialize(computer);
        nextTick(0); // Execute first game tick after start
    }

    public void initMiniMaxAI() {
        ai = new MiniMaxAI(this, 6);
        ai.initialize(computer);
        nextTick(0);
    }
    //endregion

    @Override
    public void nextTick(int tick) {
        renderer.render(field.field);
        Player currentPlayer = Player.otherPlayer(lastPlayer);

        ChosenField chosen;
        Piece pieceToPlace;
        if (currentPlayer == Player.PLAYER_1) {
            chosen = getFieldFromPlayer();
            pieceToPlace = player;
        } else if (enemy == Enemy.PLAYER_1) {
            chosen = getFieldFromPlayer();
            pieceToPlace = computer;
        } else {
            chosen = ai.nextMove(field, tick);
            pieceToPlace = computer;
        }
        if (chosen == null) return; // Something went wrong; Panic & Exit

        try {
            field.place(chosen.row(), chosen.col(), pieceToPlace);
        } catch (PositionOccupied e) {
            // logger.infof("chosen: field[%d][%d]: %s", chosen.row(), chosen.col(), field.field[chosen.row()][chosen.col()]);
            // logger.info("Client ", Player.otherPlayer(lastPlayer), " tried executing an invalid move! (plyr 1 is human; plyr 0 is a bot)");
            logger.info("Position already occupied! Try another one.");
            nextTick(tick++);
            return;
        }

        if (GameState.checkIfWon(field.field)) {
            renderer.render(field.field);

            if (currentPlayer == Player.PLAYER_1) logger.info("Congrats :) You won the game!");
            else logger.info("Bruh... Couldn't even win against some dumb ai????");
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
            if (Pattern.matches("([A-c])([0-3])", inp)) {
                int row = Integer.parseInt(Character.toString(inp.charAt(1))) - 1;
                int col = Character.getNumericValue(inp.charAt(0)) - 10;

                return new ChosenField(row, col);
            } else {
                logger.err("Invalid format! First column then row: a1, c3, ...");
                return getFieldFromPlayer();
            }
        }
        return null;
    }

    //region util
    public void clearConsole() {
        System.out.println(System.lineSeparator().repeat(100)); // cross-platform & always works
    }

    public int readInputAsInt(String message) {
        return Integer.parseInt(readInput(message));
    }

    public String readInput(String message) {
        System.out.println(message);
        while (true) {
            if (scanner.hasNext()) return scanner.next();
        }
    }
    //endregion
}
