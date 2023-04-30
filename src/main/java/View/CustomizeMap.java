package View;

import Controller.CustomizeMapController;
import Controller.Orders;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

import javax.print.attribute.standard.MediaSize;
import javax.swing.plaf.synth.SynthOptionPaneUI;
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
    }
    private static void dropTree(Matcher matcher){
    }
    private static void printer(String toPrint){
        System.out.println(toPrint);
    }
}
