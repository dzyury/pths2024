package pths.game.xo.gui;

import pths.game.xo.model.GameState;
import pths.game.xo.model.Model;
import pths.game.xo.model.Subscriber;

import javax.swing.*;

public class Dialog implements Subscriber {
    private final Model model;

    public Dialog(Model model) {
        this.model = model;
    }

    public static void register(Model model) {
        var dialog = new Dialog(model);
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
        String[] options = {"Yes, please", "No, thanks"};
        SwingUtilities.invokeLater(() -> {
            var answer = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to play again?",
                    "Game over: " + title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (answer != 0) System.exit(0);
            model.init();
        });
    }
}
