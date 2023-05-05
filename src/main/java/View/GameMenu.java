package View;

import Controller.GameMenuController;
import Controller.Orders;
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
                attack(matcher);
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
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING) != null)
                dropBuilding(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_BUILDING) != null)
                selectBuilding(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.CREATE_UNIT) != null)
                createUnit(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.REPAIR_BUILDING) != null)
                repair();
            else
                System.out.println("invalid command");
        }
    }

    private static void nextTurn() {
//        GameMenuMessages message = GameMenuController.nextTurnController();
//
//        switch (message){
//        }
    }

    private static void userLogout() {
    }

 
    private static void dropBuilding(Matcher matcher) {
        String buildingComponents=matcher.group("buildingComponents");
        String x=Orders.findFlagOption("-x", buildingComponents);
        String y=Orders.findFlagOption("-y", buildingComponents);
        String buildingName=Orders.findFlagOption("-type", buildingComponents);

        if(x==null || y==null || buildingName==null){
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }

        GameMenuMessages result=GameMenuController.dropBuildingController(x, y, buildingName);
        switch (result) {
            case WRONG_FORMAT_COORDINATE:
                Input_Output.outPut("error: coordination format is invalid!");
                break;

            case INVALID_COORDINATE:
                Input_Output.outPut("error: coordination is out of maps' bounds!");
                break;

            case DROPBUILDING_INVALID_BUILDINGNAME:
                Input_Output.outPut("error: there is no building with such name!");
                break;

            case DROPBUILDING_INVALID_PLACE:
                Input_Output.outPut("error: can't build there! either incompatible or already occupied land!");
                break;

            case INSUFFICIENT_GOLD:
                Input_Output.outPut("error: you don't have enough gold for this operation!");
                break;

            case INSUFFICIENT_RESOURCES:
                Input_Output.outPut("error: you don't have enough resources for this building!");
                break;
            
        
            default:
                Input_Output.outPut("the building was succesfully built!");
                break;
        }
    }

    private static void selectBuilding(Matcher matcher) {
        String buildingComponents=matcher.group("buildingComponents");
        String x=Orders.findFlagOption("-x", buildingComponents);
        String y=Orders.findFlagOption("-y", buildingComponents);

        if(x==null || y==null){
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }

        GameMenuMessages result=GameMenuController.selectBuildingController(x, y);
        switch (result) {
            case WRONG_FORMAT_COORDINATE:
            Input_Output.outPut("error: coordination format is invalid!");
            break;

            case INVALID_COORDINATE:
            Input_Output.outPut("error: coordination is out of maps' bounds!");
            break;

            case SELECTBUILDING_EMPTY_SQUARE:
            Input_Output.outPut("error: the selected square doesn't have a building!");
            break;

            case SELECTBUILDING_UNOWNED_BUILDING:
            Input_Output.outPut("error: the building you want to select is not yours!");
            break;
        
            default:
            Input_Output.outPut("succesfully selected the building!");
            break;
        }
        

    }

    private static void createUnit(Matcher matcher) {
        String inputComponents=matcher.group("unitComponents");
        String type=Orders.findFlagOption("-t", inputComponents);
        String count=Orders.findFlagOption("-c", inputComponents);
        
        if(type==null || count==null){
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }
        GameMenuMessages result=GameMenuController.createUnitController(type, count);
        switch (result) {
            case CREATE_UNIT_WRONG_SELECTED_BUILDING:
                Input_Output.outPut("error: the selected building is not a barracks!");
                break;

            case CREATEUNIT_WRONG_NUMBERFORMAT:
                Input_Output.outPut("error: invalid format or number of units!");
                break;

            case CREATEUNIT_UNMATCHING_BARRACK:
                Input_Output.outPut("error: the selected barracks can't build such unit!");
                break;
        
            case INSUFFICIENT_GOLD:
                Input_Output.outPut("error: you don't have enough gold for this operation!");
                break;

            case INSUFFICIENT_RESOURCES:
                Input_Output.outPut("error: you don't have enough weapons for the trainings!");
                break;

            case CREATEUNIT_INSUFFICIENT_FREEPOP:
                Input_Output.outPut("error: you don't have enough population of free workers!");
                break;

            default:
                Input_Output.outPut("succesfully trained the troops!");
                break;
        }

    }

    private static void repair() {
        GameMenuMessages message = GameMenuController.repairController();
        switch (message) {
            case REPAIR_UNREPAIRABLE_SELECTED_BUILDING:
                Input_Output.outPut("error: the selected building is not repairable!");
                break;

            case EMPTY_INPUT_FIELDS_ERROR:
                Input_Output.outPut("error: nothing is selected!");
                break;
        
            case INSUFFICIENT_RESOURCES:
                Input_Output.outPut("error: insufficient stone to continue the repairing!");
                break;

            default:
                Input_Output.outPut("building is now fully repaired!");
                break;
        }
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
        String input=matcher.group("coordinates");
        String targetX=Orders.findFlagOption("-x", input);
        String targetY=Orders.findFlagOption("-y", input);

        if(targetX==null || targetY==null){
            Input_Output.outPut("error: empty coordination fields!");
            return;
        }

        GameMenuMessages result=GameMenuController.rangedAttackController(targetX, targetY);
        switch (result) {
            case SUCCESS:
                Input_Output.outPut("the selected units performed a ranged attack on the target units succesfully!");
                break;

            case WRONG_FORMAT_COORDINATE:
                Input_Output.outPut("error: coordination format is invalid!");
                break;
            
            case INVALID_COORDINATE:
                Input_Output.outPut("error: coordination is out of map's bounds!");
                break;

            case ATTACK_NO_ENEMY_IN_AREA:
                Input_Output.outPut("error: the selected square doesn't have a building!");
                break;

            case RANGEDATTACK_NON_ARCHER_SELECTION:
                Input_Output.outPut("error: the selected units are not archers!");
                break;

            case RANGEDATTACK_TARGET_NOT_IN_RANGE:
                Input_Output.outPut("error: the target square is not in the range of archers!");
                break;
            
            default:
                break;
        }
    }

    private static void attack(Matcher matcher) {
        String input=matcher.group("coordinates");
        String targetX=Orders.findFlagOption("-x", input);
        String targetY=Orders.findFlagOption("-y", input);

        if(targetX==null || targetY==null){
            Input_Output.outPut("error: empty coordination fields!");
            return;
        }

        GameMenuMessages result=GameMenuController.attackController(targetX, targetY);
        switch (result) {
            case SUCCESS:
                Input_Output.outPut("the selected units performed a ranged attack on the target units succesfully!");
                break;

            case WRONG_FORMAT_COORDINATE:
                Input_Output.outPut("error: coordination format is invalid!");
                break;
            
            case INVALID_COORDINATE:
                Input_Output.outPut("error: coordination is out of map's bounds!");
                break;

            case ATTACK_NO_ENEMY_IN_AREA:
                Input_Output.outPut("error: the selected square doesn't have a building!");
                break;

            case NORMALATTACK_TARGET_NOT_IN_RANGE:
                Input_Output.outPut("error: the units are not able to reach the target to perform attack!");
                break;

            default:
            break;
        }
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
        String siegeName = matcher.group("siegeName");
        GameMenuMessages message = GameMenuController.buildEquipmentController(siegeName);
        switch (message) {
            case WRONG_NAME -> System.out.println("there is no siege with this name");
            case CHOSE_UNIT_FIRST -> System.out.println("choose Engineer first");
            case UNIT_ISNT_ENGINEER -> System.out.println("unit is not Engineer");
            case NOT_ENOUGH_ENGINEER -> System.out.println("not enough Engineer");
            case NOT_ENOUGH_BALANCE -> System.out.println("you dont have enough money");
            case CANT_BUILD_HERE -> System.out.println("cant build here");
            case SUCCESS -> System.out.println("siege created");
        }
    }

    private static void disbandUnit() {
        GameMenuMessages message = GameMenuController.disbandUnitController();
        switch (message) {
            case CHOSE_UNIT_FIRST -> System.out.println("choose unit first");
            case SUCCESS -> System.out.println("unit disbanded");
        }
    }

    private static void dropUnit(Matcher matcher) {
    }

    private static void showMap(Matcher matcher) {
    }
}
