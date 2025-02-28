package pths.game.xo.gui;

import pths.game.xo.model.GameState;
import pths.game.xo.model.Model;
import pths.game.xo.model.Position;
import pths.game.xo.model.Subscriber;
import pths.game.xo.service.GameService;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame implements Subscriber {
    private final String name;
    private final Model model;

    public Board(String name, Model model, Renderer renderer, GameService gameService) {
        this.name = name;
        this.model = model;
        changeTitle();

        var pane = new JPanel();
        getContentPane().add(pane);

        var layout = new GridLayout(3, 3);
        pane.setLayout(layout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = Cell.create(i, j, model, renderer, gameService);
                pane.add(cell);
            }
        }

        setSize(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void changeTitle() {
        var title = name + ": " + (model.getGamer() == Position.X ? "Крестики" : "Нолики");
        setTitle(title);
    }

    @Override
    public void stateChanged(GameState oldState, GameState newState) {
        if (newState.isEnd()) {
            var title = switch(newState) {
                case DRAW -> "Ничья";
                case X_WON -> "Крестики победили";
                case O_WON -> "Нолики победили";
                default -> "--------";
            };
            setTitle(name + ": " + title);
        } else if (newState == GameState.X_TURN && oldState.isEnd()) {
            changeTitle();
        }
    }
}
