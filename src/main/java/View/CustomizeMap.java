package View;

import Controller.CustomizeMapController;
import Controller.Orders;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

import javax.print.attribute.standard.MediaSize;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Date;
import java.util.Scanner;
import java.util.SplittableRandom;
import java.util.regex.Matcher;

public class CustomizeMap {
    public static void run(Scanner scanner){
        String input;
        Matcher matcher;

        while(true){
            input = Input_Output.getInput();
            if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.CREATE_NEW_MAP)) != null)
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
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if((matcher = CustomizeMapCommands.getMatcher(input , CustomizeMapCommands.DROP_UNIT)) != null)
                dropUnit(matcher);
            else{
                System.out.println("invalid command");
            }
        }
    }

    private static void dropUnit(Matcher matcher) {
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders.findFlagOption("-t" , options);
        String count = Orders.findFlagOption("-c" , options);
        String onwerGovernmentNumber = Orders.findFlagOption("-g" , options);

        CustomizeMapMessages message = CustomizeMapController.dropUnitController(x , y , type , count , onwerGovernmentNumber);

        switch (message){
            case INVALID_GOVERNMENT_NUMBER:
                System.out.println("drop unit error: invalid government number");
                break;
            case NO_OWNER_GOVERNMENT_NUMBER:
                System.out.println("drop unit error: please enter owner government number after " +
                        " -g flag next time");
                break;
            case INVALID_NUMBER:
                System.out.println("drop unit error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("drop unit error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("drop unit error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("drop unit error: y out of bounds");
                break;
            case INVALID_COUNT:
                System.out.println("drop unit error: invalid count");
                break;
            case UNSUITABLE_LAND:
                System.out.println("drop unit error: unsuitable land to drop unit");
                break;
            case DROP_UNIT_SUCCESS:
                System.out.println("unit dropped successfully");
                break;
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

        switch (message){
            case INVALID_OPTIONS:
                System.out.println("settexture error: invalid options please enter x and y components");
                break;
            case INVALID_NUMBER:
                System.out.println("settexture error: invalid number");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("settexture error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("settexture error: y out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("settexture error: invalid type");
                break;
            case SET_TEXTURE_SUCCESS:
                System.out.println("settexture success :)");
                break;
        }
    }
    private static void clear(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);

        CustomizeMapMessages message = CustomizeMapController.clearController(x , y);

        switch (message){
            case INVALID_NUMBER:
                System.out.println("clear tile error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("clear tile error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("clear tile error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("clear tile error: y out of bounds");
                break;
            case CLEAR_TILE_SUCCESS:
                System.out.println("tile cleared successfully");
                break;
        }
    }
    private static void dropRock(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String direction = Orders.findFlagOption("-d" , options);

        CustomizeMapMessages message = CustomizeMapController.dropRockController(x , y ,direction);

        switch (message){
            case INVALID_NUMBER:
                System.out.println("drop rock error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("drop rock error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("drop rock error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("drop rock error: y out of bounds");
                break;
            case INVALID_DIRECTION:
                System.out.println("drop rock error: invalid direction");
                break;
            case DROP_ROCK_SUCCESS:
                System.out.println("rock dropped successfully :)");
                break;

        }
    }
    private static void dropTree(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders .findFlagOption("-t" , options);

        CustomizeMapMessages message = CustomizeMapController.dropTreeController(x , y , type);

        switch (message){
            case INVALID_NUMBER:
                System.out.println("drop tree error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("drop tree error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("drop tree error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("drop tree error: y out of bounds");
                break;
            case DROP_TREE_SUCCESS:
                System.out.println("tree dropped successfully");
                break;
            case INVALID_TREE_NAME:
                System.out.println("drop tree error: invalid tree name");
                break;
        }

    }
    private static void dropBuilding(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders.findFlagOption("-t", options);
        String governmentNumber = Orders.findFlagOption("-g" , options);

        CustomizeMapMessages message = CustomizeMapController.dropBuildingController(x , y  , type , governmentNumber);

        switch (message){
            case INVALID_NUMBER:
                System.out.println("drop building error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("drop building error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("drop building error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("drop building error: y out of bounds");
                break;
            case INVALID_BUILDING_NAME:
                System.out.println("drop building error: invalid building name");
                break;
            case DROP_BUILDING_SUCCESS:
                System.out.println("building dropped successfully");
                break;
            case UNSUITABLE_LAND:
                System.out.println("drop building error: can't place there my lord");
                break;
            case INVALID_GOVERNMENT_NUMBER:
                System.out.println("drop building error: invalid government number");
                break;
            case NO_OWNER_GOVERNMENT_NUMBER:
                System.out.println("drop building error: please enter owner government number after " +
                        " -g flag next time");
                break;
        }
    }
    public static void printer(String toPrint){
        System.out.println(toPrint);
    }
}
