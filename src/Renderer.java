public class Renderer {
    private String corner = "+";
    private String wall = "|";
    private String ceiling = "-";
    private String floor = "-";

    public void render(Piece[][] field) {
        StringBuilder sb = new StringBuilder();

        int width = field.length * 3 + 2;

        sb.append(corner);
        sb.append(ceiling.repeat(width));
        sb.append(corner);
        sb.append('\n');

        for (int i = 0; i < field.length; i++) {
            Piece[] pieces = field[i];
            sb.append(wall);

            for (Piece piece : pieces) {
                sb.append(' ');
                sb.append(piece.getSymbol());
                sb.append(' ');
                sb.append(wall);
            }

            sb.append('\n');
            if (i + 1 != field.length) {
                for (int j =  0; j < width + 2; j++) {
                    if (j % 4 == 0) sb.append(corner);
                    else sb.append(floor);
                }
                sb.append('\n');
            }
        }

        sb.append(corner);
        sb.append(floor.repeat(width));
        sb.append(corner);

        System.out.println(sb);
    }
}
