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
    private static Square[][] squares;
    private static ArrayList<ArrayList<Square>> allWays;
    private static ArrayList<Square> path;
    private static Government currentGovernment;



    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        GameMenuController.map = map;
        squares = map.getSquares();
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



    public static ArrayList<ArrayList<Unit>> separateUnits(HashMap<String, Integer> unitNameAndCount) {
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

    public static ArrayList<Square> moveUnit(Unit unit, int x, int y) {
        if (squares[x][y].getBuilding() != null) {
            if (squares[x][y].getBuilding() instanceof Defence &&
                    squares[x][y].getUnits().size() >= ((Defence) squares[x][y].getBuilding()).getCapacity())
                return null;

            if (!(squares[x][y].getBuilding() instanceof Defence))
                return null;
        }

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
        if (!map.isCoordinationValid(x, y))
            return false;

        if (!squares[x][y].canPass())
            return false;

        if (up) {

            if (squares[x][y].getBuilding() != null && squares[x][y].getBuilding() instanceof Defence) {
                if (squares[x][y].getBuilding().getName().equals("Stair"))
                    up = false;
            } else if (squares[x][y].getBuilding() == null) {
                LadderMan ladderMan = LadderMan.createLadderMan(DataBase.getCurrentGovernment(), -1, -1);
                if (squares[x][y].getUnits().contains(ladderMan) && unit instanceof Troop && ((Troop) unit).isClimbLadder())
                    up = false;
                else if (!unit.getName().equals("Assassin")) return false;
            } else return false;

        } else if (!up) {

            if (squares[x][y].getBuilding() != null) {
                if (squares[x][y].getBuilding() instanceof Defence ) {
                    if (squares[x][y].getBuilding().getName().equals("Stair"))
                        up = true;
                    else if (!unit.getName().equals("Assassin") && !squares[x][y].getBuilding().getCanPass()) return false;
                }

                else if (!squares[x][y].getBuilding().getCanPass()) return false;

                else if (squares[x][y].getBuilding().getName().equals("DrawBridge")
                        || squares[x][y].getBuilding().getName().equals("SmallStoneGate")
                        || squares[x][y].getBuilding().getName().equals("BigStoneGate"))
                    up = up;//nothing

                else if ((unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher")))
                    return false;

                else return false;

            } else {
                LadderMan ladderMan = LadderMan.createLadderMan(DataBase.getCurrentGovernment(), -1, -1);
                if (squares[x][y].getUnits().contains(ladderMan) && unit instanceof Troop && ((Troop) unit).isClimbLadder())
                    up = true;
            }

        }



        if (speed >= 0 && x == xFin && y == yFin) {
            allWays.add(new ArrayList<>(path));
            return true;
        }

        if (speed == 0) return false;

        if (x < map.getWidth() - 1) {
            path.add(squares[x + 1][y]);
            move(unit, x + 1, y, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x + 1][y]));
        }
        if (x > 0) {
            path.add(squares[x - 1][y]);
            move(unit, x - 1, y, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x - 1][y]));
        }
        if (y < map.getLength() - 1) {
            path.add(squares[x][y + 1]);
            move(unit, x, y + 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x][y + 1]));
        }
        if (y > 0) {
            path.add(squares[x][y - 1]);
            move(unit, x, y - 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x][y - 1]));
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


    public static void disbandUnit() {
        ArrayList<Unit> units = DataBase.getSelectedUnit();
        if (units.size() == 0) return;



        currentGovernment.changeFreeWorkers(units.size());

    }

    private static GameMenuMessages attackBySingleType(String enemyX, String enemyY) {
        Game game=DataBase.getGame();
        if (!Orders.isInputInteger(enemyY) || !Orders.isInputInteger(enemyX))
            return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        if (DataBase.getSelectedUnit().size() == 0)
            return GameMenuMessages.CHOSE_UNIT_FIRST;

        int targetXInNum = Integer.parseInt(enemyX) - 1;
        int targetYInNum = Integer.parseInt(enemyY) - 1;

        if (!map.isCoordinationValid(targetXInNum, targetYInNum))
            return GameMenuMessages.INVALID_COORDINATE;

        int targetType = map.getSquareUnfriendlyBelongingsType(currentGovernment, targetXInNum, targetYInNum);
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
        ArrayList<int[]> squaresOptimalForFight = map.getSquaresWithinRange(targetXInNum, targetYInNum,
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

        if (!map.doesSquareContainEnemyUnits(targetXInNum, targetYInNum, currentGovernment))
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
    
    public static GameMenuMessages createUnitController(String type) {
        int countInNum = 1;
        Troop targetTroop = Troop.getTroopByName(type);

        assert targetTroop != null;
        int totalCost = targetTroop.getCost() * countInNum;
        if (currentGovernment.getMoney() < totalCost)
            return GameMenuMessages.INSUFFICIENT_GOLD;

        if (!doesHaveUnitsWeapons(countInNum, targetTroop))
            return GameMenuMessages.INSUFFICIENT_RESOURCES;

        if (currentGovernment.getFreeWorker() < countInNum)
            return GameMenuMessages.CREATEUNIT_INSUFFICIENT_FREEPOP;

        Barrack selectedBarrack=(Barrack) DataBase.getSelectedBuilding();
        trainTroopsForGovernment(countInNum, targetTroop, selectedBarrack);
        return GameMenuMessages.SUCCESS;
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

    public static GameMenuMessages setUnitModeController(String option) {
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
                return GameMenuMessages.INVALID_STATE;
            }
        }

        Square square = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
        for (Unit unit : square.getUnits()) {
            if (unit.getOwner().equals(DataBase.getCurrentGovernment())) {
                unit.setState(newState);
            }
        }
        return GameMenuMessages.SUCCESS;
    }
}
