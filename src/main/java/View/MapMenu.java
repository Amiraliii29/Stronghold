package View;

import Controller.MapMenuController;
import View.Enums.Commands.MapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static void run(Scanner scanner){
        String input;
        input = Input_Output.getInput();
        Matcher matcher;

        while (true){
            if((matcher = MapMenuCommands.getMatcher(input , MapMenuCommands.SHOW_MAP)) != null)
                showMap(matcher);
        }
    }
    private static void showMap(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt("y");

        String[][] toPrint = MapMenuController.showMapController(x , y);

        System.out.println(toPrint);
    }
    private static void moveMap(Matcher matcher){

    }
    private static void showDetails(Matcher matcher){
    }
    private static void exit(){
    }
}
