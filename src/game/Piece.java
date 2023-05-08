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
}
