package engine;

import org.jetbrains.annotations.NotNull;

public class Engine {
    private static final Logger logger = new Logger("engine.Engine");
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
        game.beforeFirstTick();
    }
}
