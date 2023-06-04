package Controller;

import Model.DataBase;
import Model.Map;
import View.GameMenu;
import View.LoginMenu;
import javafx.scene.input.MouseEvent;

public class MainMenuController {

    public void startGame(MouseEvent mouseEvent) throws Exception {
        // todo delete line below   amirali
        DataBase.setSelectedMap(new Map("map1"  , 100 , 100));

        new GameMenu().start(DataBase.getMainStage());
    }

    public void openMapMenu(MouseEvent mouseEvent) {
        //todo open map menu

    }

    public void openProfileMenu(MouseEvent mouseEvent) {
        //todo open profile menu
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(DataBase.getMainStage());
    }
}
