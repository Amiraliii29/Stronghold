package Controller;

import Model.DataBase;
import Model.Square;
import Model.Units.Unit;
import View.Enums.Commands.ShowMapMenuCommands;

import java.util.HashMap;

public class ShowMapMenuController {
    private static int xLocationOnMap;
    private static int yLocationOnMap;
    public static String[][] showMapController(String x , String  y){
        String[][] mapToShow = new String[20][20];

        if(x == null || y == null){
            mapToShow[0][0] = "show map error: please enter both x and y components of location you want to see";
            return mapToShow;
        }
        else if(ShowMapMenuCommands.getMatcher(x , ShowMapMenuCommands.VALID_NUMBER) == null ||
        ShowMapMenuCommands.getMatcher(y , ShowMapMenuCommands.VALID_NUMBER) == null){
            mapToShow[0][0] = "show map error: please enter number for x and y";
            return mapToShow;
        }

        xLocationOnMap = Integer.parseInt(x);
        yLocationOnMap = Integer.parseInt(y);


        if(xLocationOnMap > DataBase.getSelectedMap().getWidth() || xLocationOnMap <= 0)
             mapToShow[0][0] = "show map error: invalid x amount\n";
        else if(yLocationOnMap > DataBase.getSelectedMap().getLength() || yLocationOnMap <= 0)
            mapToShow[0][0] = "show map error: invalid y amount\n";
        else{
            for (int i = -10 ; i < 10 ; i++){
                for (int j = -10 ; j < 10 ; j++){
                    if(xLocationOnMap+i < 0  || yLocationOnMap + j < 0 || xLocationOnMap + i > DataBase.getSelectedMap().getLength()
                            || yLocationOnMap + j > DataBase.getSelectedMap().getWidth() )
                        mapToShow[j+10][i+10] = "0 |";
                    else if(DataBase.getSelectedMap().getSquareFromMap( yLocationOnMap + j, xLocationOnMap + i ).getUnits().size() != 0)
                        mapToShow[j+10][i+10] = "S|";
                    else if(DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + j , xLocationOnMap + i).getBuilding() != null)
                        mapToShow[j+10][i+10] = "B|";
                    else if(DataBase.getSelectedMap().getSquareFromMap( yLocationOnMap + j , xLocationOnMap + i).getResource() != null){
                        char[] resourceName = DataBase.getSelectedMap().getSquareFromMap(yLocationOnMap + i , xLocationOnMap+j).
                                getResource().getName().toCharArray();
                        mapToShow[j+10][i+10] = resourceName[0] + resourceName[2] + "|";
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
                yLocationOnMap += amountInt;
                break;
            case "down":
                yLocationOnMap -= amountInt;
                break;
            case "right":
                xLocationOnMap += amountInt;
                break;
            case "left":
                xLocationOnMap -= amountInt;
                break;
        }
        switch (direction2){
            case "up":
                yLocationOnMap += amountInt;
                break;
            case "down":
                yLocationOnMap -= amountInt;
                break;
            case "right":
                xLocationOnMap += amountInt;
                break;
            case "left":
                xLocationOnMap -= amountInt;
                break;
        }
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
            Square square = DataBase.getSelectedMap().getSquareFromMap(xInt , yInt);
            toReturn += "Land type: " + square.getLand() + "\nresource: " + square.getResource().getName()
                    + "\nTroops: ";
            HashMap<Unit,  Integer> unitsTypeAndCount = square.getUnitsTypeAndCount();
            for (Unit unit : unitsTypeAndCount.keySet()) {
                toReturn += unit.getName() + " (" + unitsTypeAndCount.get(unit) + ")\n";
            }
            toReturn += "building: " + square.getBuilding().getName();
            return toReturn;
    }
}

