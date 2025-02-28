package pths.game.xo.service;

import pths.game.xo.model.GameState;
import pths.game.xo.model.Model;
import pths.game.xo.model.Position;
import pths.game.xo.model.Subscriber;
import pths.game.xo.net.model.NBoard;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Scheduler {
    private final ScheduledExecutorService executor;
    private final GameService service;

    private volatile Model model;

    public Scheduler(GameService service) {
        this.service = service;
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void start() {
        executor.scheduleAtFixedRate(this::run, 0, 100, MILLISECONDS);
    }

    public synchronized void run() {
        try {
            NBoard board = service.getBoard(model.getId());
            if (board.getUsers().stream().noneMatch(user -> user.getPosition() == Position.X)) return;
            if (board.getUsers().stream().noneMatch(user -> user.getPosition() == Position.O)) return;

            var marks = service.toMarks(board.getDetails());
            SwingUtilities.invokeLater(() -> {
                try {
                    model.setReady(true);
                    model.turn(marks);
                } catch (Exception e) {
                    // OK, skip it
                }
            });
        } catch (Exception e) {
            // OK. Repeat later.
            // No logging because it is just a POC (proof of concept)
        }
    }
}
