package pths.game.xo;

public interface Subscriber {
    void stateChanged(GameState oldState, GameState newState);
}
