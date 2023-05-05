import org.jetbrains.annotations.NotNull;

public class Engine {
    private Logger logger = new Logger("Engine");
    private Game game;

    public void attachTo(@NotNull Game game) {
        if (this.game != null) logger.err("Cannot attach to game");
        this.game = game;
    }

    public void runGame() {
        if (this.game == null) {
            logger.err("Attach first!");
            return;
        }
        start();
        nextTick();
    }


    public void start() {
        logger.info("game start!");
    }

    public void nextTick() {
    }
}
