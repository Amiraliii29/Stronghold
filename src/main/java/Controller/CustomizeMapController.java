package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.Buildings.Generator;
import Model.Buildings.Stockpile;
import Model.Units.*;
import View.CustomizeMap;
import View.Enums.Commands.CustomizeMapCommands;
import View.Enums.Messages.CustomizeMapMessages;
import View.GameMenu;
import View.Input_Output;

import java.util.Random;

import javax.transaction.xa.Xid;

public class CustomizeMapController {
    private static String[] randomDirection = {"n" , "s" , "e" , "w"};

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

        else
        {
            // enter governments count
            CustomizeMap.printer("enter the number of governments in map");
            String governmentsCount = Input_Output.getInput();
            if(CustomizeMapCommands.getMatcher(governmentsCount , CustomizeMapCommands.VALID_NUMBER) == null)
                return CustomizeMapMessages.INVALID_NUMBER;
            int governmentsCountInt = Integer.parseInt(governmentsCount);
            if(governmentsCountInt <= 0 || governmentsCountInt > 8)
                return CustomizeMapMessages.INVALID_NUMBER;
            // create map
            Map map = new Map(mapName , widthInt , lengthInt);
            map.setGovernmentsInMap(governmentsCountInt);
            Map.saveMap(map , mapName);
            return CustomizeMapMessages.CREATE_NEW_MAP_SUCCESS;
        }
    }

    public static CustomizeMapMessages selectMapController(String mapName){
        Map.loadMap(mapName);
        if(DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.MAP_NOT_FOUND;
        else
            return CustomizeMapMessages.SELECT_MAP_SUCCESS;
    }

    public static CustomizeMapMessages setTextureController(String x , String y , String x1 , String y1 ,
                                                            String x2 , String y2 , String type){
        if(x != null && y != null){
            if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
            CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
                return CustomizeMapMessages.INVALID_NUMBER;

            int xInt = Integer.parseInt(x);
            int yInt = Integer.parseInt(y);

            if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
                return CustomizeMapMessages.X_OUT_OF_BOUNDS;
            else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
                return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

            Land land = Land.getLandByName(type);

            if(land == null)
                return CustomizeMapMessages.INVALID_TYPE;

            DataBase.getSelectedMap().getSquareFromMap(xInt -1  , yInt -1 ).setLand(land);
            Map.saveMap(DataBase.getSelectedMap() , DataBase.getSelectedMap().getName());
            return CustomizeMapMessages.SET_TEXTURE_SUCCESS;

        }
        else if(x1 != null && x2 != null && y1 != null && y2 != null){
            if(CustomizeMapCommands.getMatcher(x1 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher(x2 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher(y1 , CustomizeMapCommands.VALID_NUMBER) == null ||
                    CustomizeMapCommands.getMatcher( y2 , CustomizeMapCommands.VALID_NUMBER) == null)
                return CustomizeMapMessages.INVALID_NUMBER;

            int x1Int = Integer.parseInt(x1);
            int y1Int = Integer.parseInt(y1);
            int x2Int = Integer.parseInt(x2);
            int y2Int = Integer.parseInt(y2);


            if (DataBase.getSelectedMap() == null)
                return CustomizeMapMessages.NO_MAP_SELECTED;
            else if(x1Int <= 0 || x1Int > DataBase.getSelectedMap().getLength() ||x2Int <= 0
                    || x2Int > DataBase.getSelectedMap().getLength())
                return CustomizeMapMessages.X_OUT_OF_BOUNDS;
            else if(y1Int <= 0 || y1Int > DataBase.getSelectedMap().getWidth() || y2Int <= 0 ||
                    y2Int > DataBase.getSelectedMap().getWidth())
                return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

            Land land = Land.getLandByName(type);

            if(land == null)
                return CustomizeMapMessages.INVALID_TYPE;

            for (int j = y2Int - 1 ; j >= y1Int - 1 ; j--){
                for (int i = x1Int - 1 ; i <= x2Int - 1 ; i++){
                    DataBase.getSelectedMap().getSquareFromMap(i , j).setLand(land);
                }
            }
            Map.saveMap(DataBase.getSelectedMap() , DataBase.getSelectedMap().getName());
            return CustomizeMapMessages.SET_TEXTURE_SUCCESS;
        }
        else{
            return CustomizeMapMessages.INVALID_OPTIONS;
        }
    }

    public static CustomizeMapMessages clearController(String x , String y){
        if(x == null || y  == null)
            return CustomizeMapMessages.INVALID_OPTIONS;
        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);

        if (DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.NO_MAP_SELECTED;

        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

        else {
            Square selectedSquare = DataBase.getSelectedMap().getSquareFromMap(yInt - 1 , xInt - 1);
            selectedSquare.setLand(Land.DEFAULT);
            selectedSquare.setBuilding(null);
            selectedSquare.newSelectedUnit();
            return CustomizeMapMessages.CLEAR_TILE_SUCCESS;

        }
    }

    public static CustomizeMapMessages dropRockController(String x , String y , String direction){
        if(x == null || y  == null || direction == null)
            return CustomizeMapMessages.INVALID_OPTIONS;
        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);

        if (DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.NO_MAP_SELECTED;

        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;
        else if(!direction.equals("n") && !direction.equals("s") && !direction.equals("w") &&
                !direction.equals("e") && !direction.equals("r"))
            return CustomizeMapMessages.INVALID_DIRECTION;
        else {
            Square squareSelected = DataBase.getSelectedMap().getSquareFromMap(yInt , xInt);
            squareSelected.setLand(Land.CLIFF);
            if(direction.equals("r")){
                Random random = new Random();
                int rand = random.nextInt(4);
                direction = randomDirection[rand];
            }
            squareSelected.setCliffDirection(direction);
            return CustomizeMapMessages.DROP_ROCK_SUCCESS;
        }
    }

    public static CustomizeMapMessages dropTreeController(String x , String y , String type){
        if(x == null || y  == null)
            return CustomizeMapMessages.INVALID_OPTIONS;
        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);

        if (DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.NO_MAP_SELECTED;
        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;
        else if(Trees.getTreeByName(type) == null)
            return CustomizeMapMessages.INVALID_TREE_NAME;
        else {
            Square selectedSquare = DataBase.getSelectedMap().getSquareFromMap(yInt , xInt);
            selectedSquare.setTree(Trees.getTreeByName(type));
            selectedSquare.setTreeAmount(100);
            return CustomizeMapMessages.DROP_TREE_SUCCESS;
        }

    }

    public static CustomizeMapMessages dropBuildingController(String x, String y, String type , String ownerGovernmentNumber) {
        if(x == null || y  == null)
            return CustomizeMapMessages.INVALID_OPTIONS;
        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;
        if(CustomizeMapCommands.getMatcher(ownerGovernmentNumber , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;
        if(ownerGovernmentNumber == null)
            return CustomizeMapMessages.NO_OWNER_GOVERNMENT_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);
        int ownerGovernmentNumberInt = Integer.parseInt(ownerGovernmentNumber);

        Building buildingToConstruct = GameMenuController.getBuildingByName(type);
        if (DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.NO_MAP_SELECTED;
        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;
        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;
        else if ( buildingToConstruct == null)
            return CustomizeMapMessages.INVALID_BUILDING_NAME;
        else if(ownerGovernmentNumberInt <= 0 || ownerGovernmentNumberInt > DataBase.getSelectedMap().getGovernmentsInMap().size())
            return CustomizeMapMessages.INVALID_GOVERNMENT_NUMBER;
        else{
            Map selectedMap = DataBase.getSelectedMap();
            for (int i = 0 ; i < buildingToConstruct.getWidth() ; i++) {
                for (int j = 0 ; j < buildingToConstruct.getLength() ; j++) {
                    Square selectedSquare = DataBase.getSelectedMap().getSquareFromMap(yInt + j , xInt + i);
                    if (!GameMenuController.getBuildingValidLandsByName(type).contains(Land.getName(selectedSquare.getLand())))
                        return CustomizeMapMessages.UNSUITABLE_LAND;
                }
            }
            Government government = DataBase.getCurrentGovernment();
            DataBase.setCurrentGovernment(selectedMap.getGovernmentsInMap().get(ownerGovernmentNumberInt - 1));

            if (!selectedMap.canConstructBuildingInPlace(buildingToConstruct, xInt, yInt))
                return CustomizeMapMessages.DROPBUILDING_INVALID_PLACE;

            if(type.equals("Keep") && selectedMap.getGovernmentsInMap().get(ownerGovernmentNumberInt - 1).getLord() == null){
                GameMenu.addKeepCnt();

                buildingToConstruct = GameMenuController.constructBuildingForPlayer(type, xInt, yInt);
                Stockpile stockpile = Stockpile.createStockpile(DataBase.getCurrentGovernment(), xInt + 8, yInt, "Stockpile");
                Stockpile granary = Stockpile.createStockpile(DataBase.getCurrentGovernment(), xInt + 8, yInt + 4, "Granary");

                selectedMap.constructBuilding(buildingToConstruct, xInt, yInt);
                selectedMap.constructBuilding(stockpile, xInt + 8, yInt);
                selectedMap.constructBuilding(granary, xInt + 8, yInt + 4);

                Government ownerGovernment = selectedMap.getGovernmentsInMap().get(ownerGovernmentNumberInt - 1);
                ownerGovernment.setLord(xInt , yInt);

                for (int i = 0 ; i < 5 ; i++) {
                    Troop.createTroop(ownerGovernment, "Archer", xInt, yInt);
                }
                for (int i = 0 ; i < 5 ; i++) {
                    Troop.createTroop(ownerGovernment, "SpearMan", xInt, yInt);
                }

                DataBase.getCurrentGovernment().addToStockpile(Resource.createResource("Bread"), 80);
                DataBase.getCurrentGovernment().addToStockpile(Resource.createResource("Stone"), 80);
                DataBase.getCurrentGovernment().addToStockpile(Resource.createResource("Wood"), 80);

            } else if (type.equals("Keep") && selectedMap.getGovernmentsInMap().get(ownerGovernmentNumberInt - 1).getLord() != null) {
                DataBase.setCurrentGovernment(government);
                return CustomizeMapMessages.THIS_GOVERNMENT_HAS_KEEP;
            }
            DataBase.setCurrentGovernment(government);
            return CustomizeMapMessages.DROP_BUILDING_SUCCESS;
        }
    }

    public static CustomizeMapMessages dropUnitController(String x, String y, String type, String count , String ownerGovernmentNumber) {
        if(x == null || y  == null || count == null)
            return CustomizeMapMessages.INVALID_OPTIONS;

        if(CustomizeMapCommands.getMatcher(x , CustomizeMapCommands.VALID_NUMBER) == null ||
                CustomizeMapCommands.getMatcher(y , CustomizeMapCommands.VALID_NUMBER) == null)
            return CustomizeMapMessages.INVALID_NUMBER;

        if(ownerGovernmentNumber == null)
            return CustomizeMapMessages.NO_OWNER_GOVERNMENT_NUMBER;

        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);
        int countInt = Integer.parseInt(count);
        int ownerGovernmentNumberInt = Integer.parseInt(ownerGovernmentNumber);

        if (DataBase.getSelectedMap() == null)
            return CustomizeMapMessages.NO_MAP_SELECTED;

        if(xInt <= 0 || xInt > DataBase.getSelectedMap().getLength())
            return CustomizeMapMessages.X_OUT_OF_BOUNDS;

        else if(yInt <= 0 || yInt > DataBase.getSelectedMap().getWidth())
            return CustomizeMapMessages.Y_OUT_OF_BOUNDS;

        else if(countInt < 0)
            return CustomizeMapMessages.INVALID_COUNT;

        else if(ownerGovernmentNumberInt <= 0 || ownerGovernmentNumberInt > DataBase.getSelectedMap().getGovernmentsInMap().size())
            return CustomizeMapMessages.INVALID_GOVERNMENT_NUMBER;

        else if (!Unit.getAllUnits().contains(type))
            return CustomizeMapMessages.No_UNIT_WITH_THIS_NAME;

        else{
            Government ownerGovernment = DataBase.getSelectedMap().getGovernmentsInMap().get(ownerGovernmentNumberInt - 1);
            Square selectedSquare = DataBase.getSelectedMap().getSquareFromMap(yInt-1 , xInt-1);
            if(selectedSquare.getLand().equals(Land.SEA) || selectedSquare.getLand().equals(Land.CLIFF)
                    || selectedSquare.getLand().equals(Land.OIL) || selectedSquare.getLand().equals(Land.ROCK) ||
            selectedSquare.getLand().equals(Land.FLAT_ROCK))
                return CustomizeMapMessages.UNSUITABLE_LAND;
            if (Troop.getTroopByName(type) != null) {
                for (int i = 0; i < countInt; i++)
                    Troop.createTroop(ownerGovernment, type, xInt, yInt);
            } else if (Siege.getSiegesName().contains(type)) {
                for (int i = 0; i < countInt; i++)
                    Siege.createSiege(ownerGovernment, type, xInt, yInt);
            } else if (type.equals("Engineer")) {
                for (int i = 0; i < countInt; i++)
                    Engineer.createEngineer(ownerGovernment, xInt, yInt);
            } else if (type.equals("Tunneler")) {
                for (int i = 0; i < countInt; i++)
                    Tunneler.createTunneler(ownerGovernment, xInt, yInt);
            } else if (type.equals("LadderMan")) {
                for (int i = 0; i < countInt; i++)
                    LadderMan.createLadderMan(ownerGovernment, xInt, yInt);
            } else return CustomizeMapMessages.No_UNIT_WITH_THIS_NAME;
            return CustomizeMapMessages.DROP_UNIT_SUCCESS;
        }

    }
}
