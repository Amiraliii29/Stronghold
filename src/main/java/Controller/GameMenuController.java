package Controller;

import java.util.ArrayList;

import Model.Map;
import Model.Square;
import Model.Buildings.*;
import View.Enums.Messages.GameMenuMessages;

public class GameMenuController {

    private static Map currentMap;

    public static GameMenuMessages nextTurnController(){
        return null;
    }
    public static GameMenuMessages userLogout(){
        return null;
    }
    public static GameMenuMessages dropBuildingController(String x , String y , String type){
        String buildingCategory= getBuildingCategoryByName(type);
    }
    public  static GameMenuMessages selectBuildingController(String x , String y){
        return null;
    }
    public static GameMenuMessages createUnitController(String type , String count){
        return null;
    }
    public static GameMenuMessages repairController(){
        return null;
    }
    public static GameMenuMessages selectUnitController(String x , String y){
        return null;
    }
    public static GameMenuMessages moveUnitController(String x , String y){
        return null;
    }
    public static GameMenuMessages setUnitModeController(String x , String y , String mode){
        return null;
    }
    public static GameMenuMessages attackController(String enemy){
        return null;
    }
    public static GameMenuMessages attackController(String x , String y){
        return null;
    }
    public static GameMenuMessages pourOilController(String direction){
        return null;
    }
    public static GameMenuMessages digTunnelController(String x , String y){
        return null;
    }
    public static GameMenuMessages buildEquipmentController(String EquipmentName){
        return null;
    }
    public static GameMenuMessages disbandUnitController(){
        return null;
    }
    public static GameMenuMessages dropBuildingController(String x , String y , String type , String count){
        return null;
    }
    public static GameMenuMessages showMapController(String x , String y){
        return null;
    }

    public static void setCurrentMap(Map map){
        currentMap=map;
    }



    private static String getBuildingCategoryByName(String buildingName){
        for (String name : Generator.getGeneratorsName()) {
            if(buildingName.equals(name))
                return "Generator";
        }
        //TODO: FILL THE REST

        return null;
    }

    private static ArrayList<String> getBuildingValidLandsByName(String buildingName, String buildingType){
        switch (buildingType) {
            case "Generator":
                Generator
                break;
        
            default:
                break;
        }
    }
}
