package Controller;

import Model.Buildings.Defence;
import Model.DataBase;
import Model.Square;
import Model.Units.*;
import View.Enums.Messages.GameMenuMessages;
import Model.Map;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    private static ArrayList<ArrayList<Square>> allWays;
    private static ArrayList<Square> squares;
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

        if (!DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).canPass())
            return GameMenuMessages.CANT_GO_THERE;

    }

    private static boolean findWay(int x, int y, int xFin, int yFin, int speed, boolean up) {
        if (speed >= 0 && x == xFin && y == yFin) return true;
        if (speed == 0) return false;

        Square[][] squares = DataBase.getSelectedMap().getSquares();
        boolean stair = squares[x][y].getBuilding().getName().equals("Stair");
        if (stair) up = !up;
        if (!stair) {
            if (up) {
                if (squares[x+1][y].getBuilding() instanceof Defence &&
                        !squares[x+1][y].getBuilding().getName().equals("DrawBridge") &&
                        findWay(x+1, y, xFin, yFin, speed-1, up))
                    return true;
                else if (squares[x-1][y].getBuilding() instanceof Defence &&
                        !squares[x-1][y].getBuilding().getName().equals("DrawBridge") &&
                        findWay(x-1, y, xFin, yFin, speed-1, up))
                    return true;
                else if (squares[x][y+1].getBuilding() instanceof Defence &&
                        !squares[x][y+1].getBuilding().getName().equals("DrawBridge") &&
                        findWay(x, y+1, xFin, yFin, speed-1, up))
                    return true;
                else return squares[x][y - 1].getBuilding() instanceof Defence &&
                            !squares[x][y - 1].getBuilding().getName().equals("DrawBridge") &&
                            findWay(x, y - 1, xFin, yFin, speed - 1, up);
            } else {

            }
        }

//        (dfs(x+1, y, xFin, yFin, speed-1)
//                || dfs(x-1, y, xFin, yFin, speed-1)
//                || dfs(x, y+1, xFin, yFin, speed-1)
//                || dfs(x, y-1, xFin, yFin, speed-1))
    }

    private static boolean moveUnit(int x, int y) {
        ArrayList<Unit> unit = DataBase.getSelectedUnit();

        int speed = unit.get(0).getSpeed();
        int firstX = unit.get(0).getXCoordinate();
        int firstY = unit.get(0).getYCoordinate();
        boolean up;
        // define up
        squares = new ArrayList<>();
        allWays = new ArrayList<>();
        move(unit.get(0), DataBase.getSelectedMap(), firstX, firstY, x, y, speed, up);

        //find shortest way
        //add move left to units
        //handle Trap if in pass //TODO
    }

    private static boolean move(Unit unit, Map map, int x, int y, int xFin, int yFin, int speed, boolean up) {
        if (speed >= 0 && x == xFin && y == yFin) {
            allWays.add(squares);
            return true;
        }
        if (speed == 0) return false;

        //conditions
        if (!map.getSquareFromMap(x, y).canPass()) return false;
        if (unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher")) {
            if (!map.getSquareFromMap(x, y).getBuilding().getCanPass()
                    || map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair"))
                return false;
        } else if (!unit.getName().equals("Assassin")){
            if (!(map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                    || map.getSquareFromMap(x, y).getBuilding().getCanPass()))
                return false;

            LadderMan ladderMan = LadderMan.createLadderMan(DataBase.getCurrentGovernment(), -1, -1);
            if (map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair")
                    || map.getSquareFromMap(x, y).getUnits().contains(ladderMan)) up = !up;

            if (up && !(map.getSquareFromMap(x, y).getBuilding() instanceof Defence)) return false;

            if (!up && (map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                    && !map.getSquareFromMap(x, y).getBuilding().getCanPass()))
                return false;
        }

        squares.add(map.getSquareFromMap(x+1, y));
        move(unit, map, x+1, y, xFin, yFin, speed-1, up);
        squares.remove(map.getSquareFromMap(x+1, y));

        squares.add(map.getSquareFromMap(x-1, y));
        move(unit, map, x-1, y, xFin, yFin, speed-1, up);
        squares.remove(map.getSquareFromMap(x-1, y));

        squares.add(map.getSquareFromMap(x, y+1));
        move(unit, map, x, y+1, xFin, yFin, speed-1, up);
        squares.remove(map.getSquareFromMap(x, y+1));

        squares.add(map.getSquareFromMap(x, y-1));
        move(unit, map, x, y-1, xFin, yFin, speed-1, up);
        squares.remove(map.getSquareFromMap(x, y-1));

        return allWays.size() != 0;
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
