package game;

import game.errors.PositionOccupied;

import java.util.Arrays;

public class Field {
    public Piece[][] field = new Piece[3][3];

    public enum Position {
        EMPTY,
        PLAYER_X,
        PLAYER_O,
    }

    public boolean positionOccupied(int row, int col) {
        return field[row][col] != Piece.EMPTY;
    }

    public void resetField() {
        for (int row = 0; row < field.length; row++) {
            field[row] = new Piece[3];
            Arrays.fill(field[row], Piece.EMPTY);
        }
    }

    public void place(int row, int col, Piece piece) throws PositionOccupied {
        if (positionOccupied(row, col)) throw new PositionOccupied(row, col);
        field[row][col] = piece;
    }
}