package Controller;

        return null;
import Model.DataBase;
import Model.Land;
import Model.Map;
import Model.Square;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

import javax.xml.crypto.Data;

public class CustomizeMapController {
    public static CustomizeMapMessages createNewMapController(String mapName , String length , String width) {
        if (mapName == null)
            return CustomizeMapMessages.NO_NAME;
        else if (length == null)
            return CustomizeMapMessages.NO_LENGTH;
        else if (width == null)
            return CustomizeMapMessages.NO_WIDTH;

        int widthInt = Integer.parseInt(width);
        int lengthInt = Integer.parseInt(length);

        if(CustomizeMapCommands.getMatcher(width , CustomizeMapCommands.VALID_NUMBER) == null || widthInt < 0)
            return CustomizeMapMessages.INVALID_WIDTH;
        else if(CustomizeMapCommands.getMatcher(length , CustomizeMapCommands.VALID_NUMBER) == null || lengthInt < 0)
            return CustomizeMapMessages.INVALID_LENGTH;

        else
        {
            Map map = new Map(mapName , widthInt , lengthInt);
            Map.saveMap(map , mapName);
            return CustomizeMapMessages.CREATE_NEW_MAP_SUCCESS;
        }
    }

    public static CustomizeMapMessages selectMapController(String mapName){
        Map.loadMap(mapName);
        if(DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.MAP_NOT_FOUND;

        else
            return CustomizeMapMessages.SELECT_MAP_SUCCESS;
    }
    public static CustomizeMapMessages setTextureController(String x , String y , String x1 , String y1 ,
                                                            String x2 , String y2 , String type){
        if(x != null && y != null){
            if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
            CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
                return CustomizeMapMessages.INVALID_NUMBER;

            int xInt = Integer.parseInt(x);
            int yInt = Integer.parseInt(y);

            if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
                return CustomizeMapMessages.X_OUT_OF_BOUNDS;
            else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
                return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

            Land land = Land.getLandByName(type);

            if(land == null)
                return CustomizeMapMessages.INVALID_TYPE;

            DataBase.getSelectedMap().getSquareFromMap(xInt , yInt).setLand(land);

            return CustomizeMapMessages.SET_TEXTURE_SUCCESS;

        }
        else if(x1 != null && x2 != null && y1 != null && y2 != null){
            if(CustomizeMapCommands.getMatcher(x1 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher(x2 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher(y1 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher( y2 , CustomizeMapCommands.VALID_NUMBER) == null)
                return CustomizeMapMessages.INVALID_NUMBER;

            int x1Int = Integer.parseInt(x1);
            int y1Int = Integer.parseInt(y1);
            int x2Int = Integer.parseInt(x2);
            int y2Int = Integer.parseInt(y2);

            if(x1Int <= 0 || x1Int > DataBase.getSelectedMap().getLength() ||x2Int <= 0
                    || x2Int > DataBase.getSelectedMap().getLength())
                return CustomizeMapMessages.X_OUT_OF_BOUNDS;
            else if(y1Int <= 0 || y1Int > DataBase.getSelectedMap().getWidth() || y2Int <= 0 ||
                    y2Int > DataBase.getSelectedMap().getWidth())
                return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

            Land land = Land.getLandByName(type);

            if(land == null)
                return CustomizeMapMessages.INVALID_TYPE;

            for (int j = y1Int - 1 ; j >= y2Int - 1 ; j--){
                for (int i = x1Int - 1 ; i <= x2Int - 1 ; i++){
                    DataBase.getSelectedMap().getSquareFromMap(j , i).setLand(land);
                }
            }
            return CustomizeMapMessages.SET_TEXTURE_SUCCESS;
        }
        else{
            return CustomizeMapMessages.INVALID_OPTIONS;
        }
    }
    public static CustomizeMapMessages clearController(String x , String y){
        if(x == null || y  == null)
            return CustomizeMapMessages.INVALID_OPTIONS;
        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);

        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

        else {
            Square selectedSquare = DataBase.getSelectedMap().getSquareFromMap(yInt - 1 , xInt - 1);
            selectedSquare.setLand(Land.DEFAULT);
            selectedSquare.setBuilding(null);
            selectedSquare.removeAllTroops();
            return CustomizeMapMessages.CLEAR_TILE_SUCCESS;

        }
    }
    public static CustomizeMapMessages dropRockController(String x , String y , String direction){
        return null;
    }
    public static CustomizeMapMessages dropTreeController(String x , String y , String type){
        return null;
    }
}
