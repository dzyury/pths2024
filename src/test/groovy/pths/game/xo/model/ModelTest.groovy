package pths.game.xo.model

import spock.lang.Specification

import static pths.game.xo.model.GameState.DRAW
import static pths.game.xo.model.GameState.INVALID
import static pths.game.xo.model.GameState.O_TURN
import static pths.game.xo.model.GameState.O_WON
import static pths.game.xo.model.GameState.X_TURN
import static pths.game.xo.model.GameState.X_WON

class ModelTest extends Specification {
    Controller controller = new Controller()
    ModelChecker checker = new ModelChecker()

    def "controller"() {
        when:
        controller.getModel(idx)

        then:
        Exception e = thrown()
        e.message == "Модель не найдена"

        where:
        idx << [-1, 0, 1, 2]
    }

    def "controller with two models"() {
        given:
        def model

        when:
        model = controller.createNewModel()

        then:
        model.id == 1
        controller.getModel(model.id) === model

        when:
        model = controller.createNewModel()

        then:
        model.id == 2
        controller.getModel(model.id) === model
    }

    void "game start"() {
        given:
        def model = controller.createNewModel(checker)

        expect:
        model.id == 1
        controller.getModel(model.id) === model
    }

    void "gamex"(GameState state, String[] lines) {
        given:
        def model = controller.createNewModel(checker)

        when:
        for (line in lines) {
            Mark[][] newModel = line.split("\\|").collect { it.toCharArray().collect { Mark.of(it) } }
            model.turn(newModel)
        }

        then:
        model.id == 1
        controller.getModel(model.id) === model
        model.state == state

        where:
        state  | lines
        X_TURN | []
        O_TURN | ["x  |   |   "]
        X_TURN | ["x  |   |   ", "xo |   |   "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   "]
        X_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   "]
        X_WON  | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xo |xo |x  "]
    }

    void "gameo"(GameState state, String[] lines) {
        given:
        def model = controller.createNewModel(checker)

        when:
        for (line in lines) {
            Mark[][] newModel = line.split("\\|").collect { it.toCharArray().collect { Mark.of(it) } }
            model.turn(newModel)
        }

        then:
        model.id == 1
        controller.getModel(model.id) === model
        model.state == state

        where:
        state  | lines
        X_TURN | []
        O_TURN | ["x  |   |   "]
        X_TURN | ["x  |   |   ", "xo |   |   "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   "]
        X_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   "]
        O_WON  | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   ", "xox|xo | o "]
    }

    void "gamed"(GameState state, String[] lines) {
        given:
        def model = controller.createNewModel(checker)

        when:
        for (line in lines) {
            Mark[][] newModel = line.split("\\|").collect { it.toCharArray().collect { Mark.of(it) } }
            model.turn(newModel)
        }

        then:
        model.id == 1
        controller.getModel(model.id) === model
        model.state == state

        where:
        state  | lines
        X_TURN | []
        O_TURN | ["x  |   |   "]
        X_TURN | ["x  |   |   ", "xo |   |   "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   "]
        X_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   "]
        X_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   ", "xox|xo |o  "]
        O_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   ", "xox|xo |o  ", "xox|xox|o  "]
        X_TURN | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   ", "xox|xo |o  ", "xox|xox|o  ", "xox|xox|o o"]
        DRAW   | ["x  |   |   ", "xo |   |   ", "xo |x  |   ", "xo |xo |   ", "xox|xo |   ", "xox|xo |o  ", "xox|xox|o  ", "xox|xox|o o", "xox|xox|oxo"]
    }

    void "gamei"(GameState state, String[] lines) {
        given:
        def model = controller.createNewModel(checker)

        when:
        for (line in lines) {
            Mark[][] newModel = line.split("\\|").collect { it.toCharArray().collect { Mark.of(it) } }
            model.turn(newModel)
        }

        then:
        model.id == 1
        model.state == X_TURN
        thrown(IllegalStateException)

        where:
        state   | lines
        INVALID | ["   |   |   "]
        INVALID | ["o  |   |   "]
        INVALID | ["x  |   |  o"]
        INVALID | ["x  |x  |   "]
    }
}
