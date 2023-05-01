package Controller;

import Model.DataBase;
import Model.Square;
import Model.Units.Troop;
import Model.Units.Unit;

import java.util.HashMap;

public class MapMenuController {
    private static int xLocationOnMap;
    private static int yLocationOnMap;
    public static String[][] showMapController(int x , int y){
        xLocationOnMap = x;
        yLocationOnMap = y;

        String[][] mapToShow = new String[20][20];

        if(x > DataBase.getSelectedMap().getWidth() || x <= 0)
             mapToShow[0][0] = "show map error: invalid x amount\n";
        else if(y > DataBase.getSelectedMap().getLength() || y <= 0)
            mapToShow[0][0] = "show map error: invalid y amount\n";
        else{
            for (int i = -10 ; i < 10 ; i++){
                for (int j = -10 ; j < 10 ; j++){
                    if(x+i < 0  || y + j < 0 || x + i > DataBase.getSelectedMap().getLength()
                            || y + j > DataBase.getSelectedMap().getWidth() )
                        mapToShow[j+10][i+10] = "0 |";
                    else if(DataBase.getSelectedMap().getSquareFromMap( y + j, x+i ).getUnits().size() != 0)
                        mapToShow[j+10][i+10] = "S|";
                    else if(DataBase.getSelectedMap().getSquareFromMap(y+j , x + i).getBuilding() != null)
                        mapToShow[j+10][i+10] = "B|";
                    else if(DataBase.getSelectedMap().getSquareFromMap( y+j , x + i).getResource() != null){
                        char[] resourceName = DataBase.getSelectedMap().getSquareFromMap(x+i , y+j).
                                getResource().getName().toCharArray();
                        mapToShow[j+10][i+10] = String.valueOf(resourceName[0]) + String.valueOf(resourceName[2]) + "|";
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
        return showMapController(xLocationOnMap , yLocationOnMap);
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

