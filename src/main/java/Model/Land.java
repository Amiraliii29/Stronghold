package Model;

public enum Land {
    DEFAULT("default"),
    GRAVEL("gravel"),
    FLAT_ROCK("flatRock"),
    ROCK("rock"),
    IRON("iron"),
    GRASS("grass"),
    MEADOW("meadow"),
    FULL_MEADOW("highDensityMeadow"),
    OIL("oil"),
    PLAIN("plain"),
    LOW_DEPTH_WATER("lowDepthWater"),
    RIVER("river"),
    SMALL_LAKE("smallLake"),
    BIG_LAKE("bigLake"),
    BEACH("beach"),
    SEA("sea"),
    CLIFF("cliff"),
    ;
    private String name;

    private Land(String name) {
        this.name = name;
    }

    public static String getName(Land mainEnum){
        return mainEnum.name;
    }

    public static Land getLandByName(String name){
        for (Land land : Land.values()) {
            if (land.name.equals(name)) return land;
        }
        return null;
    }

}
