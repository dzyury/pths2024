package pths.game.xo.gui;

import pths.game.xo.model.Model;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    public Board(Model model, Renderer renderer) {
        setTitle("Крестики-Нолики");

        var pane = new JPanel();
        getContentPane().add(pane);

        var layout = new GridLayout(3, 3);
        pane.setLayout(layout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = Cell.create(i, j, model, renderer);
                pane.add(cell);
                model.addSubscriber(cell);
            }
        }

        setSize(200, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
