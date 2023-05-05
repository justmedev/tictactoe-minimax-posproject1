import java.util.Arrays;

public class TicTacToe extends Game {
    private Logger logger = new Logger("TicTacToe");
    public Field[][] field = new Field[3][3];
    public Piece player;
    public Piece computer;

    public TicTacToe() {
        resetField();
    }

    public void resetField() {
        for (int row = 0; row < field.length; row++) {
            field[row] = new Field[3];
            Arrays.fill(field[row], Field.PLAYER_O);
        }
    }

    @Override
    public void onStart() {
        System.out.println("Hello! You are player");
    }

    @Override
    public void onTick() {

    }
}
