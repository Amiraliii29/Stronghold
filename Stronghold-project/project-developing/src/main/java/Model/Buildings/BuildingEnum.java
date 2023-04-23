package Model.Buildings;

import Model.Resources.Resource;

public enum BuildingEnum {
    ;

    private String name;
    private Resource resource;
    private int hp;
    private int numberOfResource;
    private int cost;
    private boolean canPass;
    private BuildingEnum() {
    }
}
