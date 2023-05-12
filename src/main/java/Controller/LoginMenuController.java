package Controller;

import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.User;
import View.Enums.Messages.LoginMenuMessages;
import View.Input_Output;

public class LoginMenuController {
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
                Input_Output.outPut("please enter payer number" + i + "'s name:");
                String userName = Input_Output.getInput();
                if(User.getUserByUserName(userName) == null) {
                    Input_Output.outPut("Invalid username");
                    i--;
                } else
                    selectedMap.getGovernmentsInMap().get(i).setOwner(User.getUserByUserName(userName));
            }
            DataBase.newGovernments();
            for (Government government : selectedMap.getGovernmentsInMap()) {
                DataBase.addGovernment(government);
            }
            GameMenuController.setCurrentGovernment(selectedMap.getGovernmentsInMap().get(0));
            DataBase.setCurrentGovernment(selectedMap.getGovernmentsInMap().get(0));
            return LoginMenuMessages.START_GAME_SUCCESS;
        }
    }
}
