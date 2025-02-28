package pths.game.xo;

import pths.game.xo.gui.Board;
import pths.game.xo.gui.Dialog;
import pths.game.xo.gui.Renderer;
import pths.game.xo.service.GameService;
import pths.game.xo.service.Scheduler;

public class Main {
    public static void main(String[] args) throws Exception {
        var name = args[0];
        var passwd = args[1];

        var gameService = new GameService(name, passwd);
        gameService.registerUser();
        var model = gameService.getNewGame();

        var scheduler = new Scheduler(gameService);
        scheduler.setModel(model);
        scheduler.start();

        Dialog.createAndSubscribe(model, gameService);
        var renderer = new Renderer();
        var board = new Board(name, model, renderer, gameService);
        model.addSubscriber(board);
        board.setVisible(true);
    }
}
