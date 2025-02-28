package pths.game.xo.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import pths.game.xo.net.model.UserInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserClient {
    private final ObjectMapper mapper = new ObjectMapper();

    public void register(UserInfo user) throws Exception {
    }

    public static void main(String[] args) throws Exception {
        var client = new UserClient();
        client.register(new UserInfo("kit", "cat"));
    }
}
