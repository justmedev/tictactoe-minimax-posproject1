package game;

public enum WinState {
    X,
    O,
    DRAW,
    // Nobody won, signals to just continue the game instead of exiting.
    CONTINUE_GAME;

    public static WinState fromPiece(Piece piece) {
        if (piece == Piece.X) return WinState.X;
        else if (piece == Piece.O) return WinState.O;

        return WinState.CONTINUE_GAME;
    }

    public boolean gameWon() {
        return this == X || this == O || this == DRAW;
    }
}
