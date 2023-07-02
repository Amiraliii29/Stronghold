package Controller;

import Main.*;
import Model.*;
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

        ArrayList<ArrayList<UnitPrototype>> allUnits = new ArrayList<>();

        for (java.util.Map.Entry<String, Integer> set : unitNameAndCount.entrySet()) {
            ArrayList<Square> squaresChecked = new ArrayList<>();

            for (UnitPrototype unit : DataBase.getSelectedUnit()) {
                if (!unit.getName().equals(set.getKey()) || squaresChecked.contains(unit.getSquare())) continue;

                squaresChecked.add(unit.getSquare());
                ArrayList<UnitPrototype> selectedUnit = new ArrayList<>();

                for (UnitPrototype squareUnit : unit.getSquare().getUnits()) {
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

    public static ArrayList<Square> moveUnit(UnitPrototype unit, int squareI, int squareJ) {
        return null;//TODO
    }




    public static void constructBuilding(BuildingPrototype buildingPrototype) {
        Request request = new Request(GameRequest.CREATE_BUILDING);
        request.addToArguments("buildingPrototype", buildingPrototype.toJson());


        client.sendRequestToServer(request, true);

        Request result = Request.fromJson(client.getRecentResponse());

        switch (result.resultEnums) {
            case SUCCESS -> {
                BuildingPrototype building = BuildingPrototype.fromJson(result.argument.get("buildingPrototype"));
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


    public static void createUnitController(String name) {
        Request request = new Request(GameRequest.CREATE_UNIT_IN_BUILDING);
        request.argument.put("name", name);

        client.sendRequestToServer(request, false);
    }

    public static void createUnit(String name, int x, int y, int cnt) {
        Request request = new Request(GameRequest.CREATE_UNIT);
        request.argument.put("name", name);
        request.argument.put("x", String.valueOf(x));
        request.argument.put("y", String.valueOf(y));
        request.argument.put("count", String.valueOf(cnt));

        client.sendRequestToServer(request, false);
    }

    public static void setUnitModeController(String mode) {
        Request request = new Request(GameRequest.CHANGE_UNIT_STATE);
        request.argument.put("changeUnitState", mode);

        client.sendRequestToServer(request, false);
    }

    public static void disbandUnit() {
        Request request = new Request(GameRequest.DISBAND);

        client.sendRequestToServer(request, false);
    }

    public static void repair() {
        Request request = new Request(GameRequest.REPAIR);

        client.sendRequestToServer(request, false);
    }

    public static void modifyGates(boolean b) {
        Request request = new Request(GameRequest.MODIFY_GATE);
        request.argument.put("modify", String.valueOf(b));

        client.sendRequestToServer(request, false);
    }

    public static void changeProduct(String product) {
        Request request = new Request(GameRequest.CHANGE_PRODUCT);
        request.argument.put("changeProduct", product);

        client.sendRequestToServer(request, false);
    }
}
