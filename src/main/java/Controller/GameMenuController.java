package Controller;

import Model.Buildings.*;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Resource;
import Model.Square;
import Model.Units.*;
import View.Controller.BuildingInfo;
import View.Controller.GameGraphicController;
import View.Game;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static Game getGame() {
        return game;
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




    public static ArrayList<ArrayList<Unit>> separateUnits() {
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
        if (unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher"))
            up = false;

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
        return false;
    }



    public static void disbandUnit() {
        ArrayList<ArrayList<Unit>> allUnits = separateUnits();
        if (allUnits == null) return;

        for (ArrayList<Unit> selectedUnit : allUnits) {
            currentGovernment.changeFreeWorkers(selectedUnit.size());
            squares[selectedUnit.get(0).getXCoordinate()][selectedUnit.get(0).getYCoordinate()].removeAllUnit(selectedUnit.get(0));
        }
    }

    public static void modifyGates(boolean open) {
        DataBase.getSelectedBuilding().changeCanPass(open);
    }

    public static void repair() {
        Building selectedBuilding = DataBase.getSelectedBuilding();
        if (selectedBuilding.getMaximumHp() == selectedBuilding.getHp()) return;

        Resource stone = Resource.getResourceByName("Stone");
        double damage = (selectedBuilding.getMaximumHp() - selectedBuilding.getHp()) / (selectedBuilding.getMaximumHp() * 1.0);
        int stoneNumber = (int) (Math.ceil(damage * selectedBuilding.getNumberOfResource()));

        if (currentGovernment.getResourceInStockpiles(stone) >= stoneNumber) {
            currentGovernment.removeFromStockpile(stone, stoneNumber);
            selectedBuilding.changeHP(selectedBuilding.getHp() - selectedBuilding.getMaximumHp());

            try {
                game.showBuildingDetail(selectedBuilding);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void changeProduct(String product) {
        Building selectedBuilding = DataBase.getSelectedBuilding();
        if (!(selectedBuilding instanceof Generator)) return;

        Resource last = ((Generator) selectedBuilding).getResourceGenerate();
        currentGovernment.addToGenerationRate(product, ((Generator) selectedBuilding).getGeneratingRate());
        currentGovernment.removeFromResourceGenerationRate(last.getName(), ((Generator) selectedBuilding).getGeneratingRate());
    }

    public static void setUnitModeController(String state) {
        StateUnits newState;
        switch (state) {
            case "Standing" -> newState = StateUnits.Stan_Ground;
            case "Defensive" -> newState = StateUnits.Defensive;
            case "Aggressive" -> newState = StateUnits.Aggressive;
            default -> {
                return ;
            }
        }

        for (Unit unit : DataBase.getSelectedUnit())
            unit.setState(newState);
    }




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
                game.setAttackIcon();
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
        game.setAttackIcon();
    }

    public static void AttackBySelectedUnits(String enemyX, String enemyY){
        HashMap <String , ArrayList<Unit>> unitsByType=DataBase.getSelectedUnitsByType();
        for (String unitType : unitsByType.keySet()) {
            DataBase.setSelectedUnit(unitsByType.get(unitType));
            attackBySingleType(enemyX, enemyY);
        }
    }



    public static void buildSiege(String siegeName) {
        ArrayList<ArrayList<Unit>> allUnits = separateUnits();
        if (allUnits == null) return;

        ArrayList<Unit> engineerUnit = null;
        for (ArrayList<Unit> selectedUnit : allUnits) {
            if (selectedUnit.get(0) instanceof Engineer) {
                engineerUnit = selectedUnit;
                break;
            }
        }

        Siege siege = Siege.createSiege(currentGovernment, siegeName, -1 , -1);
        if (siege == null || engineerUnit == null) return;

        int x = engineerUnit.get(0).getXCoordinate();
        int y = engineerUnit.get(0).getYCoordinate();

        if (siege.getEngineerNeed() > engineerUnit.size())
            game.showErrorText("Not Enough Engineer!");
        else if (siege.getCost() > currentGovernment.getMoney())
            game.showErrorText("Not Enough Money!");
        else if (squares[x][y].getBuilding() != null && !squares[x][y].getBuilding().getName().equals("CircularTower")
                && !squares[x][y].getBuilding().getName().equals("SquareTower"))
            game.showErrorText("Can't Build Siege Here!");
        else {
            currentGovernment.changeMoney(-siege.getCost());
            DataBase.removeUnit(engineerUnit.get(0), siege.getEngineerNeed());
            Siege.createSiege(currentGovernment, siegeName, x, y);
            game.drawMap();
        }
    }

    private static void trainTroopsForGovernment(int count, Unit unit, Barrack selectedBarrack) {
        int barrackX = selectedBarrack.getXCoordinateLeft();
        int barrackY = selectedBarrack.getYCoordinateUp();

        currentGovernment.changeMoney(-count * unit.getCost());
        currentGovernment.changeFreeWorkers(-count);
        GameGraphicController.setPopularityGoldPopulation();

        for (int i = 0; i < count; i++) {
            if (unit.getName().equals("Engineer")) {
                Engineer.createEngineer(currentGovernment, barrackX - 1, barrackY - 1);
            } else if (unit.getName().equals("LadderMan")) {
                LadderMan.createLadderMan(currentGovernment, barrackX - 1, barrackY - 1);
            } else if (unit.getName().equals("Tunneler")) {
                Tunneler.createTunneler(currentGovernment, barrackX - 1, barrackY - 1);
            } else {
                Troop.createTroop(currentGovernment, unit.getName() , barrackX - 1, barrackY - 1);
            }
        }

        game.drawMap();
    }
    
    public static void createUnitController(String type) {
        //won't create Siege !!!

        int countInNum = 1;
        Unit unit;

        if (type.equals("Engineer"))
            unit = Engineer.createEngineer(currentGovernment, -1, -1);
        else if (type.equals("LadderMan"))
            unit = LadderMan.createLadderMan(currentGovernment, -1, -1);
        else if (type.equals("Tunneler"))
            unit = Tunneler.createTunneler(currentGovernment, -1, -1);
        else
            unit = Troop.getTroopByName(type);

        assert unit != null;
        int totalCost = unit.getCost() * countInNum;

        if (currentGovernment.getMoney() < totalCost)
            game.showErrorText("Not enough gold!");
        else if (currentGovernment.getFreeWorker() < countInNum)
            game.showErrorText("Insufficient population!");
        else if (!doesUnitsHaveWeapons(countInNum, unit))
            game.showErrorText("Insufficient weapons!");
        else {
            Barrack selectedBarrack = (Barrack) DataBase.getSelectedBuilding();
            trainTroopsForGovernment(countInNum, unit, selectedBarrack);
        }
    }

    private static boolean doesUnitsHaveWeapons(int count, Unit unit) {
        if (!(unit instanceof Troop)) return true;
        ArrayList<Resource> neededWeapons = ((Troop) unit).getWeapons();
        if(neededWeapons==null) return true;
        for (Resource resource : neededWeapons) {
            if (currentGovernment.getResourceInStockpiles(resource) < count)
                return false;
        }

        for (Resource resource : neededWeapons) {
            currentGovernment.removeFromStockpile(resource, count);
        }

        return true;
    }
}
