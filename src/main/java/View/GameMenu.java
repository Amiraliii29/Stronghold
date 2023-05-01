package View;

import Controller.GameMenuController;
import View.Enums.Commands.GameMenuCommands;
import View.Enums.Messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    public static void run(Scanner scanner) {
        String input;
        Matcher matcher;
        while (true) {
            input = Input_Output.getInput();
            if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if (((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.PATRON_UNIT)) != null))
                patrol(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SET_STATE)) != null)
                setUnitMode(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ATTACK_GROUND)) != null)
                attackGround(matcher);
            else if (((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ATTACK_AIR)) != null))
                attackAir(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.POUR_OIL)) != null)
                pourOil(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.BUILD_SIEGE)) != null)
                buildEquipment(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DISBAND) != null)
                disbandUnit();
        }
    }

    private static void nextTurn() {
    }

    private static void userLogout() {
    }

    private static void dropBuilding(Matcher matcher) {
    }

    private static void selectBuilding(Matcher matcher) {
    }

    private static void createUnit(Matcher matcher) {
    }

    private static void repair(Matcher matcher) {
    }

    private static void selectUnit(Matcher matcher) {
        String option = matcher.group("option");
        GameMenuMessages message = GameMenuController.selectUnitController(option);
        switch (message) {
            case SUCCESS -> Input_Output.outPut("unit moved");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate!");
            case INVALID_TROOP_TYPE -> Input_Output.outPut("we don't have a unit with this name");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("coordinate is invalid!");
            case THERE_IS_NO_UNIT -> Input_Output.outPut("there is no unit with this name here");
        }
    }

    private static void moveUnit(Matcher matcher) {
        String coordinate = matcher.group("coordinate");
        GameMenuMessages message = GameMenuController.moveUnitController(coordinate);
        switch (message) {
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("wrong format for coordinate");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate");
            case CANT_GO_THERE -> Input_Output.outPut("cant go there");
            case SUCCESS -> Input_Output.outPut("units moved successfully");
        }
    }

    private static void patrol(Matcher matcher) {
    }

    private static void setUnitMode(Matcher matcher) {
        String option = matcher.group("option");
        GameMenuMessages message = GameMenuController.setUnitModeController(option);
        switch (message) {
            case SUCCESS -> Input_Output.outPut("state changed");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate!");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("coordinate is invalid!");
            case INVALID_STATE -> Input_Output.outPut("invalid state!");
        }
    }

    private static void attackAir(Matcher matcher) {
    }

    private static void attackGround(Matcher matcher) {
    }

    private static void pourOil(Matcher matcher) {
        String direction = matcher.group("direction");
        GameMenuMessages message = GameMenuController.pourOilController(direction);
        switch (message) {

        }
    }

    private static void digTunnel(Matcher matcher) {
        String coordinate = matcher.group("coordinate");
        GameMenuMessages message = GameMenuController.digTunnelController(coordinate);

    }

    private static void buildEquipment(Matcher matcher) {
    }

    private static void disbandUnit() {
    }

    private static void dropUnit(Matcher matcher) {
    }

    private static void showMap(Matcher matcher) {
    }
}
