package game;

public enum Piece {
    X('x'),
    O('o'),
    EMPTY(' ');

    private char symbol;
    Piece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getSymbolString() {
        return Character.toString(symbol);
    }

    public Piece getOther() {
        if (this == EMPTY) return null;
        return this == X ? O : X;
    }
}
