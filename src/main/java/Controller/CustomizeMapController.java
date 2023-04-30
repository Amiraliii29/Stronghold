package Controller;


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


    }

    public static CustomizeMapMessages selectMapController(String mapName){
        return null;
    }
    public static CustomizeMapMessages setTextureController(String x1 , String y1 ,
                                                  String x2 , String y2 , String type){
        return null;
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
