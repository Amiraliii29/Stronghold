package Controller;

import Main.Request;
import Main.ResultEnums;
import Main.UserDataBase;
import Model.*;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Buildings.Generator;
import Model.Buildings.TownBuilding;
import Model.Units.*;
import java.util.ArrayList;

public class GameMenuController {
    private UserDataBase userDataBase;
    private Map map;
    private Square[][] squares;
    private ArrayList<ArrayList<Square>> allWays;
    private ArrayList<Square> path;


    public GameMenuController(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
        map = userDataBase.getDataBase().getSelectedMap();
        squares = map.getSquares();
    }




    public boolean constructBuilding(Building building) {
        Government currentGovernment = userDataBase.getGovernment();

        if (!map.canConstructBuildingInPlace(building, building.getXCoordinateLeft(), building.getYCoordinateUp()))
            userDataBase.sendRequest(new Request(ResultEnums.CANT_BUILD_HERE));

        else if (building.getCost() > currentGovernment.getMoney())
            userDataBase.sendRequest(new Request(ResultEnums.NOT_ENOUGH_MONEY));

        else if (building.getResource() != null &&
                currentGovernment.getResourceInStockpiles(building.getResource()) < building.getNumberOfResource())
            userDataBase.sendRequest(new Request(ResultEnums.NOT_ENOUGH_MATERIAL));

        else if (building instanceof Generator && ((Generator) building).getNumberOfWorker() > currentGovernment.getFreeWorker())
            userDataBase.sendRequest(new Request(ResultEnums.NOT_ENOUGH_FREE_WORKER));

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
            userDataBase.sendRequest(new Request(ResultEnums.SUCCESS));
            return true;
        }
        return false;
    }





//    public void createUnitController(String type) {
//        //won't create Siege !!!
//
//        int countInNum = 1;
//        Unit unit;
//
//        if (type.equals("Engineer"))
//            unit = Engineer.createEngineer(userDataBase.getGovernment(), -1, -1);
//        else if (type.equals("LadderMan"))
//            unit = LadderMan.createLadderMan(userDataBase.getGovernment(), -1, -1);
//        else if (type.equals("Tunneler"))
//            unit = Tunneler.createTunneler(userDataBase.getGovernment(), -1, -1);
//        else
//            unit = Troop.getTroopByName(type);
//
//        assert unit != null;
//        int totalCost = unit.getCost() * countInNum;
//
//        if (userDataBase.getGovernment().getMoney() < totalCost)
//            game.showErrorText("Not enough gold!");
//        else if (userDataBase.getGovernment().getFreeWorker() < countInNum)
//            game.showErrorText("Insufficient population!");
//        else if (!doesUnitsHaveWeapons(countInNum, unit))
//            game.showErrorText("Insufficient weapons!");
//        else {
//            Barrack selectedBarrack = (Barrack) DataBase.getSelectedBuilding();
//            trainTroopsForGovernment(countInNum, unit, selectedBarrack);
//        }
//    }
//
//    private boolean doesUnitsHaveWeapons(int count, Unit unit) {
//        if (!(unit instanceof Troop)) return true;
//        ArrayList<Resource> neededWeapons = ((Troop) unit).getWeapons();
//        if(neededWeapons==null) return true;
//        for (Resource resource : neededWeapons) {
//            if (currentGovernment.getResourceInStockpiles(resource) < count)
//                return false;
//        }
//
//        for (Resource resource : neededWeapons) {
//            currentGovernment.removeFromStockpile(resource, count);
//        }
//
//        return true;
//    }



//    public static void disbandUnit() {
//        ArrayList<ArrayList<Unit>> allUnits = separateUnits();
//        if (allUnits == null) return;
//
//        for (ArrayList<Unit> selectedUnit : allUnits) {
//            currentGovernment.changeFreeWorkers(selectedUnit.size());
//            squares[selectedUnit.get(0).getXCoordinate()][selectedUnit.get(0).getYCoordinate()].removeAllUnit(selectedUnit.get(0));
//        }
//    }
//
//    public static void modifyGates(boolean open) {
//        DataBase.getSelectedBuilding().changeCanPass(open);
//    }
//
//    public static void repair() {
//        Building selectedBuilding = DataBase.getSelectedBuilding();
//        if (selectedBuilding.getMaximumHp() == selectedBuilding.getHp()) return;
//
//        Resource stone = Resource.getResourceByName("Stone");
//        double damage = (selectedBuilding.getMaximumHp() - selectedBuilding.getHp()) / (selectedBuilding.getMaximumHp() * 1.0);
//        int stoneNumber = (int) (Math.ceil(damage * selectedBuilding.getNumberOfResource()));
//
//        if (currentGovernment.getResourceInStockpiles(stone) >= stoneNumber) {
//            currentGovernment.removeFromStockpile(stone, stoneNumber);
//            selectedBuilding.changeHP(selectedBuilding.getHp() - selectedBuilding.getMaximumHp());
//
//            try {
//                game.showBuildingDetail(selectedBuilding);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public static void changeProduct(String product) {
//        Building selectedBuilding = DataBase.getSelectedBuilding();
//        if (!(selectedBuilding instanceof Generator)) return;
//
//        Resource last = ((Generator) selectedBuilding).getResourceGenerate();
//        currentGovernment.addToGenerationRate(product, ((Generator) selectedBuilding).getGeneratingRate());
//        currentGovernment.removeFromResourceGenerationRate(last.getName(), ((Generator) selectedBuilding).getGeneratingRate());
//    }
//
//    public static void setUnitModeController(String state) {
//        StateUnits newState;
//        switch (state) {
//            case "Standing" -> newState = StateUnits.Stan_Ground;
//            case "Defensive" -> newState = StateUnits.Defensive;
//            case "Aggressive" -> newState = StateUnits.Aggressive;
//            default -> {
//                return ;
//            }
//        }
//
//        for (Unit unit : DataBase.getSelectedUnit())
//            unit.setState(newState);
//    }
}
