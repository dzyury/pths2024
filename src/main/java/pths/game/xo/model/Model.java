package pths.game.xo.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static pths.game.xo.model.Mark.O;
import static pths.game.xo.model.Mark.X;

public class Model {
    private final ModelChecker checker;
    private final Set<Subscriber> subscribers = new LinkedHashSet<>();

    private volatile int id;
    private volatile Position gamer;
    private volatile boolean ready;
    private Mark[][] marks;

    public Model(int id, Position gamer, ModelChecker checker) {
        this.id = id;
        this.gamer = gamer;
        this.checker = checker;

        marks = new Mark[3][3];
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void init(int id, Position gamer) {
        this.id = id;
        this.gamer = gamer;

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
        var oldState = checker.check(marks);
        switch (oldState) {
            case X_WON -> throw new IllegalStateException("Чёрные выиграли");
            case X_TURN -> turn(X, model);
            case O_WON -> throw new IllegalStateException("Белые выиграли");
            case O_TURN -> turn(O, model);
            case DRAW -> throw new IllegalStateException("Ничья");
            case INVALID -> throw new IllegalStateException("Неожиданная ошибка");
        }
        notifySubscribers(oldState);
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
        if (!ready) return null;

        var mark = switch (checker.check(marks)) {
            case GameState.O_TURN -> Mark.O;
            case GameState.X_TURN -> Mark.X;
            default -> null;
        };
        if (marks[x][y] != null) return null;
        if (mark == X && gamer != Position.X) return null;
        if (mark == O && gamer != Position.O) return null;
        return mark;
    }

    /**
     * Проверяет текущий статус игры и возвращает его.
     */
    public GameState getState() {
        return checker.check(marks);
    }

    public Mark[][] getMarks() {
        var marks = new Mark[3][];
        for (int i = 0; i < 3; i++) {
            marks[i] = Arrays.copyOf(this.marks[i], 3);
        }
        return marks;
    }

    public int getId() {
        return id;
    }

    public Position getGamer() {
        return gamer;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
