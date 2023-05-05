package Controller;

import java.security.NoSuchAlgorithmException;

import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.User;
import View.Enums.Commands.LoginMenuCommands;
import View.Enums.Messages.LoginMenuMessages;
import View.GameMenu;
import View.Input_Output;

public class LoginMenuController {

    public static void prepareMenuByType(LoginMenuCommands menuType) throws NoSuchAlgorithmException{

        if(menuType.equals(LoginMenuCommands.LOGOUT)){
            User.getCurrentUser().setStayLoggedIn(false);
            UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/java/jsonData/Users.json");
            User.setCurrentUser(null);
        }
    }

    public static LoginMenuMessages startGameController(String mapName) {
        Map.loadMap(mapName);
        Map selectedMap = DataBase.getSelectedMap();

        if(mapName == null)
            return LoginMenuMessages.NO_MAP_NAME;
        else if(DataBase.getSelectedMap() == null)
            return LoginMenuMessages.INVALID_MAP_NAME;
        else{
            int usersCount = selectedMap.getGovernmentsInMap().size();
            for (int i = 0 ; i < usersCount ; i++){
                Input_Output.outPut("please enter payer number" + i + "\'s name:");
                String userName = Input_Output.getInput();
                if(User.getUserByUserName(userName) == null)
                    return LoginMenuMessages.INVALID_USERNAME;
                selectedMap.getGovernmentsInMap().get(i).setOwner(User.getUserByUserName(userName));
            }
            DataBase.newGovernments();
            for (Government government : selectedMap.getGovernmentsInMap()) {
                DataBase.addGovernment(government);
            }
            return LoginMenuMessages.START_GAME_SUCCESS;
        }
    }
}
