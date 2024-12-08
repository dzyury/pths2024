package pths.game.xo.model;

public interface Subscriber {
    void stateChanged(GameState oldState, GameState newState);
}
