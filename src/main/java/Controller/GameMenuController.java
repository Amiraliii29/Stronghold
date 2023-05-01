package Controller;

import java.lang.Thread.State;
import java.util.ArrayList;

import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Square;
import Model.Buildings.*;
import Model.Resources.Resource;
import Model.Units.Troop;
import Model.Units.Unit;
import View.Enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameMenuController {

    private static Government currentGovernment;
    private static Building selectedBuilding=null;
    private static Map currentMap;
    private static ArrayList<Building> allBuildings=new ArrayList<Building>();

    public static GameMenuMessages nextTurnController() {
        return null;
    }

    public static void setCurrentGovernment(Government government){
        currentGovernment=government;
    }

    public static GameMenuMessages userLogout() {
        return null;
    }
   
    public static void addToGameBuildings(Building building){
        allBuildings.add(building);
    }

    public static GameMenuMessages dropBuildingController(String x, String y, String buildingType) {
    
        if(!Orders.isInputInteger(x) || !Orders.isInputInteger(y))
        return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        int xInNum=Integer.parseInt(x);
        int yInNum=Integer.parseInt(y);
        Building targetBuilding=getBuildingByName(buildingType);

        if(!currentMap.isCoordinationValid(xInNum, yInNum))
            return GameMenuMessages.INVALID_COORDINATE;


        if(targetBuilding==null) 
            return GameMenuMessages.DROPBUILDING_INVALID_BUILDINGNAME;
        
        if(!currentMap.canConstructBuildingInPlace(targetBuilding, xInNum, yInNum))
            return GameMenuMessages.DROPBUILDING_INVALID_PLACE;
        
        int buildingCost=targetBuilding.getCost();
        if(buildingCost>currentGovernment.getMoney())
            return GameMenuMessages.INSUFFICIENT_GOLD;
        
        int resourceCount=targetBuilding.getNumberOfResource();
        if(targetBuilding.getResource()!=null)
            if(currentGovernment.getResourceInStockpiles(targetBuilding.getResource()) < resourceCount )
                return GameMenuMessages.INSUFFICIENT_RESOURCES;
        

        currentMap.constructBuilding(targetBuilding, xInNum, yInNum);

        currentGovernment.changeMoney(-buildingCost);

        if(targetBuilding.getResource()!=null)
            currentGovernment.removeFromStockpile(targetBuilding.getResource(), resourceCount);

        constructBuildingForPlayer(buildingType, xInNum, yInNum);
        return GameMenuMessages.SUCCESS;
    }

    private static void constructBuildingForPlayer(String buildingName, int x, int y ){
        String buildingCategory=getBuildingCategoryByName(buildingName);
        switch (buildingCategory) {
            case "Generator":
                Generator generator=Generator.createGenerator(currentGovernment, x, y, buildingName);
                currentGovernment.addToGenerationRate(generator.getResourceGenerate().getName(),generator.getGeneratingRate());
                break;
        
            case "TownBuilding":
                TownBuilding townBuilding=TownBuilding.createTownBuilding(currentGovernment, x, y, buildingName);
                currentGovernment.addToMaxPopulation(townBuilding.getCapacity());
                currentGovernment.updateBuildingPopularity();
                break;
            
            case "Stockpile":
                Stockpile stockpile=Stockpile.createStockpile(currentGovernment, x, y, buildingName);

            case "Barrack":
                Barrack barrack=Barrack.createBarrack(currentGovernment, x, y, buildingName);

            default:
                Defence defence=Defence.createDefence(currentGovernment, x, y, buildingName)
                break;
        }
    }
    
    private static String getBuildingCategoryByName(String buildingName){
        for (String name : Generator.getGeneratorsName()) 
            if(buildingName.equals(name)) return "Generator";
                
        for (String name : Barrack.getBarracksName()) 
            if(buildingName.equals(name)) return "Barrack";
                
        for(String name : Defence.getDefencesName())
            if(buildingName.equals(name)) return "Defence";

        for(String name : TownBuilding.getTownBuildingsName())
            if(buildingName.equals(name)) return "TownBuilding";

        for(String name : Stockpile.getStockpilesName())
            if(buildingName.equals(name)) return "Stockpile";

        return null;
    }

    public static GameMenuMessages selectBuildingController(String x, String y) {

        if(!Orders.isInputInteger(x) || !Orders.isInputInteger(y))
        return GameMenuMessages.WRONG_FORMAT_COORDINATE;

        int xInNum=Integer.parseInt(x);
        int yInNum=Integer.parseInt(y);

        if(!currentMap.isCoordinationValid(xInNum, yInNum))
            return GameMenuMessages.INVALID_COORDINATE;

        Building targetBuilding=currentMap.getSquareFromMap(xInNum,yInNum).getBuilding();
        if(targetBuilding==null)
            return GameMenuMessages.SELECTBUILDING_EMPTY_SQUARE;
        
        if(!targetBuilding.getOwner().getOwner().getUsername().equals(currentGovernment.getOwner().getUsername()))
            return GameMenuMessages.SELECTBUILDING_UNOWNED_BUILDING;

        selectedBuilding=targetBuilding;
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages createUnitController(String type, String count) {
        if(!(selectedBuilding instanceof Barrack) )
            return GameMenuMessages.CREATE_UNIT_WRONG_SELECTED_BUILDING;

        if(!Orders.isInputInteger(count))
            return GameMenuMessages.CREATEUNIT_WRONG_NUMBERFORMAT;

        int countInNum=Integer.parseInt(count);
        if(countInNum<=0)
            return GameMenuMessages.CREATEUNIT_WRONG_NUMBERFORMAT;

        Barrack selectedBarrack=(Barrack) selectedBuilding;
        if(!selectedBarrack.canBuildTroopByName(type))
            return GameMenuMessages.CREATEUNIT_UNMATCHING_BARRACK;

        Troop targetTroop=Troop.getTroopByName(type);

        int totalCost=targetTroop.getCost()*countInNum;
        if(currentGovernment.getMoney()<totalCost)
            return GameMenuMessages.INSUFFICIENT_GOLD;

        if(!doesHaveUnitsWeapons(countInNum, targetTroop))
            return GameMenuMessages.INSUFFICIENT_RESOURCES;

        if(currentGovernment.getFreeWorker()<countInNum)
            return GameMenuMessages.CREATEUNIT_INSUFFICIENT_FREEPOP;

        trainTroopsForGovernment(countInNum, targetTroop, selectedBarrack);
            return GameMenuMessages.SUCCESS;
    }

    private static boolean doesHaveUnitsWeapons(int count,Troop targetTroop){
        ArrayList <Resource> neededWeapons=new ArrayList<Resource>(targetTroop.getWeapons());
        
        for (Resource resource : neededWeapons) {
            resource.changeCount(count-resource.getCount());
        }

        for (Resource resource : neededWeapons) {
            if(currentGovernment.getResourceInStockpiles(resource)<resource.getCount())
            return false;
        }

        return true;
    }

    private static void trainTroopsForGovernment(int count, Troop targetTroop, Barrack selectedBarrack){

        int barrackX=selectedBarrack.getXCoordinateLeft();
        int barrackY=selectedBarrack.getYCoordinateUp();

        currentGovernment.changeMoney(-count*targetTroop.getCost());

        for (Resource weapon : targetTroop.getWeapons()) {
            currentGovernment.removeFromStockpile(weapon, count);
        }

        for (int i = 0; i < count; i++) {
            Troop newTroop=Troop.createTroop(currentGovernment, targetTroop.getName(), barrackX, barrackY);
            currentMap.getSquareFromMap(barrackX+2+i%3, barrackY+2+(i+1)%3).addTroop(newTroop);
            currentGovernment.addUnits(newTroop);
        }

        currentGovernment.changeFreeWorkers(-count);
        currentGovernment.changePopulation(count);
    }

    public static GameMenuMessages repairController() {

        if( selectedBuilding==null)
            return GameMenuMessages.EMPTY_INPUT_FIELDS_ERROR;

        if( !(selectedBuilding instanceof Defence) )
            return GameMenuMessages.REPAIR_UNREPAIRABLE_SELECTED_BUILDING;

        int hpToRecover=selectedBuilding.getMaximumHp()-selectedBuilding.getHp();
        int stepsToRepair=hpToRecover/50 +1;
        Resource stone=Resource.getResourceByName("Stone");

        for (int i = 0; i < stepsToRepair+1; i++) {
            if(currentGovernment.getResourceInStockpiles(stone)<5)
                return GameMenuMessages.INSUFFICIENT_RESOURCES;

            currentGovernment.removeFromStockpile(stone, 5);
            
            if(selectedBuilding.changeHP(-50)> selectedBuilding.getMaximumHp()){
                selectedBuilding.changeHP(selectedBuilding.getHp()-selectedBuilding.getMaximumHp());
                break;
            }
        }
        return GameMenuMessages.SUCCESS;
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
        return null;
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
        return null;
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

    public static GameMenuMessages showMapController(String x, String y) {
        return null;
    }

    public static void setCurrentMap(Map map){
        currentMap=map;
    }




    private static ArrayList<String> getBuildingValidLandsByName(String buildingName){
        
        for (Building building : allBuildings) 
            if(building.getName().equals(buildingName))
                return building.getLands();
    
        return null;
    }

    private static Building getBuildingByName(String buildingName){
        for (Building building : allBuildings) 
            if(building.getName().equals(buildingName))
                return building;
        return null;
    }
}
