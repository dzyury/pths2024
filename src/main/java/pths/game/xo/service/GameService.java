package pths.game.xo.service;

import pths.game.xo.model.Mark;
import pths.game.xo.model.Model;
import pths.game.xo.model.ModelChecker;
import pths.game.xo.net.GameClient;
import pths.game.xo.net.UserClient;
import pths.game.xo.net.model.NBoard;
import pths.game.xo.net.model.UserInfo;

import java.util.List;

import static pths.game.xo.model.Position.O;
import static pths.game.xo.model.Position.X;

public class GameService {
    private final String name;
    private final String passwd;
    private final GameClient gameClient;
    private final UserClient userClient;
    private final ModelChecker checker;

    private volatile Model model;

    public GameService(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;

        this.gameClient = new GameClient(name, passwd);
        this.userClient = new UserClient();
        this.checker = new ModelChecker();
    }

    public Model createNewGame() {
        while (true) try {
            List<NBoard> boards = gameClient.getOpenBoards();
            for (NBoard board : boards) {
                var id = board.getId();
                var position = hasOpenXPosition(board) ? X : O;
                try {
                    gameClient.attachUser(id, position);
                    return new Model(id, position, checker);
                } catch (Exception e) {
                    // OK, find next board
                }
            }
            gameClient.createBoard();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так... Не могу создать новое поле");
        }
    }

    public Model getNewGame() {
        return model = createNewGame();
    }

    public void restartModel() {
        var newModel = createNewGame();
        model.init(newModel.getId(), newModel.getGamer());
        model.setReady(false);
    }

    private static boolean hasOpenXPosition(NBoard board) {
        return board.getUsers().stream().noneMatch(it -> it.getPosition() == X);
    }

    public void registerUser() {
        try {
            userClient.register(new UserInfo(name, passwd));
        } catch (Exception e) {
            System.out.println("Не получается зарегистрироваться");
            // Надеемся, что это уже мы зарегистрировались
        }
    }

    public void turn(Mark[][] marks) {
        try {
            gameClient.turn(model.getId(), fromMarks(marks));
        } catch (Exception e) {
            System.out.println("Не могу сделать ход");
        }
    }


    private String fromMarks(Mark[][] marks) {
        var details = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                details.append(fromMark(marks[i][j]));
            }
        }
        return details.toString();
    }

    public Mark[][] toMarks(String details) {
        var marks = new Mark[3][3];
        for (int i = 0; i < 9; i++) {
            var ch = details.charAt(i);
            marks[i / 3][i % 3] = toMark(ch);
        }
        return marks;
    }

    private char fromMark(Mark mark) {
        return mark == null ? '_' : mark.ch;
    }

    private Mark toMark(char ch) {
        if (ch == '_') return null;
        if (ch == 'x') return Mark.X;
        if (ch == 'o') return Mark.O;
        throw new IllegalArgumentException("Invalid position: " + ch);
    }

    public NBoard getBoard(int boardId) throws Exception {
        return gameClient.getBoard(boardId);
    }
}
