package Main;

import com.google.gson.Gson;

import java.util.HashMap;

public class Request {
    private static String userToken;
    private final String token;
    public GameRequest gameRequest;
    public NormalRequest normalRequest;
    public ResultEnums resultEnums;
    public HashMap<String, String> argument;


    {
        token = userToken;
        argument = new HashMap<>();
    }


    public static void setUserToken(String userToken) {
        Request.userToken = userToken;
    }



    public Request(GameRequest gameRequest, NormalRequest normalRequest) {
        this.gameRequest = gameRequest;
        this.normalRequest = normalRequest;
        this.resultEnums = null;
    }

    public Request(ResultEnums resultEnums) {
        this.resultEnums = resultEnums;
        this.gameRequest = null;
        this.normalRequest = null;
    }



    public boolean verify(String token) {
        if(this.token==null) return false;
        //TODO: FIX AND CHANGE
        return this.token.equals(token);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Request fromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }
}
