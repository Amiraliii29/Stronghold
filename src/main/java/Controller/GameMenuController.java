package Controller;

import Model.Buildings.*;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Resource;
import Model.Square;
import Model.Units.LadderMan;
import Model.Units.Siege;
import Model.Units.StateUnits;
import Model.Units.Troop;
import Model.Units.Unit;
import View.Game;
import View.Enums.Messages.GameMenuMessages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameMenuController {
    private static Game game;
    private static Map map;
    private static ArrayList<ArrayList<Square>> allWays;
    private static ArrayList<Square> path;
    private static Government currentGovernment;



    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        GameMenuController.map = map;
    }

    public static void setCurrentMap() {
        map = DataBase.getSelectedMap();
    }

    public static void setCurrentGovernment() {
        currentGovernment = DataBase.getCurrentGovernment();
    }

    public static void setGame(Game game) {
        GameMenuController.game = game;
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
            path.remove(path.lastIndexOf(map.getSquareFromMap(x + 1, y)));
        }
        if (x > 0) {
            path.add(map.getSquareFromMap(x - 1, y));
            move(unit, x - 1, y, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(map.getSquareFromMap(x - 1, y)));
        }
        if (y < map.getLength() - 1) {
            path.add(map.getSquareFromMap(x, y + 1));
            move(unit, x, y + 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(map.getSquareFromMap(x, y + 1)));
        }
        if (y > 0) {
            path.add(map.getSquareFromMap(x, y - 1));
            move(unit, x, y - 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(map.getSquareFromMap(x, y - 1)));
        }

        return allWays.size() != 0;
    }

    public static boolean constructBuilding(Building building) {
        if (!map.canConstructBuildingInPlace(building, building.getXCoordinateLeft(), building.getYCoordinateUp()))
            game.showErrorText("Can't Build Here!");

        else if (building.getCost() > currentGovernment.getMoney())
            game.showErrorText("You Don't Have Enough Money!");

        else if (building.getResource() != null &&
                currentGovernment.getResourceInStockpiles(building.getResource()) < building.getNumberOfResource())
            game.showErrorText("Don't Have Enough Material!");

        else if (building instanceof Generator && ((Generator) building).getNumberOfWorker() > currentGovernment.getFreeWorker())
            game.showErrorText("Not Enough Free Worker!");

        else {
            currentGovernment.changeMoney(-building.getCost());
            currentGovernment.removeFromStockpile(building.getResource(), building.getNumberOfResource());

            switch (Building.getBuildingCategoryByName(building.getName())) {
                case "Generator" -> {
                    assert building instanceof Generator;
                    Generator generator = (Generator) building;
                    currentGovernment.changePopulation(generator.getNumberOfWorker());
                    currentGovernment.changeFreeWorkers(-generator.getNumberOfWorker());
                    currentGovernment.addToGenerationRate(generator.getResourceGenerate().getName(), generator.getGeneratingRate());
                    currentGovernment.applyOxEffectOnStoneGeneration();
                }
                case "TownBuilding" -> {
                    assert building instanceof TownBuilding;
                    TownBuilding townBuilding = (TownBuilding) building;
                    currentGovernment.addToMaxPopulation(townBuilding.getCapacity());
                    currentGovernment.updateBuildingPopularity();
                }
            }
            map.constructBuilding(building, building.getXCoordinateLeft(), building.getYCoordinateUp());
            return true;
        }

        return true; // TODO : make it false
    }


    // public static void disbandUnit() {
    //     ArrayList<Unit> units = DataBase.getSelectedUnit();
    //     if (units.size() == 0) return;



    //     currentGovernment.changeFreeWorkers(units.size());
    //     DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).removeAllUnit(units.get(0));
    // }

    private static void attackBySingleType(String enemyX, String enemyY) {
        Game game=DataBase.getGame();

        int targetXInNum = Integer.parseInt(enemyX);
        int targetYInNum = Integer.parseInt(enemyY);

        int targetType = map.getSquareUnfriendlyBelongingsType(currentGovernment, targetXInNum, targetYInNum);

        ArrayList<Unit> currentUnits = DataBase.getSelectedUnit();
        int currentUnitsX = currentUnits.get(0).getXCoordinate();
        int currentUnitsY = currentUnits.get(0).getYCoordinate();
        int unitRange = currentUnits.get(0).getAttackRange();

        if (unitRange > Map.getDistance(currentUnitsX, currentUnitsY, targetXInNum, targetYInNum)) {
            rangedAttackController(enemyX, enemyY);
            return ;
        }
        
        int unitsZoneFromTarget = Map.getCartesianZone(targetXInNum, targetYInNum, currentUnitsX, currentUnitsY);
        ArrayList<int[]> squaresOptimalForFight = map.getSquaresWithinRange(targetXInNum, targetYInNum,
                unitRange, unitsZoneFromTarget);

        boolean result = false;
        System.out.println(squaresOptimalForFight.size());
        for (int[] validCoord : squaresOptimalForFight) {
            if (game.move(validCoord[0], validCoord[1])) {
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
            return ;

        game.showErrorText("TARGET NOT IN RANGE");
    }

    private static void rangedAttackController(String enemyX, String enemyY) {

        int targetXInNum = Integer.parseInt(enemyX);
        int targetYInNum = Integer.parseInt(enemyY);

        if (DataBase.getSelectedUnit().size() == 0)
            return ;
        
        int currentUnitsX = DataBase.getSelectedUnit().get(0).getXCoordinate();
        int currentUnitsY = DataBase.getSelectedUnit().get(0).getYCoordinate();

        double distance = Map.getDistance(targetXInNum, targetYInNum, currentUnitsX, currentUnitsY);
        int unitRange = DataBase.getSelectedUnit().get(0).getAttackRange();

        if (unitRange < distance){
            game.showErrorText("Enemy out of Range");
            return ;
        }

        DataBase.attackEnemyBySelectedUnits(distance, targetXInNum, targetYInNum);
        return ;
    }

    public static void AttackBySelectedUnits(String enemyX, String enemyY){
        HashMap <String , ArrayList<Unit>> unitsByType=DataBase.getSelectedUnitsByType();
        for (String unitType : unitsByType.keySet()) {
            DataBase.setSelectedUnit(unitsByType.get(unitType));
            attackBySingleType(enemyX, enemyY);
        }
    }

    private static void trainTroopsForGovernment(int count, Troop targetTroop, Barrack selectedBarrack) {
        int barrackX = selectedBarrack.getXCoordinateLeft();
        int barrackY = selectedBarrack.getYCoordinateUp();

        currentGovernment.changeMoney(-count * targetTroop.getCost());

        for (Resource weapon : targetTroop.getWeapons()) {
            currentGovernment.removeFromStockpile(weapon, count);
        }

        for (int i = 0; i < count; i++) {
            Troop newTroop = Troop.createTroop(currentGovernment, targetTroop.getName(), barrackX, barrackY);
            // addToAllUnits(newTroop);
        }

        currentGovernment.changeFreeWorkers(-count);
    }
    
    public static void createUnitController(String type) {
        int countInNum = 1;
        Troop targetTroop = Troop.getTroopByName(type);

        assert targetTroop != null;
        int totalCost = targetTroop.getCost() * countInNum;
        if (currentGovernment.getMoney() < totalCost){
            game.showErrorText("Not enough gold!");
            return ;
        }

        if (!doesHaveUnitsWeapons(countInNum, targetTroop)){
            game.showErrorText("Insufficient weapons!");
            return ;
        }

        if (currentGovernment.getFreeWorker() < countInNum){
            game.showErrorText("Insufficient population!");
            return ;
        }

        Barrack selectedBarrack=(Barrack) DataBase.getSelectedBuilding();
        trainTroopsForGovernment(countInNum, targetTroop, selectedBarrack);
    }

    private static boolean doesHaveUnitsWeapons(int count, Troop targetTroop) {
        ArrayList<Resource> neededWeapons = new ArrayList<>(targetTroop.getWeapons());

        for (Resource resource : neededWeapons) {
            resource.changeCount(count - resource.getCount());
        }

        for (Resource resource : neededWeapons) {
            if (currentGovernment.getResourceInStockpiles(resource) < resource.getCount())
                return false;
        }

        return true;
    }

    public static void setUnitModeController(String option) {
        String x = Orders.findFlagOption("-x", option);
        String y = Orders.findFlagOption("-y", option);
        String state = Orders.findFlagOption("-s", option);

        // assert x != null;
        // if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
        //     return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        int xCoordinate = Integer.parseInt(x) - 1;
        int yCoordinate = Integer.parseInt(y) - 1;

        // if (!map.isCoordinationValid(xCoordinate, yCoordinate))
        //     return GameMenuMessages.INVALID_COORDINATE;

        StateUnits newState;
        switch (Objects.requireNonNull(state)) {
            case "Standing" -> newState = StateUnits.Stan_Ground;
            case "Defensive" -> newState = StateUnits.Defensive;
            case "Offensive" -> newState = StateUnits.Aggressive;
            default -> {
                return ;
            }
        }

        Square square = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
        for (Unit unit : square.getUnits()) {
            if (unit.getOwner().equals(DataBase.getCurrentGovernment())) {
                unit.setState(newState);
            }
        }
    }

}
