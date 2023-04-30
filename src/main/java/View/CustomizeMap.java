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

        CustomizeMapController.selectMapController(mapName);

    }
    private static void setTexture(Matcher matcher){
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders.findFlagOption("-t" , options);

        CustomizeMapMessages message = CustomizeMapController.setTextureController(x , y , type);

        switch (message){
            case NO_X:
                System.out.println("settexture error: invalid x amount");
                break;
            case NO_Y:
                System.out.println("settexture error: invalid y amount");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("settexture error: x amount out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("settexture error: y amount out of bounds");
                break;
        }
    }
    private static void clear(Matcher matcher){
    }
    private static void dropRock(Matcher matcher){
    }
    private static void dropTree(Matcher matcher){
    }
    private static void printer(String toPrint){
        System.out.println(toPrint);
    }
}
