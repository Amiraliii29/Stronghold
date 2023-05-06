package View;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import Controller.LoginMenuController;
import Controller.Orders;
import Controller.UserInfoOperator;
import Model.User;
import View.Enums.Commands.LoginMenuCommands;
import View.Enums.Messages.LoginMenuMessages;

public class LoginMenu {
    public static void run() throws NoSuchAlgorithmException {
        Matcher matcher;
        Input_Output.outPut("MAIN MENU:");
        String input;
        while (true) {
            input = Input_Output.getInput();

            if (LoginMenuCommands.getMatcher(input, LoginMenuCommands.EXIT) != null){
                UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/resources/jsonData/Users.json");
                User.setCurrentUser(null);
                break;
            }
            else if (LoginMenuCommands.getMatcher(input, LoginMenuCommands.LOGOUT) != null) {
                User.getCurrentUser().setStayLoggedIn(false);
                UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/resources/jsonData/Users.json");
                User.setCurrentUser(null);
                break;
            }
            else if (LoginMenuCommands.getMatcher(input, LoginMenuCommands.ENTER_MAP_MENU) != null)
                CustomizeMap.run();
            else if (LoginMenuCommands.getMatcher(input, LoginMenuCommands.ENTER_PROFILE_MENU) != null)
                ProfileMenu.run();
            else if ((matcher = LoginMenuCommands.getMatcher(input, LoginMenuCommands.START_GAME)) != null)
                startGame(matcher);
            else Input_Output.outPut("error: invalid command!");
        }
    }

    private static void startGame(Matcher matcher) {
        String options = matcher.group("options");
        String mapName = Orders.findFlagOption("-m", options);

        LoginMenuMessages message = LoginMenuController.startGameController(mapName);

        switch (message) {
            case INVALID_MAP_NAME:
                Input_Output.outPut("start game error: invalid map name");
                break;
            case NO_MAP_NAME:
                Input_Output.outPut("start game error: please enter map name");
                break;
            case NO_USERS_COUNT:
                Input_Output.outPut("start game error: please enter users count");
                break;
            case START_GAME_SUCCESS:
                System.out.println("game started successfully");
                GameMenu.run();
                break;
        }
    }
}
