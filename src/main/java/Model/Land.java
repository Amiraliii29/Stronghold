package Model;

public enum Land {
    DEFAULT("default Land"),
    GRAVEL("gravel"),
    FLAT_ROCK("flat rock"),
    ROCK("rock"),
    IRON("iron"),
    GRASS("grass"),
    MEADOW("meadow"),
    FULL_MEADOW("high density meadow"),
    OIL("oil"),
    PLAIN("plain"),
    LOW_DEPTH_WATER("low depth water"),
    RIVER("river"),
    SMALL_LAKE("small lake"),
    BIG_LAKE("big lake"),
    BEACH("beach"),
    SEA("sea"),
    ;
    private String name;

    Land(String name) {
        this.name = name;
    }
    public static String getName(Land mainEnum){
        return mainEnum.name;
    }
}
