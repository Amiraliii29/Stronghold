package View;

import Controller.CustomizeMapController;
import Controller.Orders;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Scanner;
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
        }
    }
    private static void selectMap(Matcher matcher){

    }
    private static void setTexture(Matcher matcher){
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
