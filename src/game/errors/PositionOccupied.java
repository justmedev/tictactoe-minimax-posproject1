package game.errors;

public class PositionOccupied extends Exception {
    public PositionOccupied(int row, int col) {
        super("The board position at {row=%d;col=%d}".formatted(row, col));
    }
}
