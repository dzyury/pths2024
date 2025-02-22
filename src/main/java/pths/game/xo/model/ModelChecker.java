package pths.game.xo.model;

import java.util.Arrays;

import static pths.game.xo.model.GameState.DRAW;
import static pths.game.xo.model.GameState.INVALID;
import static pths.game.xo.model.GameState.O_TURN;
import static pths.game.xo.model.GameState.O_WON;
import static pths.game.xo.model.GameState.X_TURN;
import static pths.game.xo.model.GameState.X_WON;

public class ModelChecker {
    public GameState check(Mark[][] model) {
        if (model.length != 3 || Arrays.stream(model).anyMatch(line -> line.length != 3)) {
            return INVALID;
        }

        int k = 0;
        int xa = 0;
        int oa = 0;
        for (Mark[] marks : model) {
            for (int j = 0; j < marks.length; j++, k++) {
                Mark mark = marks[j];
                if (mark == Mark.X) xa |= 1 << k;
                if (mark == Mark.O) oa |= 1 << k;
            }
        }

        int nx = Integer.bitCount(xa);
        int no = Integer.bitCount(oa);
        if (no > nx || nx - no > 1) return INVALID;
        boolean xw = wins(xa);
        boolean ow = wins(oa);
        if (xw && ow) return INVALID;
        if (xw) return (nx > no) ? X_WON : INVALID;
        if (ow) return O_WON;
        if (no + nx == 9) return DRAW;
        if (no == nx) return X_TURN;
        if (no + 1 == nx) return O_TURN;
        return INVALID;
    }

    private boolean wins(int a) {
        int[] wins = {
                0b111000000,
                0b000111000,
                0b000000111,
                0b100100100,
                0b010010010,
                0b001001001,
                0b100010001,
                0b001010100,
        };
        return Arrays.stream(wins).anyMatch(i -> (i & a) == i);
    }
}
