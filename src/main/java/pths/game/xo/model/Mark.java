package pths.game.xo.model;

import java.util.Arrays;

public enum Mark {
    X('x'), O('o');

    public final char ch;

    Mark(char ch) {
        this.ch = ch;
    }

    public static Mark of(char ch) {
        return Arrays.stream(values()).filter(m -> m.ch == ch).findFirst().orElse(null);
    }
}
