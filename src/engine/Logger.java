package engine;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Logger {
    public String tag;


    public Logger(@NotNull String tag) {
        this.tag = tag;
    }

    private String formattedTag() {
        return "[%s]".formatted(tag);
    }

    private String formatArgs(Object ...args) {
        if (args == null) return "null";
        else if (args.length > 1) {
            String asString = Arrays.deepToString(args);
            return asString.substring(1, asString.length() - 1);
        } else return args[0].toString();
    }

    public void err(Object ...args) {
        System.err.printf("%s %s%n", formattedTag(), formatArgs(args));
    }

    public void info(Object ...args) {
        System.out.printf("%s %s%n", formattedTag(), formatArgs(args));
    }

    public void infof(String message, Object ...args) {
        System.out.printf("%s %s%n", formattedTag(), message.formatted(args));
    }
}
