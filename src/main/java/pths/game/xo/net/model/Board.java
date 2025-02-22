package pths.game.xo.net.model;

import java.util.List;

public class Board {
    private int id;
    private String status;
    private String details;
    private List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                ", users=" + users +
                '}';
    }
}
