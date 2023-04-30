package Controller;

import Model.DataBase;
import Model.Government;
import Model.Square;
import Model.Units.Unit;
import View.Enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMenuController {
    public static GameMenuMessages nextTurnController() {
        return null;
    }

    public static GameMenuMessages userLogout() {
        return null;
    }

    public static GameMenuMessages dropBuildingController(String x, String y, String type) {
        return null;
    }

    public static GameMenuMessages selectBuildingController(String x, String y) {
        return null;
    }

    public static GameMenuMessages createUnitController(String type, String count) {
        return null;
    }

    public static GameMenuMessages repairController() {
        return null;
    }

    public static GameMenuMessages selectUnitController(String option) {
        String x = Orders.findFlagOption("-x", option);
        String y = Orders.findFlagOption("-y", option);
        String type = Orders.findFlagOption("-type", option);
        if (!x.matches("^\\d+$") || !y.matches("^\\d+$"))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
        if (!Unit.getAllUnits().contains(type))
            return GameMenuMessages.INVALID_TROOP_TYPE;
        int xCoordinate = Integer.parseInt(x);
        int yCoordinate = Integer.parseInt(y);
        if (DataBase.getSelectedMap().getLength() < xCoordinate
                || DataBase.getSelectedMap().getWidth() < yCoordinate)
            return GameMenuMessages.INVALID_COORDINATE;
        Square square = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
        ArrayList<Unit> selectedUnit = new ArrayList<>();
        for (Unit unit : square.getUnits()) {
            if (unit.getName().equals(type)) {
                selectedUnit.add(unit);
            }
        }
        DataBase.setSelectedUnit(selectedUnit);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages moveUnitController(String x, String y) {
        return null;
    }

    public static GameMenuMessages setUnitModeController(String x, String y, String mode) {
        return null;
    }

    public static GameMenuMessages attackController(String enemy) {
        return null;
    }

    public static GameMenuMessages attackController(String x, String y) {
        return null;
    }

    public static GameMenuMessages pourOilController(String direction) {
        return null;
    }

    public static GameMenuMessages digTunnelController(String x, String y) {
        return null;
    }

    public static GameMenuMessages buildEquipmentController(String EquipmentName) {
        return null;
    }

    public static GameMenuMessages disbandUnitController() {
        return null;
    }

    public static GameMenuMessages dropBuildingController(String x, String y, String type, String count) {
        return null;
    }

    public static GameMenuMessages showMapController(String x, String y) {
        return null;
    }
}
