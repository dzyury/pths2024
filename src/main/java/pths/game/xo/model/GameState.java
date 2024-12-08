package pths.game.xo.model;

public enum GameState {
    X_WON, X_TURN, O_WON, O_TURN, DRAW, INVALID;

    public boolean isEnd() {
        return this == X_WON || this == O_WON || this == DRAW;
    }
}
