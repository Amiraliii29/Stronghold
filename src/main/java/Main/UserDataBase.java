package Main;

import Controller.GameMenuController;
import Model.Buildings.Building;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Units.Unit;

import java.io.IOException;
import java.util.ArrayList;

public class UserDataBase {
    private final Client client;
    private final Government government;
    private final DataBase dataBase;
    private final GameMenuController gameMenuController;
    private Building selectedBuilding;
    private ArrayList<Unit> selectedUnit;


    public UserDataBase (Client client, Government government, DataBase dataBase) {
        this.client = client;
        this.government = government;
        this.dataBase = dataBase;
        selectedBuilding = null;
        selectedUnit = new ArrayList<>();
        gameMenuController = new GameMenuController(this);
    }


    public void sendRequest(Request request) {
        try {
            client.getDataOutputStream().writeUTF(request.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Client getClient() {
        return client;
    }

    public Government getGovernment() {
        return government;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void setSelectedBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public void setSelectedUnit(ArrayList<Unit> selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public ArrayList<Unit> getSelectedUnit() {
        return selectedUnit;
    }
}
