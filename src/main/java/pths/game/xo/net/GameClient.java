package pths.game.xo.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pths.game.xo.net.model.NBoard;
import pths.game.xo.model.Position;
import pths.game.xo.net.model.Turn;
import pths.game.xo.net.model.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class GameClient {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String name;
    private final String passwd;

    public GameClient(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public List<NBoard> getOpenBoards() throws Exception {
        HttpRequest request = http("http://localhost:8080/board?status=WAITING")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IllegalArgumentException("Cannot create board");

        String body = response.body();
        TypeReference<List<NBoard>> typeRef = new TypeReference<>() {};
        return mapper.readValue(body, typeRef);
    }

    public NBoard getBoard(int id) throws Exception {
        var body = "";
        return mapper.readValue(body, NBoard.class);
    }

    public void createBoard() throws Exception {
        HttpRequest request = http("http://localhost:8080/board")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IllegalArgumentException("Cannot create board");
    }


    public void attachUser(int boardId, Position position) throws Exception {
        var netUser = new User(name, position);
        var body = mapper.writeValueAsString(netUser);

        HttpRequest request = http("http://localhost:8080/board/" + boardId + "/user")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IllegalArgumentException("Cannot create board");
    }

    private HttpRequest.Builder http(String uri) throws URISyntaxException {
        var auth = Base64.getEncoder().encodeToString((name + ":" + passwd).getBytes());

        return HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + auth);
    }

    public void turn(int boardId, String details) throws Exception {
        var turn = new Turn(details);
        var body = mapper.writeValueAsString(turn);
    }

    public static void main(String[] args) throws Exception {
        var client = new GameClient("kit", "cat");
        client.createBoard();
        client.attachUser(1, Position.X);
        List<NBoard> openBoards = client.getOpenBoards();
        System.out.println(openBoards);
    }
}
