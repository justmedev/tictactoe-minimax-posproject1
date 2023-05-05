import java.util.Arrays;

public class TicTacToe extends Game {
    private Logger logger = new Logger("TicTacToe");
    public Piece[][] field = new Piece[3][3];
    public Renderer renderer = new Renderer();
    public Piece player;
    public Piece computer;

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
    public void onStart() {
        System.out.println("Hello! You are player");
    }

    @Override
    public void onTick() {

        // renderer.render(field);
        // nextTick(tick++);
    }
}
