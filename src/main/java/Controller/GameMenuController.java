package Controller;

import Main.*;
import Model.*;
import Model.Units.*;
import View.Controller.BuildingInfo;
import View.Game;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.HashMap;

public class GameMenuController {
    public static Client client;
    private static Game game;
    private static Map map;
    private static Square[][] squares;
    private static ArrayList<Square> path;
    private static Government currentGovernment;





    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        GameMenuController.map = map;
        squares = map.getSquares();
    }

    public static Game getGame() {
        return game;
    }

    public static void setCurrentGovernment() {
        currentGovernment = DataBase.getMyGovernment();
    }

    public static void setGame(Game game) {
        GameMenuController.game = game;
    }







    public static ArrayList<ArrayList<UnitPrototype>> separateUnits() {
        HashMap<String, Integer> unitNameAndCount = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (!BuildingInfo.getTextFields().get(i).isVisible()) break;
            if (BuildingInfo.getTextFields().get(i).getText().matches("^\\d*$")) {
                if (BuildingInfo.getTextFields().get(i).getText().matches("^\\d+$"))
                    unitNameAndCount.put(BuildingInfo.imagesOrder.get(i), Integer.parseInt(BuildingInfo.getTextFields().get(i).getText()));
                else
                    unitNameAndCount.put(BuildingInfo.imagesOrder.get(i), -1);

                continue;
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Use Only 0-9");
            return null ;
        }

        ArrayList<ArrayList<Unit>> allUnits = new ArrayList<>();

        for (java.util.Map.Entry<String, Integer> set : unitNameAndCount.entrySet()) {
            ArrayList<Square> squaresChecked = new ArrayList<>();

            for (Unit unit : DataBase.getSelectedUnit()) {
                if (!unit.getName().equals(set.getKey()) || squaresChecked.contains(unit.getSquare())) continue;

                squaresChecked.add(unit.getSquare());
                ArrayList<Unit> selectedUnit = new ArrayList<>();

                for (Unit squareUnit : unit.getSquare().getUnits()) {
                    if (set.getValue() == 0) break;
                    if (!squareUnit.getName().equals(set.getKey())) continue;

                    selectedUnit.add(squareUnit);
                    set.setValue(set.getValue() - 1);
                }

                if (selectedUnit.size() != 0) allUnits.add(selectedUnit);
            }
        }

        return allUnits;
    }




    public static void constructBuilding(BuildingPrototype buildingPrototype) {
        Request request = new Request(GameRequest.CREATE_BUILDING);
        request.addToArguments("buildingPrototype", buildingPrototype.toJson());


        client.sendRequestToServer(request, true);

        Result result = Result.fromJson(client.getRecentResponse());

        switch (result.resultEnums) {
            case SUCCESS -> {
                BuildingPrototype building = BuildingPrototype.fromJson(result.arguments.get("buildingPrototype"));
                map.constructBuilding(building, building.x, building.y);
                game.drawMap();
            }
            case CANT_BUILD_HERE -> game.showErrorText("Can't Build Here!");
            case NOT_ENOUGH_MONEY -> game.showErrorText("Not Enough Money!");
            case NOT_ENOUGH_FREE_WORKER -> game.showErrorText("Not Enough Free Worker!");
            case NOT_ENOUGH_MATERIAL -> game.showErrorText("Not Enough Material!");
        }
    }




    public static void AttackBySelectedUnits(String valueOf, String valueOf1) {
    }
}
