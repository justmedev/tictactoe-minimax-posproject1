import java.util.Arrays;
import java.util.Scanner;

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
    public void beforeFirstTick() {
        System.out.println("Choose player o/x:");

        Scanner s = new Scanner(System.in);
        if (s.hasNext()) {
            String inp = s.next().trim().toLowerCase();
            if (inp.equals(Piece.O.getSymbolString())) {
                player = Piece.O;
                computer = Piece.X;
            } else if (inp.equals(Piece.X.getSymbolString())) {
                player = Piece.X;
                computer = Piece.O;
            } else {
                logger.err("");
            }

            System.out.printf("Hello! You are player %s%n", player);
            System.out.println("This is the empty starting field. Rows are A-C and cols are 1-3!");
            nextTick(0); // Execute first game tick after start
        }
    }

    @Override
    public void nextTick(int tick) {
        /*try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        // renderer.render(field);
        // nextTick(tick++);
    }
}
