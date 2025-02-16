package pths.game.xo.model;

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
    public void turn(Mark[][] model) {
        int xCountOld = 0;
        int oCountOld = 0;
        int xCountNew = 0;
        int oCountNew = 0;
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                if (marks[y][x] == Mark.X) xCountOld++;
                if (marks[y][x] == Mark.O) oCountOld++;
                if (model[y][x] == Mark.X) xCountNew++;
                if (model[y][x] == Mark.O) oCountNew++;
            }
        }

        GameState stateOld = getState();
        GameState state = checker.check(model);

        if (state == GameState.INVALID  ) {
            throw new java.lang.IllegalStateException("Невозможный хож");
        }else if (stateOld == GameState.DRAW || stateOld == GameState.X_WON || stateOld == GameState.O_WON|| stateOld == GameState.INVALID) {
            throw new java.lang.IllegalStateException("ИГРА ЗАКОНЧЕНА");
        } else if (xCountNew + oCountNew != 1 + xCountOld + oCountOld) {
            throw new java.lang.IllegalStateException("Внезапная ошибка");
        }
        for (int y = 0; y <= 2; y++) {
            System.arraycopy(model[y], 0, marks[y], 0, 3);
        }
    }

    /**
     *  Пытается сделать ход.<br/>
     *  Если ход сделать удаётся, модель обновляется.<br/>
     * @see Model#turn(Mark[][])
     */
    public void turn(int x, int y, Mark mark) {
        GameState oldState = getState();
        if (marks[y][x] != null) throw new IllegalArgumentException("Поле уже занято");



        Mark[][] newModel = new Mark[3][3];
        for (int i = 0; i <= 2; i++) {
            System.arraycopy(marks[i], 0, newModel[i], 0, 3);
        }
        newModel[y][x] = mark;
        GameState newState = checker.check(newModel);
        turn(newModel);
        notifySubscribers(oldState);
    }

    /**
     * В случае, если позиция корректна и игра не закончена, возвращает игрока, чей ход ожидается.
     * В противном случае возвращает {@code null}.
     */
    public Mark whoseTurn(int x, int y) {
        if(marks[y][x] == null && getState() == GameState.X_TURN) return Mark.X;
        if(marks[y][x] == null && getState() == GameState.O_TURN) return Mark.O;
        return null;
    }

    /**
     * Проверяет текущий статус игры и возвращает его.
     */
    public GameState getState() {
        return checker.check(marks);
    }
}
