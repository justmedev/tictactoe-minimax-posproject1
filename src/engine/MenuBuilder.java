package engine;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuBuilder {
    private final char ceiling = '-';
    private final char wall = '|';
    private String title;
    private final StringBuilder menu = new StringBuilder();

    public MenuBuilder createMenu(LinkedHashMap<String, String> data) {
        int longestKeyLength = 0;
        int longestValueLength = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }

            int keyLength = entry.getKey().length() + 2 /* ": " */;
            int valLength = entry.getValue().length();

            if (keyLength > longestKeyLength) longestKeyLength = keyLength;
            if (valLength > longestValueLength) longestValueLength = valLength;
        }

        String ceilingAndGround = "+" + Character.toString(ceiling).repeat(longestKeyLength + longestValueLength + 7) + "+" + "\n";

        if (title != null) {
            menu.append(ceilingAndGround);
            menu.append(wall).append("  ").append(title).append(" ".repeat(longestKeyLength + longestValueLength + 2 - title.length())).append(wall).append("\n");
        }

        menu.append(ceilingAndGround);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();

            if (v == null) {
                menu.append(String.format("%s%s%s\n", wall, " ".repeat(longestKeyLength + longestValueLength + 4), wall));
                continue;
            }

            String keyValueFormatted = String.format("%s%s", k, v);
            String spaces = " ".repeat(longestKeyLength + longestValueLength - keyValueFormatted.length());
            String row = wall + "  " + keyValueFormatted + spaces + "  " + wall;

            menu.append(row + "\n");
        }
        menu.append(ceilingAndGround);

        return this;
    }

    public void display() {
        System.out.println(menu.toString());
    }
}

