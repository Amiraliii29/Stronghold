package Model;

public enum Trees {
    CHERRY_PALM("cherryPalm"),
    COCONUT_TREE("coconutTree"),
    OLIVE_PALM("olivePalm"),
    DATE_PALM("datePalm"),
    SICKNESS("sickness"),
    FIRE("fire"),
    ;

    private String name;

    Trees(String name) {
        this.name = name;
    }
    public static String getName(Trees mainEnum){
        if (mainEnum == null) return "--";
        return mainEnum.name;
    }
    public static Trees getTreeByName(String name){
        for (Trees trees : Trees.values()) {
            if (trees.name.equals(name)) return trees;
        }
        return null;
    }
}
