import org.jetbrains.annotations.NotNull;

public class Logger {
    public String tag;


    public Logger(@NotNull String tag) {
        this.tag = tag;
    }

    private String formattedTag() {
        return "[%s]".formatted(tag);
    }

    public void err(String message) {
        System.err.printf("%s %s%n", formattedTag(), message);
    }

    public void info(String message) {
        System.out.printf("%s %s%n", formattedTag(), message);
    }
}
