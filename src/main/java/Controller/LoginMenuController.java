package Controller;

import java.security.NoSuchAlgorithmException;

import Model.User;
import View.Enums.Commands.LoginMenuCommands;

public class LoginMenuController {

    public static void prepareMenuByType(LoginMenuCommands menuType) throws NoSuchAlgorithmException{

        if(menuType.equals(LoginMenuCommands.LOGOUT)){
            User.getCurrentUser().setStayLoggedIn(false);
            UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/java/jsonData/Users.json");
            User.setCurrentUser(null);
        }
    }
}
