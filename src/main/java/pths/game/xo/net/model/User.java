package pths.game.xo.net.model;

import pths.game.xo.model.Position;

public class User {
    private String name;
    private Position position;

    public User() {
    }

    public User(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}
