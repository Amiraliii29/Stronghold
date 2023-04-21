package Model.Buildings;

import Model.Resource;

public class TownBuilding extends Building {
    private int capacity;
    private int popularityRate;

    public TownBuilding(String name, int hp, Resource resource, int numberOfResource, int cost) {
        super(name, hp, resource, numberOfResource, cost);
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
