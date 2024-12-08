package pths.game.xo;

import pths.game.xo.gui.Board;
import pths.game.xo.gui.Dialog;
import pths.game.xo.gui.Renderer;
import pths.game.xo.model.Model;
import pths.game.xo.model.ModelChecker;

public class Main {
    public static void main(String[] args) {
        var checker = new ModelChecker();
        var model = new Model(0, checker);

        Dialog.register(model);
        var renderer = new Renderer();
        var board = new Board(model, renderer);
        board.setVisible(true);
    }
}
