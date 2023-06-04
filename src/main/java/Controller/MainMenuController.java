package Controller;

import Model.DataBase;
import View.LoginMenu;
import javafx.scene.input.MouseEvent;

public class MainMenuController {

    public void startGame(MouseEvent mouseEvent) {
        //todo open game
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
