package pths.game.xo.gui;

import pths.game.xo.model.GameState;
import pths.game.xo.model.Mark;
import pths.game.xo.model.Model;
import pths.game.xo.model.Subscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static pths.game.xo.model.GameState.X_TURN;

public class Cell extends JPanel implements Subscriber {
    private static final Color BACKGROUND = new Color(160, 160, 160);

    private final Renderer renderer;

    private Mark mark;
    private boolean isHint;

    public Cell(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void stateChanged(GameState oldState, GameState newState) {
        if (oldState.isEnd() && newState == X_TURN) {
            mark = null;
            isHint = false;
            repaint();
        }
    }

    public static Cell create(int x, int y, Model model, Renderer renderer) {
        var cell = new Cell(renderer);
        cell.setBackground(BACKGROUND);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cell.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Mark mark = model.whoseTurn(x, y);
                if (mark == null) return;

                model.turn(x, y, mark);
                cell.mark = mark;
                cell.isHint = false;
                cell.repaint();
            }

            public void mouseEntered(MouseEvent e) {
                Mark mark = model.whoseTurn(x, y);
                if (mark == null) return;

                cell.mark = mark;
                cell.isHint = true;
                cell.repaint();
            }

            public void mouseExited(MouseEvent e) {
                if (cell.isHint) {
                    cell.mark = null;
                    cell.isHint = false;
                    cell.repaint();
                }
            }
        });

        return cell;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderer.show(g, mark, getWidth(), getHeight(), isHint);
    }
}
