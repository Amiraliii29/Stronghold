package Model;

import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Buildings.Generator;
import Model.Buildings.TownBuilding;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

import Controller.GameMenuController;
import Controller.JsonConverter;
import Model.Units.Troop;
import Model.Units.Unit;
import View.Game;
import View.LoginMenu;
import View.ShopMenu;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class DataBase {
    private static Game game;

    private  static  final Clipboard clipboard;
    private static ArrayList<Government> governments;
    private static final ArrayList<Map> maps;
    private static Government currentGovernment;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Unit> selectedUnit;
    private static SecureRandom randomGenerator = new SecureRandom();
    private static Stage ShopMenuStage = new Stage();
    private static int captchaNumber;
    private static Stage mainStage;
    private static Stage tradeMenuStage = new Stage();


    static {
        clipboard= Clipboard.getSystemClipboard();
        JsonConverter.fillFormerUsersDatabase("src/main/resources/jsonData/Users.json");
        governments = new ArrayList<>();
        maps = new ArrayList<>();
        selectedUnit = new ArrayList<>();
    }


    public static Stage getTradeMenuStage() {
        return tradeMenuStage;
    }

    public static Stage getShopMenuStage() {
        return ShopMenuStage;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static ArrayList<Unit> getSelectedUnit() {
        return selectedUnit;
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static int getCaptchaNumber() {
        return captchaNumber;
    }

    public static Game getGame() {
        return game;
    }

    public static Government getGovernmentByUserName(String userName) {
        for (Government government : governments)
            if (government.getOwner().getUsername().equals(userName))
                return government;

        return null;
    }

    public static String getRandomCaptchaImageAddress() throws MalformedURLException {
        int[] numbers = {1181 , 1381 , 1491 , 1722 , 1959 , 2163 , 8692 , 8776 , 8972 , 8996 , 9061 ,
                9386 , 9582 , 9633};
        Random random = new Random();
        int randomIndex  = random.nextInt(numbers.length - 1);
        captchaNumber = numbers[randomIndex];
        return String.valueOf(new URL(LoginMenu.class.getResource("/Images/Captcha/" + captchaNumber + ".png")
                .toExternalForm()));
    }



    public static void setMainStage(Stage mainStage) {
        DataBase.mainStage = mainStage;
    }

    public static void setSelectedBuilding(Building selectedBuilding) {
        DataBase.selectedBuilding = selectedBuilding;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        DataBase.currentGovernment = currentGovernment;
        selectedUnit = new ArrayList<>();
        selectedBuilding = null;
    }

    public static void setSelectedUnit(ArrayList<Unit> selectedUnit) {
        DataBase.selectedUnit = selectedUnit;
    }

    public static void setGame(Game game) {
        DataBase.game = game;
    }

    public static void newGovernments() {
        governments = new ArrayList<>();
    }



    public static void addGovernment(Government government) {
        governments.add(government);
    }

    public static void addSelectedUnit(Troop selectedUnit) {
        DataBase.selectedUnit.add(selectedUnit);
    }

    public static void addMap(Map map) {
        maps.add(map);
    }



    public static void handleEndOfTurnFights() {
//        for (Unit unit : GameMenuController.getAllUnits()) {
//            GameMenuController.setCurrentGovernment(unit.getOwner());
//            currentGovernment = unit.getOwner();
//            int[] targetCoord = selectedMap.getAnEnemyCoordInRange(unit);
//            if (targetCoord != null) {
//                selectedUnit.clear();
//                selectedUnit.add(unit);
//                GameMenuController.attackController(Integer.toString(targetCoord[0] + 1), Integer.toString(targetCoord[1] + 1));
//                unit.setDidFight(true);
//            }
//        }
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

    public static void attackEnemyBySelectedUnits(Double distance, int xUnderAttack, int yUnderAttack) {
        ArrayList<Unit> enemyUnits = selectedMap.getSquareUnfriendlyUnits(currentGovernment, xUnderAttack, yUnderAttack);
        Unit randomEnemy;
        int randomEnemyIndex;

        for (Unit unit : selectedUnit) {
            if (enemyUnits.size() == 0) break;
//            if (unit.getDidFight() || unit.getAttackRange() < (int) Math.floor(distance))
//                continue;

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

//        if (Math.ceil(deffender.getAttackRange()) >= Math.floor(distance) && !deffender.getDidFight()) {
//            attacker.changeHitPoint(deffender.getDamage());
//            deffender.setDidFight(true);
//        }
    }

    public static boolean isUnitFriendly(Government owner, Unit unit) {
        return owner.equals(unit.getOwner());
    }

    public static boolean isBuildingFriendly(Government owner, Building building) {
        String ownerUsername = owner.getOwner().getUsername();
        if (ownerUsername.equals(building.getOwner().getOwner().getUsername()))
            return true;
        return false;
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

    public static void removeUnit(Unit unit) {
        for (int i = 0; i < selectedUnit.size(); i++) {
            if (selectedUnit.get(i).equals(unit) && unit.getHitPoint() == selectedUnit.get(i).getHitPoint()){
                selectedUnit.remove(i);
                break;
            }
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

    public static boolean areSelectedUnitsRanged() {
        if (selectedUnit.get(0).getAttackRange() > 1)
            return true;
        else return false;
    }

    public static void handleEndOfTurnFights() {
//        for (Unit unit : GameMenuController.getAllUnits()) {
//            GameMenuController.setCurrentGovernment(unit.getOwner());
//            currentGovernment = unit.getOwner();
//            int[] targetCoord = selectedMap.getAnEnemyCoordInRange(unit);
//            if (targetCoord != null) {
//                selectedUnit.clear();
//                selectedUnit.add(unit);
//                GameMenuController.attackController(Integer.toString(targetCoord[0] + 1), Integer.toString(targetCoord[1] + 1));
//                unit.setDidFight(true);
//            }
//        }
    }
    public static void copyStringToClipboard(String targetString){
        ClipboardContent content=new ClipboardContent();
        content.putString(targetString);
        clipboard.setContent(content);
    }

    public static String readClipboardString() {
        return clipboard.getString();
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

    public static void generateResourcesForCurrentGovernment() {
        HashMap<String, Integer> generationRates = currentGovernment.getResourceGenerationRates();

        for (String s : generationRates.keySet()) {
            if (s.equals("Wood")) {
                Square[][] squares = selectedMap.getSquares();
                int changeAmount = generationRates.get(s);
                outer:
                for (int i = 0; i < selectedMap.getLength(); i++) {
                    for (int j = 0; j < selectedMap.getWidth(); j++) {
                        if (changeAmount < 0)
                            break outer;
                        if (changeAmount > squares[i][j].getTreeAmount()) {
                            squares[i][j].changeTreeAmount(squares[i][j].getTreeAmount());
                            currentGovernment.addToStockpile(Objects.requireNonNull(Resource.createResource("Wood")), squares[i][j].getTreeAmount());
                            changeAmount -= squares[i][j].getTreeAmount();
                        } else {
                            squares[i][j].changeTreeAmount(changeAmount);
                            currentGovernment.addToStockpile(Objects.requireNonNull(Resource.createResource("Wood")), changeAmount);
                            changeAmount = 0;
                        }
                    }
                }
            } else {
                Integer resourceGenerationRate = generationRates.get(s);
                Resource targetResource = Resource.getResourceByName(s);
                assert targetResource != null;
                currentGovernment.addToStockpile(targetResource, resourceGenerationRate);
            }
        }
    }

    public static HashMap<String , ArrayList<Unit>> getSelectedUnitsByType(){

        HashMap<String , ArrayList<Unit>> unitsByType=new HashMap<>();
        boolean isHandled;

        for (Unit unit : selectedUnit) {

            isHandled=false;
            for (String unitName : unitsByType.keySet())
                if(unit.getName().equals(unitName)){
                    unitsByType.get(unitName).add(unit);
                    isHandled=true;
                    break;
                }

            if(isHandled) continue;

            ArrayList<Unit> newTypeList=new ArrayList<>();
            newTypeList.add(unit);
            unitsByType.put(unit.getName(), newTypeList);
        }

        return unitsByType;
    }

    public static boolean isSelectedUnitOnTargetSquare(int squareX, int squareY){
        if(selectedUnit.get(0).getXCoordinate()==squareX
            && selectedUnit.get(0).getYCoordinate()==squareY) return true;
        return false;
    }



}