package Main;

import com.google.gson.Gson;

import java.util.HashMap;

public class Result {
    public ResultEnums resultEnums;
    public GameRequest gameRequest;
    public HashMap<String, String> arguments;


    {
        arguments = new HashMap<>();
    }

    public Result(ResultEnums resultEnums) {
        this.resultEnums = resultEnums;
        gameRequest = null;
    }

    public Result(GameRequest gameRequest) {
        this.gameRequest = gameRequest;
        resultEnums = null;
    }



    public void addArgument(String arg1, String arg2) {
        arguments.put(arg1, arg2);
    }


    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Result fromJson(String json) {
        return new Gson().fromJson(json, Result.class);
    }
}
