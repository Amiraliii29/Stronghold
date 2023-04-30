package Controller;

import Model.DataBase;
import Model.Square;
import Model.Units.Engineer;
import Model.Units.State;
import Model.Units.Unit;
import View.Enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.Objects;

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
        assert x != null;
        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
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
        if (selectedUnit.size() == 0) return GameMenuMessages.THERE_IS_NO_UNIT;
        DataBase.setSelectedUnit(selectedUnit);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages moveUnitController(String coordinate) {
        String x = Orders.findFlagOption("-x", coordinate);
        String y = Orders.findFlagOption("-y", coordinate);
        assert x != null;
        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
        int xCoordinate = Integer.parseInt(x);
        int yCoordinate = Integer.parseInt(y);
        if (DataBase.getSelectedMap().getLength() < xCoordinate || DataBase.getSelectedMap().getWidth() < yCoordinate)
            return GameMenuMessages.INVALID_COORDINATE;

        if (DataBase.getSelectedUnit().size() == 0) return GameMenuMessages.CHOSE_UNIT_FIRST;

        int speed = DataBase.getSelectedUnit().get(0).getSpeed();
//        int firstX = DataBase.getSelectedUnit().get(0).getXCoordinate();
//        int firstY = DataBase.getSelectedUnit().get(0).getYCoordinate();
//        if (Math.abs((xCoordinate - firstX)) > range || Math.abs((yCoordinate - firstY)) > range)
//            return GameMenuMessages.OUT_OF_RANGE;

        if (!DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).canPass())
            return GameMenuMessages.CANT_GO_THERE;

    }

    private static boolean dfs(int x, int y, int xFin, int yFin, int speed, boolean up, boolean stair) {
        if (speed >= 0 && x == xFin && y == yFin) return true;
        if (speed == 0) return false;



//        (dfs(x+1, y, xFin, yFin, speed-1)
//                || dfs(x-1, y, xFin, yFin, speed-1)
//                || dfs(x, y+1, xFin, yFin, speed-1)
//                || dfs(x, y-1, xFin, yFin, speed-1))
    }

    private static boolean moveUnit(int x, int y) {

    }

    public static GameMenuMessages setUnitModeController(String option) {
        String x = Orders.findFlagOption("-x", option);
        String y = Orders.findFlagOption("-y", option);
        String state = Orders.findFlagOption("-s", option);
        assert x != null;
        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
        int xCoordinate = Integer.parseInt(x);
        int yCoordinate = Integer.parseInt(y);
        if (DataBase.getSelectedMap().getLength() < xCoordinate
                || DataBase.getSelectedMap().getWidth() < yCoordinate)
            return GameMenuMessages.INVALID_COORDINATE;

        State newState;
        switch (Objects.requireNonNull(state)) {
            case "Standing" -> newState = State.Stan_Ground;
            case "Defensive" -> newState = State.Defensive;
            case "Offensive" -> newState = State.Aggressive;
            default -> {
                return GameMenuMessages.INVALID_STATE;
            }
        }

        Square square = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
        for (Unit unit : square.getUnits()) {
            if (unit.getOwner().equals(DataBase.getCurrentGovernment())) {
                unit.changeState(newState);
            }
        }
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages attackGroundController(String enemy) {
        return null;
    }

    public static GameMenuMessages attackAirController(String coordinate) {
        return null;
    }

    public static GameMenuMessages pourOilController(String direction) {
        if (DataBase.getSelectedUnit().size() == 0)
            return GameMenuMessages.CHOSE_UNIT_FIRST;
        if (!(DataBase.getSelectedUnit().get(0) instanceof Engineer))
            return GameMenuMessages.UNIT_ISNT_ENGINEER;

    }

    public static GameMenuMessages digTunnelController(String coordinate) {
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
