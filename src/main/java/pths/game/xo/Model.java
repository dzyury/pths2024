package pths.game.xo;

import java.util.ArrayList;
import java.util.List;

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
    public void turn(Mark[][] marks) {
    }

    /**
     *  Пытается сделать ход.<br/>
     *  Если ход сделать удаётся, модель обновляется.<br/>
     * @see Model#turn(Mark[][])
     */
    public void turn(int x, int y, Mark mark) {
        if (marks[x][y] != null) throw new IllegalArgumentException("Поле уже занято");

        // Тут надо добавить код
        // у меня было немного кода, а потом вызов другого метода turn

        // Это закомментировано, чтобы прошла компиляция, надо раскомментировать
        // notifySubscribers(oldState);
    }

    /**
     * В случае, если позиция корректна и игра не закончена, возвращает игрока, чей ход ожидается.
     * В противном случае возвращает {@code null}.
     */
    public Mark whoseTurn(int x, int y) {
        return null;
    }

    /**
     * Проверяет текущий статус игры и возвращает его.
     */
    public GameState getState() {
        return checker.check(marks);
    }
}

