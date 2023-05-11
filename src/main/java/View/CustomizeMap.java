package View;

import Controller.CustomizeMapController;
import Controller.Orders;
import Model.DataBase;
import Model.Map;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

import java.util.regex.Matcher;

public class CustomizeMap {
    public static void run(){
        String input;
        Matcher matcher;
        Input_Output.outPut("MAP MENU:");

        while(true){
            input = Input_Output.getInput();
            if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.BACK) != null) {
                if (DataBase.getSelectedMap() != null)
                    Map.saveMap(DataBase.getSelectedMap(), DataBase.getSelectedMap().getName());
                Input_Output.outPut("out of customize menu");
                break;
            }
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.CREATE_NEW_MAP)) != null)
                createNewMap(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.SELECT_MAP)) != null)
                selectMap(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.SET_TEXTURE)) != null)
                setTexture(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.CLEAR)) != null)
                clear(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.DROP_TREE)) != null)
                dropTree(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.DROP_CLIFF)) != null)
                dropRock(matcher);
            else if(CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.SHOW_MAP) != null)
                showMap();
            else{
                System.out.println("invalid command");
            }
        }
    }

    private static void createNewMap(Matcher matcher){
        String options = matcher.group("options");
        String name = Orders.findFlagOption("-n" , options);
        String width = Orders.findFlagOption("-w" , options);
        String length = Orders.findFlagOption("-l" , options);
        CustomizeMapMessages message = CustomizeMapController.createNewMapController(name , length , width);

        switch (message){
            case NO_NAME:
                System.out.println("create new map error: please enter map name after flag -n");
                break;
            case NO_LENGTH:
                System.out.println("create new map error: please enter map length after flag -l");
                break;
            case NO_WIDTH:
                System.out.println("create new map error: please enter map width after flag -w");
                break;
            case INVALID_LENGTH:
                System.out.println("create new map error: invalid length");
                break;
            case INVALID_WIDTH:
                System.out.println("create new map error: invalid width");
                break;
            case CREATE_NEW_MAP_SUCCESS:
                System.out.println("new map created successfully");
                break;
            case INVALID_NUMBER:
                System.out.println("create new map error: invalid governments count");
                break;
        }
    }

    private static void selectMap(Matcher matcher){
        String mapName = matcher.group("mapName");
        CustomizeMapMessages message =  CustomizeMapController.selectMapController(mapName);
        switch (message){
            case SELECT_MAP_SUCCESS:
                System.out.println("map selected successfully");
                break;
            case MAP_NOT_FOUND:
                System.out.println("select map error: map with this name doesn't exist");
                break;
        }
    }

    private static void setTexture(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders.findFlagOption("-t" , options);
        String x1 = Orders.findFlagOption("-x1" , options);
        String y1 = Orders.findFlagOption("-y1" , options);
        String x2 = Orders.findFlagOption("-x2" , options);
        String y2 = Orders.findFlagOption("-y2" , options);

        CustomizeMapMessages message = CustomizeMapController.setTextureController(x , y , x1 , y1 , x2 , y2 , type);

        switch (message) {
            case INVALID_OPTIONS ->
                    System.out.println("set texture error: invalid options please enter x and y components");
            case INVALID_NUMBER -> System.out.println("set texture error: invalid number");
            case X_OUT_OF_BOUNDS -> System.out.println("set texture error: x out of bounds");
            case Y_OUT_OF_BOUNDS -> System.out.println("set texture error: y out of bounds");
            case INVALID_TYPE -> System.out.println("set texture error: invalid type");
            case SET_TEXTURE_SUCCESS -> System.out.println("set texture success :)");
            case NO_MAP_SELECTED -> System.out.println("set texture error: please first select your map");
        }
    }

    private static void clear(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        CustomizeMapMessages message = CustomizeMapController.clearController(x , y);

        switch (message) {
            case INVALID_NUMBER -> System.out.println("clear tile error: invalid number");
            case INVALID_OPTIONS -> System.out.println("clear tile error: please enter x and y component");
            case X_OUT_OF_BOUNDS -> System.out.println("clear tile error: x out of bounds");
            case Y_OUT_OF_BOUNDS -> System.out.println("clear tile error: y out of bounds");
            case CLEAR_TILE_SUCCESS -> System.out.println("tile cleared successfully");
            case NO_MAP_SELECTED -> System.out.println("clear tile error: please first select your map");
        }
    }

    private static void dropRock(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String direction = Orders.findFlagOption("-d" , options);

        CustomizeMapMessages message = CustomizeMapController.dropRockController(x , y ,direction);

        switch (message) {
            case INVALID_NUMBER -> System.out.println("drop rock error: invalid number");
            case INVALID_OPTIONS -> System.out.println("drop rock error: please enter x and y component");
            case X_OUT_OF_BOUNDS -> System.out.println("drop rock error: x out of bounds");
            case Y_OUT_OF_BOUNDS -> System.out.println("drop rock error: y out of bounds");
            case INVALID_DIRECTION -> System.out.println("drop rock error: invalid direction");
            case DROP_ROCK_SUCCESS -> System.out.println("rock dropped successfully :)");
            case NO_MAP_SELECTED -> System.out.println("drop rock error: please first select your map");
        }
    }

    private static void dropTree(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders .findFlagOption("-t" , options);

        CustomizeMapMessages message = CustomizeMapController.dropTreeController(x , y , type);

        switch (message) {
            case INVALID_NUMBER -> System.out.println("drop tree error: invalid number");
            case INVALID_OPTIONS -> System.out.println("drop tree error: please enter x and y component");
            case X_OUT_OF_BOUNDS -> System.out.println("drop tree error: x out of bounds");
            case Y_OUT_OF_BOUNDS -> System.out.println("drop tree error: y out of bounds");
            case DROP_TREE_SUCCESS -> System.out.println("tree dropped successfully");
            case INVALID_TREE_NAME -> System.out.println("drop tree error: invalid tree name");
            case NO_MAP_SELECTED -> System.out.println("drop tree error: please first select your map");
        }

    }

    public static void printer(String toPrint){
        System.out.println(toPrint);
    }

    private static void showMap() {
        if (DataBase.getSelectedMap() == null) Input_Output.outPut("select map first");
        else ShowMapMenu.run();
    }
}
