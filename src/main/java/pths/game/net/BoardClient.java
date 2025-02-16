package pths.game.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pths.game.net.model.Board;
import pths.game.net.model.Position;
import pths.game.net.model.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class BoardClient {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Board> getOpenBoards() throws Exception {
        HttpRequest request = http("http://localhost:8080/board?status=WAITING")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IllegalArgumentException("Cannot create board");

        String body = response.body();
        TypeReference<List<Board>> typeRef = new TypeReference<>() {};
        return mapper.readValue(body, typeRef);
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


    public void attachUser(int boardId, String name, Position position) throws Exception {
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
        var user = "kit";
        var passwd = "cat";
        var auth = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());

        return HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + auth);
    }

    public static void main(String[] args) throws Exception {
        var client = new BoardClient();
        client.createBoard();
        client.attachUser(1, "kit", Position.X);
        List<Board> openBoards = client.getOpenBoards();
        System.out.println(openBoards);
    }
}
