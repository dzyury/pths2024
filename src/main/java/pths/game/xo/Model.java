package pths.game.xo;

public class Model {
    private final int id;
    private final ModelChecker checker;
    private Mark[][] marks;

    public Model(int id) {
        this.id = id;
        marks = new Mark[3][3];
        checker = new ModelChecker();
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
     * Проверяет текущий статус игры и возвращает его.
     */
    public GameState getState() {
        return checker.check(marks);
    }
}
