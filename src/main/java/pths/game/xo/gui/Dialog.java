package pths.game.xo.gui;

import pths.game.xo.model.GameState;
import pths.game.xo.model.Model;
import pths.game.xo.model.Position;
import pths.game.xo.model.Subscriber;
import pths.game.xo.service.GameService;

import javax.swing.*;

public class Dialog implements Subscriber {
    private final Model model;
    private final GameService gameService;

    public Dialog(Model model, GameService gameService) {
        this.model = model;
        this.gameService = gameService;
    }

    public static void createAndSubscribe(Model model, GameService gameService) {
        var dialog = new Dialog(model, gameService);
        model.addSubscriber(dialog);
    }

    @Override
    public void stateChanged(GameState oldState, GameState newState) {
        if (newState.isEnd()) end(model);
    }

    private void end(Model model) {
        var title = switch(model.getState()) {
            case X_WON -> "Крестики победили";
            case O_WON -> "Нолики выиграли";
            default -> "Ничья";
        };
        var name = model.getGamer() == Position.X ? "X" : "O";
        String[] options = {"Yes, please", "No, thanks"};
        SwingUtilities.invokeLater(() -> {
            var answer = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to play again?",
                    name + ": Game over: " + title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (answer != 0) System.exit(0);
            gameService.restartModel();
        });
    }
}
