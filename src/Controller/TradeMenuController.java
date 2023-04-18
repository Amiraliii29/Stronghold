package Controller;

import Model.User;

import java.util.HashMap;

public class TradeMenuController {
    private static HashMap<User,String> message;

    static {
        message = new HashMap<>();
    }


    public static HashMap<User, String> getMessage() {
        return message;
    }
}
