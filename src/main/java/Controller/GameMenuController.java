package Controller;

import Model.Buildings.Defence;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Square;
import Model.Units.LadderMan;
import Model.Units.Siege;
import Model.Units.Troop;
import Model.Units.Unit;
import View.Game;
import View.Enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMenuController {
    private static Map map;
    private static ArrayList<ArrayList<Square>> allWays;
    private static ArrayList<Square> path;
    private static Map currentMap;
    private static Government currentGovernment;

    public static void setMap(Map map) {
        GameMenuController.map = map;
    }

    public static Map getMap() {
        return map;
    }

    public static void setCurrentMap() {
        currentMap = DataBase.getSelectedMap();
    }

    public static void setCurrentGovernment() {
        currentGovernment = DataBase.getCurrentGovernment();
    }

    public static ArrayList<Square> moveUnit(Unit unit, int x, int y) {
        allWays = new ArrayList<>();
        path = new ArrayList<>();

        int startX = unit.getXCoordinate();
        int startY = unit.getYCoordinate();
        boolean up = map.getSquareFromMap(startX, startY).getBuilding() instanceof Defence;

        if (move(unit, startX, startY, x, y, Unit.getMaxDistance(), up)) {
            int size = 1000;
            for (ArrayList<Square> array : allWays) {
                if (array.size() < size) {
                    path = array;
                    size = array.size();
                }
            }

            return path;
        } else
            return null;
    }

    private static boolean move(Unit unit, int x, int y, int xFin, int yFin, int speed, boolean up) {
        // conditions
        if (!map.isCoordinationValid(x, y))
            return false;

        if (!map.getSquareFromMap(x, y).canPass())
            return false;

        if (map.getSquareFromMap(x, y).getBuilding() != null
                && !map.getSquareFromMap(x, y).getBuilding().getName().equals("Trap")) {
            if (unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher")) {

                if (!map.getSquareFromMap(x, y).getBuilding().getCanPass(up)
                        || map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair"))
                    return false;
                return true;

            } else if (!unit.getName().equals("Assassin")) {

                if (!(map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                        || map.getSquareFromMap(x, y).getBuilding().getCanPass(up)))
                    return false;

                LadderMan ladderMan = LadderMan.createLadderMan(DataBase.getCurrentGovernment(), -1, -1);
                if (map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair"))
                    up = !up;

                else if (map.getSquareFromMap(x, y).getUnits().contains(ladderMan)
                        && unit instanceof Troop && ((Troop) unit).isClimbLadder())
                    up = !up;

                if (up && !(map.getSquareFromMap(x, y).getBuilding() instanceof Defence))
                    return false;

                if (!up && (map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                        && !map.getSquareFromMap(x, y).getBuilding().getCanPass(false)))
                    return false;
            }
        }

        if (speed >= 0 && x == xFin && y == yFin) {
            if (map.getSquareFromMap(x, y).getBuilding() != null
                    && map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                    && map.getSquareFromMap(x, y).getUnits()
                            .size() >= ((Defence) map.getSquareFromMap(x, y).getBuilding()).getCapacity())
                return false;

            if (map.getSquareFromMap(x, y).getBuilding() != null
                    && map.getSquareFromMap(x, y).getBuilding().getCanPass(up))
                return false;

            allWays.add(new ArrayList<>(path));
            return true;
        }

        if (speed == 0)
            return false;

        if (x < map.getWidth() - 1) {
            path.add(map.getSquareFromMap(x + 1, y));
            move(unit, x + 1, y, xFin, yFin, speed - 1, up);
            path.remove(map.getSquareFromMap(x + 1, y));
        }
        if (x > 0) {
            path.add(map.getSquareFromMap(x - 1, y));
            move(unit, x - 1, y, xFin, yFin, speed - 1, up);
            path.remove(map.getSquareFromMap(x - 1, y));
        }
        if (y < map.getLength() - 1) {
            path.add(map.getSquareFromMap(x, y + 1));
            move(unit, x, y + 1, xFin, yFin, speed - 1, up);
            path.remove(map.getSquareFromMap(x, y + 1));
        }
        if (y > 0) {
            path.add(map.getSquareFromMap(x, y - 1));
            move(unit, x, y - 1, xFin, yFin, speed - 1, up);
            path.remove(map.getSquareFromMap(x, y - 1));
        }

        return allWays.size() != 0;
    }

    private static GameMenuMessages attackBySingleType(String enemyX, String enemyY) {
        Game game=DataBase.getGame();
        if (!Orders.isInputInteger(enemyY) || !Orders.isInputInteger(enemyX))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        if (DataBase.getSelectedUnit().size() == 0)
            return GameMenuMessages.CHOSE_UNIT_FIRST;

        int targetXInNum = Integer.parseInt(enemyX) - 1;
        int targetYInNum = Integer.parseInt(enemyY) - 1;

        if (!currentMap.isCoordinationValid(targetXInNum, targetYInNum))
            return GameMenuMessages.INVALID_COORDINATE;

        int targetType = currentMap.getSquareUnfriendlyBelongingsType(currentGovernment, targetXInNum, targetYInNum);
        if (targetType == 0)
            return GameMenuMessages.ATTACK_NO_ENEMY_IN_AREA;

        ArrayList<Unit> currentUnits = DataBase.getSelectedUnit();
        int currentUnitsX = currentUnits.get(0).getXCoordinate();
        int currentUnitsY = currentUnits.get(0).getYCoordinate();
        int unitRange = currentUnits.get(0).getAttackRange();

        if (unitRange > Map.getDistance(currentUnitsX, currentUnitsY, targetXInNum, targetYInNum)) {
            rangedAttackController(enemyX, enemyY);
            return GameMenuMessages.SUCCESS;
        }

        int unitsZoneFromTarget = Map.getCartesianZone(targetXInNum, targetYInNum, currentUnitsX, currentUnitsY);
        ArrayList<int[]> squaresOptimalForFight = currentMap.getSquaresWithinRange(targetXInNum, targetYInNum,
                unitRange, unitsZoneFromTarget);

        boolean result = false;
        for (int[] validCoord : squaresOptimalForFight) {
            game.move(validCoord[0], validCoord[1]);
            if (DataBase.isSelectedUnitOnTargetSquare(validCoord[0], validCoord[1])) {
                if (targetType == 1) {
                    double distance = Map.getDistance(targetXInNum, targetYInNum, validCoord[0], validCoord[1]);
                    DataBase.attackEnemyBySelectedUnits(distance, targetXInNum, targetYInNum);
                } else
                    DataBase.attackBuildingBySelectedUnits(targetXInNum, targetYInNum);

                result = true;
                break;
            }
        }
        if (result)
            return GameMenuMessages.SUCCESS;

        return GameMenuMessages.NORMALATTACK_TARGET_NOT_IN_RANGE;
    }

    private static GameMenuMessages rangedAttackController(String enemyX, String enemyY) {
        if (!Orders.isInputInteger(enemyY) || !Orders.isInputInteger(enemyX))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        int targetXInNum = Integer.parseInt(enemyX) - 1;
        int targetYInNum = Integer.parseInt(enemyY) - 1;

        if (DataBase.getSelectedUnit().size() == 0)
            return GameMenuMessages.CHOSE_UNIT_FIRST;

        if (!currentMap.isCoordinationValid(targetXInNum, targetYInNum))
            return GameMenuMessages.INVALID_COORDINATE;

        if (!currentMap.doesSquareContainEnemyUnits(targetXInNum, targetYInNum, currentGovernment))
            return GameMenuMessages.ATTACK_NO_ENEMY_IN_AREA;

        if (!DataBase.areSelectedUnitsRanged())
            return GameMenuMessages.RANGEDATTACK_NON_ARCHER_SELECTION;

        int currentUnitsX = DataBase.getSelectedUnit().get(0).getXCoordinate();
        int currentUnitsY = DataBase.getSelectedUnit().get(0).getYCoordinate();

        double distance = Map.getDistance(targetXInNum, targetYInNum, currentUnitsX, currentUnitsY);
        int unitRange = DataBase.getSelectedUnit().get(0).getAttackRange();

        if (unitRange < distance)
            return GameMenuMessages.RANGEDATTACK_TARGET_NOT_IN_RANGE;

        DataBase.attackEnemyBySelectedUnits(distance, targetXInNum, targetYInNum);
        return GameMenuMessages.SUCCESS;
    }

    public static void AttackBySelectedUnits(String enemyX, String enemyY){
        HashMap <String , ArrayList<Unit>> unitsByType=DataBase.getSelectedUnitsByType();
        for (String unitType : unitsByType.keySet()) {
            DataBase.setSelectedUnit(unitsByType.get(unitType));
            attackBySingleType(unitType, unitType);
        }
    }

}
