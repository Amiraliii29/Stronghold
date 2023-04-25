package Model.Buildings;

import Model.Resources.Resource;

import java.util.ArrayList;

public class TownBuilding extends Building {
    private int capacity;
    private int popularityRate;

    public TownBuilding(String name, int hp, Resource resource, int numberOfResource,
                        int cost, int capacity, int popularityRate, boolean canPass) {
        super(name, hp, resource, numberOfResource, cost, canPass);
        this.capacity = capacity;
        this.popularityRate = popularityRate;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPopularityRate(int popularityRate) {
        this.popularityRate = popularityRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPopularityRate() {
        return popularityRate;
    }
}
