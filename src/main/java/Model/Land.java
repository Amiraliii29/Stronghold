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
    ;
    private String name;

    Land(String name) {
        this.name = name;
    }
    public static String getName(Land mainEnum){
        return mainEnum.name;
    }
}
