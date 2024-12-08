package pths.game.xo

import pths.game.xo.model.GameState
import pths.game.xo.model.Mark
import pths.game.xo.model.ModelChecker
import spock.lang.Specification

import static pths.game.xo.model.GameState.DRAW
import static pths.game.xo.model.GameState.INVALID
import static pths.game.xo.model.GameState.O_TURN
import static pths.game.xo.model.GameState.O_WON
import static pths.game.xo.model.GameState.X_TURN
import static pths.game.xo.model.GameState.X_WON

class ModelCheckerTest extends Specification {
    ModelChecker checker = new ModelChecker()

    void "check"(String[] lines, GameState res) {
        given:
        Mark[][] model = lines.collect {
            it.toCharArray().collect { Mark.of(it) }
        }

        expect:
        checker.check(model) == res

        where:
        lines                 || res
        ["xxx", "   ", "  "]  || INVALID
        ["xx", "  ", "  "]    || INVALID
        ["xxx", "   ", "   "] || INVALID
        ["xxx", "   ", "o  "] || INVALID
        ["xxx", " oo", "oo "] || INVALID
        ["ooo", "xx ", "   "] || INVALID
        ["xxx", " oo", "o  "] || INVALID
        ["xxx", "ooo", "   "] || INVALID

        ["xxx", "oo ", "   "] || X_WON
        ["oo ", "xxx", "   "] || X_WON
        ["oo ", "   ", "xxx"] || X_WON
        ["xoo", "x  ", "x  "] || X_WON
        ["oxo", " x ", " x "] || X_WON
        ["oox", "  x", "  x"] || X_WON
        ["oox", " x ", "x  "] || X_WON
        ["xoo", " x ", "  x"] || X_WON

        ["ooo", "xx ", "x  "] || O_WON
        ["xx ", "ooo", "x  "] || O_WON
        ["xx ", "x  ", "ooo"] || O_WON
        ["oxx", "ox ", "o  "] || O_WON
        ["xox", "xo ", " o "] || O_WON
        ["xxo", "x o", "  o"] || O_WON
        ["xxo", "xo ", "o  "] || O_WON
        ["oxx", "xo ", "  o"] || O_WON

        ["xx ", "   ", "oo "] || X_TURN // ход крестиков
        ["   ", "   ", "   "] || X_TURN // начало игры, ход крестиков
        ["xx ", "   ", "o  "] || O_TURN // ход ноликов
        ["xox", "xox", "oxo"] || DRAW
    }
}
