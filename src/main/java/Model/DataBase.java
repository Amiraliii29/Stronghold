package Model;

import Model.Buildings.Building;
import Model.Resources.Resource;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Controller.JsonConverter;
import Model.Units.Troop;
import Model.Units.Unit;

public class DataBase {
    private static final ArrayList<Government> governments;
    private static final ArrayList<Map> maps;
    private static Government currentGovernment;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Unit> selectedUnit;
    private static SecureRandom randomGenerator=new SecureRandom();


    static {
        JsonConverter.fillFormerUsersDatabase("src/main/java/jsonData/Users.json");
        governments = new ArrayList<>();
        maps = new ArrayList<>();
        selectedUnit = new ArrayList<>();
    }



    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        DataBase.currentGovernment = currentGovernment;
    }

    public static Government getGovernmentByUserName(String userName) {
        for (Government government:governments)
            if (government.getOwner().getUsername().equals(userName))
                return government;

        return null;
    }


    public static void addGovernment(Government government) {
        governments.add(government);
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
    
    public static void attackEnemyByselectedUnits(Double distance,int xUnderAttack,int yUnderAttack){
        ArrayList<Unit> enemyUnits= selectedMap.getSquareFromMap(xUnderAttack, yUnderAttack).getUnits();
         Unit randomEnemy;
         int randomEnemyIndex;
 
        for (Unit unit : selectedUnit) {
 
         randomEnemyIndex=randomGenerator.nextInt(enemyUnits.size());
         randomEnemy=enemyUnits.get(randomEnemyIndex);
 
         performFightBetweenTwoUnits(distance, unit, randomEnemy);
 
         if(randomEnemy.getHitPoint()<=0)
             enemyUnits.remove(randomEnemyIndex);
        }
 
        removeDeadSelectedUnits();
     }
 
    private static void performFightBetweenTwoUnits(Double distance, Unit attacker, Unit deffender){
         deffender.changeHitPoint(attacker.getDamage());
         
         if(deffender.getAttackRange()>distance)
             attacker.changeHitPoint(deffender.getDamage());
         //TODO: APPLIED DAMAGES SHOULD BE AFFECTED BY GOVERNMENT FEAR AND POPULARITY
    }
 
    private static void removeDeadSelectedUnits(){
         ArrayList<Unit> deadUnits=new ArrayList<Unit>();
 
         for (Unit unit : selectedUnit) 
             if(unit.getHitPoint()<=0)
                 deadUnits.add(unit);
         
         for (Unit deadUnit : deadUnits) 
             selectedUnit.remove(deadUnits);
    }
 
    private static void generateResourcesForCurrentGovernment(){
         HashMap<String, Integer> generationRates=currentGovernment.getResourceGenerationRates();
         Iterator keySetIterator =  generationRates.keySet().iterator();
         
         while (keySetIterator.hasNext()) {
             String resourceName = keySetIterator.next().toString();
             Integer resourceGenerationRate=generationRates.get(resourceName);
 
             Resource targetResource=Resource.getResourceByName(resourceName);
             currentGovernment.addToStockpile(targetResource, resourceGenerationRate);
         }
    }

    private static void generatePopulationForCurrentGovernment(){
        currentGovernment.changeFreeWorkers(currentGovernment.getWorkerRate());
    }

}