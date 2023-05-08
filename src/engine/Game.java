package engine;

public abstract class Game {
    public abstract void beforeFirstTick();
    public abstract void nextTick(int tick);
}
