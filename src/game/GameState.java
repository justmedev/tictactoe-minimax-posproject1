package game;

public class GameState {
    public static boolean checkIfWon(Piece[][] field) {
        return getWinState(field).gameWon();
    }

    public static WinState getWinState(Piece[][] field) {
        WinState drawWin = checkForDraw(field);
        if (drawWin.gameWon()) return drawWin;

        WinState winningLine = checkIfWinningLine(field);
        if (winningLine.gameWon()) return winningLine;

        WinState winningDiagonal = checkIfWinningDiagonal(field);
        if (winningDiagonal.gameWon()) return winningDiagonal;

        return checkIfWinningLine(swapMatrix(field));
    }

    private static WinState checkForDraw(Piece[][] field) {
        int emptyPieces = 0;
        for (Piece[] rows : field) {
            for (Piece piece : rows) {
                if(piece == Piece.EMPTY) emptyPieces++;
            }
        }
        return emptyPieces == 0 ? WinState.DRAW : WinState.CONTINUE_GAME;
    }

    private static WinState checkIfWinningDiagonal(Piece[][] field) {
        Piece checkForPiece = field[0][0];
        if (checkForPiece != Piece.EMPTY) {
            int matches = 0;
            for (int i = 0; i < field.length; i++) {
                Piece p = field[i][i];
                if (p == checkForPiece) matches++;
            }
            if (matches >= 3) return WinState.fromPiece(checkForPiece);
        }

        checkForPiece = field[0][field.length - 1];
        if (checkForPiece == Piece.EMPTY) return WinState.CONTINUE_GAME;

        int matches = 0;
        for (int i = field.length - 1; i >= 0; i--) {
            Piece p = field[field.length - i - 1][i];
            if (p == checkForPiece) matches++;
        }
        return matches >= 3 ? WinState.fromPiece(checkForPiece) : WinState.CONTINUE_GAME;
    }

    private static WinState checkIfWinningLine(Piece[][] matrix) {
        for (Piece[] row : matrix) {
            int matchingAmount = 0;
            Piece lastMatch = Piece.EMPTY;

            for (int i = 0; i < row.length; i++) {
                Piece piece = row[i];
                if (i == 0) lastMatch = piece;
                if (lastMatch == piece && piece != Piece.EMPTY) matchingAmount++;
                else matchingAmount = 0;
            }

            if (matchingAmount == 3) return WinState.fromPiece(lastMatch);
        }
        return WinState.CONTINUE_GAME;
    }

    private static Piece[][] swapMatrix(Piece[][] pField) {
        int originalTotalRows = pField.length;
        int originalTotalColumns = pField[0].length;

        Piece[][] newMatrix = new Piece[originalTotalColumns][originalTotalRows];

        for (int i = 0; i < originalTotalRows; i++) {
            for (int j = 0; j < originalTotalColumns; j++) {
                newMatrix[j][i] = pField[i][j];
            }
        }
        return newMatrix;
    }
}
