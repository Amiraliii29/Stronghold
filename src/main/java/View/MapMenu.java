package View;

import Controller.MapMenuController;
import Controller.Orders;
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
            else if((matcher = MapMenuCommands.getMatcher(input , MapMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if((matcher = MapMenuCommands.getMatcher(input , MapMenuCommands.SHOW_DETAILS)) != null)
                showMap(matcher);
            else if(MapMenuCommands.getMatcher(input , MapMenuCommands.EXIT) != null)
                exit(scanner);
        }
    }
    private static void showMap(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt("y");

        String[][] toPrint = MapMenuController.showMapController(x , y);

        for (int i = 0 ; i < 20 ; i++){
            for (int j = 0 ; j < 20 ; j++){
                if(toPrint == null)
                    break;
                System.out.println(toPrint[i][j]);
            }
        }
    }
    private static void moveMap(Matcher matcher){
        String direction = matcher.group("direction");
        String direction2 = matcher.group("direction2");
        String amount = matcher.group("amount");

        String[][] toPrint = MapMenuController.moveMapController(direction , direction2 , amount);

        for (int i = 0 ; i < 20 ; i++){
            for (int j = 0 ; j < 20 ; j++){
                if(toPrint == null)
                    break;
                System.out.println(toPrint[i][j]);
            }
        }
    }
    private static void showDetails(Matcher matcher){
        String x = Orders.findFlagOption("-x" , matcher.group("options"));
        String y = Orders.findFlagOption("-y" , matcher.group("options"));

        String toPrint = MapMenuController.showDetailsController(x , y);

        System.out.print(toPrint);
    }
    private static void exit(Scanner scanner){
        System.out.println("returned back to main menu");
        GameMenu runner = new GameMenu();
        runner.run(scanner);
    }
}
