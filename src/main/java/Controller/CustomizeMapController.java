package Controller;


import Model.DataBase;
import Model.Map;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

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
    public static CustomizeMapMessages setTextureController(String x , String y , String type){
        if(x == null || CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.NO_X;
        else if(y == null || CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.NO_Y;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);

        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;
    }
    public static CustomizeMapMessages clearController(String x , String y){
        return null;
    }
    public static CustomizeMapMessages dropRockController(String x , String y , String direction){
        return null;
    }
    public static CustomizeMapMessages dropTreeController(String x , String y , String type){
        return null;
    }
}
