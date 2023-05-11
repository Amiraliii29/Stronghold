package Model;

import Model.Buildings.Building;
import Model.Buildings.Generator;
import Model.Buildings.TownBuilding;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Controller.GameMenuController;
import Controller.JsonConverter;
import Model.Units.Troop;
import Model.Units.Unit;

public class DataBase {
    private static ArrayList<Government> governments;
    private static final ArrayList<Map> maps;
    private static Government currentGovernment;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Unit> selectedUnit;
    private static SecureRandom randomGenerator = new SecureRandom();

    static {
        JsonConverter.fillFormerUsersDatabase("src/main/resources/jsonData/Users.json");
        governments = new ArrayList<>();
        maps = new ArrayList<>();
        selectedUnit = new ArrayList<>();
    }


    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        DataBase.currentGovernment = currentGovernment;
        selectedUnit = new ArrayList<>();
        selectedBuilding = null;
    }

    public static Government getGovernmentByUserName(String userName) {
        for (Government government : governments)
            if (government.getOwner().getUsername().equals(userName))
                return government;

        return null;
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static void addGovernment(Government government) {
        governments.add(government);
    }

    public static void newGovernments() {
        governments = new ArrayList<>();
    }

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static void setSelectedBuilding(Building selectedBuilding) {
        DataBase.selectedBuilding = selectedBuilding;
    }

    public static ArrayList<Unit> getSelectedUnit() {
        return selectedUnit;
    }

    public static void addSelectedUnit(Troop selectedUnit) {
        DataBase.selectedUnit.add(selectedUnit);
    }

    public static void setSelectedUnit(ArrayList<Unit> selectedUnit) {
        DataBase.selectedUnit = selectedUnit;
    }

    public static void addMap(Map map) {
        maps.add(map);
    }

    public static Map getMapByName(String name) {
        return null;
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }

    public static void attackBuildingBySelectedUnits(int xUnderAttack, int yUnderAttack) {

        Building buildingUnderAttack = selectedMap.getSquareFromMap(xUnderAttack, yUnderAttack).getBuilding();

        if (isBuildingFriendly(currentGovernment, buildingUnderAttack))
            return;

        for (Unit unit : selectedUnit)
            buildingUnderAttack.changeHP(unit.getDamage());
        if (buildingUnderAttack.getHp() <= 0)
            removeDestroyedBuildings(buildingUnderAttack);
    }

    public static void attackEnemyByselectedUnits(Double distance, int xUnderAttack, int yUnderAttack) {
        ArrayList<Unit> enemyUnits = selectedMap.getSquareUnfriendlyUnits(currentGovernment, xUnderAttack, yUnderAttack);
        Unit randomEnemy;
        int randomEnemyIndex;

        for (Unit unit : selectedUnit) {
            if (enemyUnits.size() == 0) break;
            if (unit.getDidFight() || unit.getAttackRange() < (int) Math.floor(distance))
                continue;

            randomEnemyIndex = randomGenerator.nextInt(enemyUnits.size());
            randomEnemy = enemyUnits.get(randomEnemyIndex);

            performFightBetweenTwoUnits(distance, unit, randomEnemy);

            if (randomEnemy.getHitPoint() <= 0) {
                selectedMap.getSquareFromMap(randomEnemy.getXCoordinate(), randomEnemy.getYCoordinate()).removeUnit(randomEnemy);
                enemyUnits.remove(randomEnemyIndex);
            }
        }

        removeDeadSelectedUnits();
    }

    private static void performFightBetweenTwoUnits(Double distance, Unit attacker, Unit deffender) {

        deffender.changeHitPoint(attacker.getDamage());
        if (Math.ceil(deffender.getAttackRange()) >= Math.floor(distance) && !deffender.getDidFight()) {
            attacker.changeHitPoint(deffender.getDamage());
            deffender.setDidFight(true);
        }

        attacker.setDidFight(true);
    }

    private static void removeDeadSelectedUnits() {
        ArrayList<Unit> deadUnits = new ArrayList<>();

        for (Unit unit : selectedUnit)
            if (unit.getHitPoint() <= 0)
                deadUnits.add(unit);

        for (Unit unit : deadUnits) {
            selectedMap.getSquareFromMap(selectedUnit.get(0).getXCoordinate(), selectedUnit.get(0).getYCoordinate()).removeUnit(unit);
            removeUnit(unit);
        }
    }

    private static void removeUnit(Unit unit) {
        for (int i = 0; i < selectedUnit.size(); i++) {
            if (selectedUnit.get(i).equals(unit) && unit.getHitPoint() == selectedUnit.get(i).getHitPoint()){
                selectedUnit.remove(i);
                break;
            }
        }
    }

    public static void generateResourcesForCurrentGovernment() {
        HashMap<String, Integer> generationRates = currentGovernment.getResourceGenerationRates();

        for (String s : generationRates.keySet()) {
            String resourceName = s.toString();
            Integer resourceGenerationRate = generationRates.get(resourceName);

            Resource targetResource = Resource.getResourceByName(resourceName);
            currentGovernment.addToStockpile(targetResource, resourceGenerationRate);
        }
    }

    public static void removeDestroyedBuildings(Building building) {
        //TODO: HANDLE STOCKPILES DELETE EFFECT
        Government owner = building.getOwner();
        String buildingType = Building.getBuildingCategoryByName(building.getName());

        owner.getBuildings().remove(building); //NOTE: removing from arraylist probably isnt going to work
        removeBuildingEffectForPlayer(building, buildingType, owner);
        selectedMap.removeBuildingFromMap(building);
    }

    private static void removeBuildingEffectForPlayer(Building building, String buildingType, Government owner) {
        switch (buildingType) {
            case "Generator":
                Generator deletedGenerator = (Generator) building;
                owner.changePopulation(deletedGenerator.getNumberOfWorker());
                owner.changeFreeWorkers(deletedGenerator.getNumberOfWorker());
                owner.addToGenerationRate(deletedGenerator.getResourceGenerate().getName(), -deletedGenerator.getGeneratingRate());
                break;

            case "TownBuilding":
                TownBuilding deletedTownBuilding = (TownBuilding) building;
                owner.addToMaxPopulation(-deletedTownBuilding.getCapacity());
                owner.updateBuildingPopularity();
                break;

            default:
                break;
        }
    }

    public static boolean isBuildingFriendly(Government owner, Building building) {
        String ownerUsername = owner.getOwner().getUsername();
        if (ownerUsername.equals(building.getOwner().getOwner().getUsername()))
            return true;
        return false;
    }

    public static boolean isUnitFriendly(Government owner, Unit unit) {
        //TODO: ALSO CHECK ALLIES
        return owner.equals(unit.getOwner());
    }

    public static boolean areSelectedUnitsRanged() {
        if (selectedUnit.get(0).getAttackRange() > 1)
            return true;
        else return false;
    }

    public static void handleEndOfTurnFights() {
        for (Unit unit : GameMenuController.getAllUnits()) {
            if (!unit.getDidFight()) {
                int[] targetCoord = selectedMap.getAnEnemyCoordInRange(unit);
                if (targetCoord != null) {
                    selectedUnit.clear();
                    selectedUnit.add(unit);
                    GameMenuController.attackController(Integer.toString(targetCoord[0]), Integer.toString(targetCoord[1]));
                    selectedUnit.clear();
                    unit.setDidFight(true);
                }
            }
        }
    }

    public static int[] getEnemyClosestBuilding(int currentX, int currentY) {
        Government enemy = null;
        int[] nearestCorner = new int[2];
        int buildingCornerX, buildingCornerY;
        double nearestDistance = 1000000, distance;

        for (Government government : governments)
            if (!government.getOwner().getUsername().equals(currentGovernment.getOwner().getUsername()))
                enemy = government;

        for (Building building : enemy.getBuildings()) {
            buildingCornerX = building.getXCoordinateLeft();
            buildingCornerY = building.getYCoordinateUp();
            distance = selectedMap.getDistance(currentX, currentY, buildingCornerX, buildingCornerY);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestCorner[0] = buildingCornerX;
                nearestCorner[1] = buildingCornerY;
            }
        }

        return nearestCorner;
    }
}