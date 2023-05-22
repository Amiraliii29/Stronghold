package Controller;

import Model.DataBase;
import Model.Square;
import Model.Units.Unit;
import View.Enums.Commands.ShowMapMenuCommands;
import View.Input_Output;

import java.util.HashMap;

public class ShowMapMenuController {
    public static int xLocationOnMap;
    public static int yLocationOnMap;
    public static int errorInShowMapFlag = 0;
    public static String[][] showMapController(String x , String  y){
        String[][] mapToShow = new String[20][20];
        errorInShowMapFlag = 0;

        if(x == null || y == null){
            mapToShow[0][0] = "show map error: please enter both x and y components of location you want to see";
            errorInShowMapFlag = 1;
            return mapToShow;
        }
        else if(ShowMapMenuCommands.getMatcher(x , ShowMapMenuCommands.VALID_NUMBER) == null ||
        ShowMapMenuCommands.getMatcher(y , ShowMapMenuCommands.VALID_NUMBER) == null){
            mapToShow[0][0] = "show map error: please enter number for x and y";
            errorInShowMapFlag = 1;
            return mapToShow;
        }

        xLocationOnMap = Integer.parseInt(x);
        yLocationOnMap = Integer.parseInt(y);


        if(xLocationOnMap > DataBase.getSelectedMap().getLength() || xLocationOnMap <= 0) {
            mapToShow[0][0] = "show map error: invalid x amount\n";
            errorInShowMapFlag = 1;
            return mapToShow;
        }
        else if(yLocationOnMap > DataBase.getSelectedMap().getWidth() || yLocationOnMap <= 0) {
            mapToShow[0][0] = "show map error: invalid y amount\n";
            errorInShowMapFlag = 1;
            return mapToShow;
        }
        else{
            for (int i = -10 ; i < 10 ; i++){
                for (int j = -10 ; j < 10 ; j++){
                    if(xLocationOnMap + j <= 0  || yLocationOnMap + i <= 0 || xLocationOnMap + j > DataBase.getSelectedMap().getLength()
                            || yLocationOnMap + i > DataBase.getSelectedMap().getWidth() )
                        mapToShow[i+10][j+10] = null;
                    else if(DataBase.getSelectedMap().getSquareFromMap( yLocationOnMap + i, xLocationOnMap + j).getUnits().size() != 0)
                        mapToShow[i+10][j+10] = " SS |";
                    else if(DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + i, xLocationOnMap + j).getBuilding() != null)
                        mapToShow[i+10][j+10] = " BB |";
                    else if(DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + i, xLocationOnMap + j).getTree() != null)
                        mapToShow[i+10][j+10] = " TT | ";
                    else if(DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + i, xLocationOnMap + j).getLand() != null){
                        char[] landName = DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + i, xLocationOnMap + j).
                                getLand().name().toCharArray();
                        mapToShow[i+10][j+10] = " " + String.valueOf(landName[0])+ String.valueOf(landName[2]) + " |" ;
                    }
                }
            }
        }
        return mapToShow;
    }
    public static String[][] moveMapController(String direction ,String direction2,  String amount){
        int amountInt;
        if(amount == null)
            amountInt = 1;
        else
            amountInt = Integer.parseInt(amount);

        switch (direction){
            case "up":
                yLocationOnMap -= amountInt;
                break;
            case "down":
                yLocationOnMap += amountInt;
                break;
            case "right":
                xLocationOnMap += amountInt;
                break;
            case "left":
                xLocationOnMap -= amountInt;
                break;
        }
        if(direction2 != null) {
            switch (direction2) {
                case "up":
                    yLocationOnMap -= amountInt;
                    break;
                case "down":
                    yLocationOnMap += amountInt;
                    break;
                case "right":
                    xLocationOnMap += amountInt;
                    break;
                case "left":
                    xLocationOnMap -= amountInt;
                    break;
            }
        }
        if (xLocationOnMap <= 0) xLocationOnMap = 1;
        if (yLocationOnMap <= 0) yLocationOnMap = 1;
        //2System.out.println("******x: " + xLocationOnMap + " ** y: " +  yLocationOnMap);
        return showMapController(Integer.toString(xLocationOnMap) , Integer.toString(yLocationOnMap));
    }
    public static String showDetailsController(String  x, String y){
        String toReturn = "";
        if(x == null)
            return "show map details error: please enter x next time\n";
        else if(y == null)
            return "show map details error: please enter y next time\n";

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);
        if (xInt <= 0 || yInt <= 0 || xInt > DataBase.getSelectedMap().getLength() || yInt > DataBase.getSelectedMap().getWidth())
            return "wrong coordinate";
        Square square = DataBase.getSelectedMap().getSquareFromMap(xInt, yInt);

        toReturn += "Land type: \n" + square.getLand() + "\n------------------\n";
        if (square.getBuilding() != null) toReturn += "building: \n" + square.getBuilding().getName() + "\n------------------\n";
        if(square.getTree() != null) toReturn += "Trees: \n" + square.getTree().name() + "\n------------------\n";
        toReturn += "Units : \n";
        HashMap<String,  Integer> unitsTypeAndCount = square.getUnitsTypeAndCount();
        for (String unitName : unitsTypeAndCount.keySet()) {
            toReturn += unitName + " (" + unitsTypeAndCount.get(unitName) + ")\n";
        }
        return toReturn;
    }
}
