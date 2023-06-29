package Main;

import com.google.gson.Gson;

import java.util.HashMap;

public class Request {
    private static String userToken;
    private final String token;
    public GameRequest gameRequest;
    public NormalRequest normalRequest;
    public HashMap<String, String> argument;


    {
        token = userToken;
        argument = new HashMap<>();
    }


    public static void setUserToken(String userToken) {
        Request.userToken = userToken;
    }



    public Request(GameRequest gameRequest) {
        this.gameRequest = gameRequest;
        this.normalRequest = null;
    }

    public Request(NormalRequest normalRequest) {
        this.gameRequest = null;
        this.normalRequest = normalRequest;
    }



    public void addToArguments(String arg1, String arg2) {
        argument.put(arg1, arg2);
    }


    public boolean verify(String token) {
        return this.token.equals(token);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Request fromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }
}
