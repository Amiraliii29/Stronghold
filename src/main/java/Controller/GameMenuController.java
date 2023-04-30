package Controller;

import Model.Map;
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
        return null;
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

}
