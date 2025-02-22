package pths.game.xo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pths.game.xo.model.Mark.O;
import static pths.game.xo.model.Mark.X;

public class Model {
    public final int id;
    private final ModelChecker checker;
    private final List<Subscriber> subscribers = new ArrayList<>();

    private Mark[][] marks;

    public Model(int id, ModelChecker checker) {
        this.id = id;
        this.checker = checker;

        marks = new Mark[3][3];
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void init() {
        var oldState = checker.check(marks);
        marks = new Mark[3][3];
        notifySubscribers(oldState);
    }

    private void notifySubscribers(GameState oldState) {
        var newState = checker.check(marks);
        if (oldState == newState) return;

        for (Subscriber subscriber : subscribers) {
            subscriber.stateChanged(oldState, newState);
        }
    }

    /**
     *  Пытается сделать ход.<br/>
     *  Если ход сделать удаётся, модель обновляется.<br/>
     *  Если ход некорректный, бросает эксепшн с соответствующим сообщением об ошибке:<ul>
     *  <li>Чёрные выиграли
     *  <li>Белые выиграли
     *  <li>Ничья
     *  <li>Неправильный ход
     *  <li>Неожиданная ошибка
     *  </ul>
     */
    public void turn(Mark[][] model) {
        var state = checker.check(marks);
        switch (state) {
            case X_WON -> throw new IllegalStateException("Чёрные выиграли");
            case X_TURN -> turn(X, model);
            case O_WON -> throw new IllegalStateException("Белые выиграли");
            case O_TURN -> turn(O, model);
            case DRAW -> throw new IllegalStateException("Ничья");
            case INVALID -> throw new IllegalStateException("Неожиданная ошибка");
        }
    }
    
    private void turn(Mark mark, Mark[][] model) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (marks[i][j] == model[i][j]) continue;
                if (marks[i][j] == null && model[i][j] == mark) count++; else count = 9;
            }
        }
        if (count != 1) throw new IllegalStateException("Неправильный ход");
        marks = model;
    }

    /**
     *  Пытается сделать ход.<br/>
     *  Если ход сделать удаётся, модель обновляется.<br/>
     * @see Model#turn(Mark[][])
     */
    public void turn(int x, int y, Mark mark) {
        if (marks[x][y] != null) throw new IllegalArgumentException("Поле уже занято");

        var oldState = checker.check(marks);
        var newMarks = Arrays.stream(marks).map(Mark[]::clone).toArray(Mark[][]::new);
        newMarks[x][y] = mark;
        turn(newMarks);

        notifySubscribers(oldState);
    }


    public Mark whoseTurn(int x, int y) {
        var mark = switch (checker.check(marks)) {
            case GameState.O_TURN -> Mark.O;
            case GameState.X_TURN -> Mark.X;
            default -> null;
        };
        return marks[x][y] == null ? mark : null;
    }

    /**
     * Проверяет текущий статус игры и возвращает его.
     */
    public GameState getState() {
        return checker.check(marks);
    }
}
