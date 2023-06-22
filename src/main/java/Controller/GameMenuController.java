package Controller;

import Model.Buildings.Defence;
import Model.DataBase;
import Model.Map;
import Model.Square;
import Model.Units.LadderMan;
import Model.Units.Siege;
import Model.Units.Troop;
import Model.Units.Unit;

import java.util.ArrayList;

public class GameMenuController {
    private static Map map;
    private static ArrayList<ArrayList<Square>> allWays;
    private static ArrayList<Square> path;


    public static void setMap(Map map) {
        GameMenuController.map = map;
    }

    public static Map getMap() {
        return map;
    }

    public static ArrayList<Square> moveUnit (Unit unit, int x, int y) {
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
        } else return null;
    }

    private static boolean move(Unit unit, int x, int y, int xFin, int yFin, int speed, boolean up) {
        //conditions
        if (!map.isCoordinationValid(x, y)) return false;

        if (!map.getSquareFromMap(x, y).canPass()) return false;

        if (map.getSquareFromMap(x, y).getBuilding() != null && !map.getSquareFromMap(x, y).getBuilding().getName().equals("Trap")) {
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
                if (map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair")) up = !up;

                else if (map.getSquareFromMap(x, y).getUnits().contains(ladderMan)
                        && unit instanceof Troop && ((Troop) unit).isClimbLadder()) up = !up;

                if (up && !(map.getSquareFromMap(x, y).getBuilding() instanceof Defence)) return false;

                if (!up && (map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                        && !map.getSquareFromMap(x, y).getBuilding().getCanPass(false)))
                    return false;
            }
        }

        if (speed >= 0 && x == xFin && y == yFin) {
            if (map.getSquareFromMap(x, y).getBuilding() != null
                    && map.getSquareFromMap(x, y).getBuilding() instanceof Defence
                    && map.getSquareFromMap(x, y).getUnits().size() >= ((Defence) map.getSquareFromMap(x, y).getBuilding()).getCapacity())
                return false;

            if (map.getSquareFromMap(x,y).getBuilding() != null && map.getSquareFromMap(x,y).getBuilding().getCanPass(up))
                return false;

            allWays.add(new ArrayList<>(path));
            return true;
        }

        if (speed == 0) return false;

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
}
