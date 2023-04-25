package Controller;

import Model.DataBase;
import View.Enums.Messages.MapMenuMessages;

public class MapMenuController {
    public static String[][] showMapController(int x , int y){

        String[][] mapToShow = new String[20][20];

        if(x > DataBase.getSelectedMap().getWidth() || x <= 0)
             mapToShow[0][0] = "show map error: invalid x amount\n";
        else if(y > DataBase.getSelectedMap().getLength() || y <= 0)
            mapToShow[0][0] = "show map error: invalid y amount\n";
        else{
            for (int i = -10 ; i < 10 ; i++){
                for (int j = -10 ; j < 10 ; j++){
                    if(DataBase.getSelectedMap().getSquareFromMap(x+i , y+j).getTroops().size() != 0)
                        mapToShow[i+10][j+10] = "S|";
                    else if(DataBase.getSelectedMap().getSquareFromMap(x+i , y+j).getBuilding() != null)
                        mapToShow[i+10][j+10] = "B|";
                    else if(DataBase.getSelectedMap().getSquareFromMap(x+i , y+j).getResource() != null){
                        char[] resourceName = DataBase.getSelectedMap().getSquareFromMap(x+i , y+j).
                                getResource().getName().toCharArray();
                        mapToShow[i+10][j+10] = String.valueOf(resourceName[0]) + String.valueOf(resourceName[2]) + "|";
                    }
                }
            }
        }
        return mapToShow;
    }
    public static String moveMapController(String direction , String amount){
    }
    public static String showDetailsController(int x, int y){
    }
}

