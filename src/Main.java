import engine.Engine;
import game.TicTacToe;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        TicTacToe ticTacToe = new TicTacToe();

        engine.attachTo(ticTacToe);
        engine.runGame();
    }
}