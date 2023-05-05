public class Renderer {
    private Logger logger = new Logger("Renderer");
    private String corner = "+";
    private String wall = "|";
    private String ceiling = "-";
    private String floor = "-";

    public void render(Piece[][] field) {
        char[][] matrix = new char[4][4];

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            if (rowIndex == 0) {
                matrix[0] = new char[]{
                        ' ',
                        'A',
                        'B',
                        'C',
                };
                continue;
            }

            for (int index = 0; index < matrix[rowIndex].length; index++) {
                if (Preferences.DEBUG_FIELD && index != 0) {
                    logger.infof("field[%d][%d] = %c%n", rowIndex - 1, index - 1, field[rowIndex - 1][index - 1].getSymbol());
                }

                matrix[rowIndex][index] = index != 0 ? field[rowIndex - 1][index - 1].getSymbol() : (char) (rowIndex + '0');
            }
        }

        String table = makeTable(matrix, 3);
        System.out.println(table);
    }

    public String makeTable(char[][] tableMatrix, int innerWidth) {
        StringBuilder sb = new StringBuilder();

        int fullWidth = tableMatrix[0].length * innerWidth + tableMatrix[0].length - 1;

        makeFloor(sb, fullWidth);

        for (int i = 0; i < tableMatrix.length; i++) {
            char[] cols = tableMatrix[i];
            sb.append(wall);

            for (char col : cols) {
                sb.append(' ');
                sb.append(col);
                sb.append(' ');
                sb.append(wall);
            }

            sb.append('\n');
            makeFloor(sb, fullWidth);
        }

        return sb.toString();
    }

    private void makeFloor(StringBuilder sb, int fullWidth) {
        for (int j = 0; j < fullWidth + 2; j++) {
            if (j % 4 == 0) sb.append(corner);
            else sb.append(floor);
        }
        sb.append('\n');
    }
}
