package Model.Buildings;

import Model.Resources.Resource;

import java.util.ArrayList;

public class Generator extends Building{
    private int usingRate;
    private int generatingRate;
    private Resource resourceGenerate;
    private Resource resourceNeed;
    private int numberOfWorker;

    public Generator(String name, int hp, Resource resource, int numberOfResource, int cost, int usingRate, int generatingRate,
                     Resource resourceGenerate, Resource resourceNeed, int numberOfWorker, boolean canPass) {
        super(name, hp, resource, numberOfResource, cost, canPass);
        this.usingRate = usingRate;
        this.generatingRate = generatingRate;
        this.resourceGenerate = resourceGenerate;
        this.resourceNeed = resourceNeed;
        this.numberOfWorker = numberOfWorker;
    }

    public void setResourceGenerate(Resource resourceGenerate) {
        this.resourceGenerate = resourceGenerate;
    }

    public void setResourceNeed(Resource resourceNeed) {
        this.resourceNeed = resourceNeed;
    }

    public void setNumberOfWorker(int numberOfWorker) {
        this.numberOfWorker = numberOfWorker;
    }

    public int getUsingRate() {
        return usingRate;
    }

    public int getGeneratingRate() {
        return generatingRate;
    }

    public Resource getResourceGenerate() {
        return resourceGenerate;
    }

    public Resource getResourceNeed() {
        return resourceNeed;
    }

    public int getNumberOfWorker() {
        return numberOfWorker;
    }

    private Generator (Generator g) {
        super(g.name, g.hp, g.resource, g.numberOfResource, g.cost, g.canPass);
        this.usingRate = g.usingRate;
        this.generatingRate = g.generatingRate;
        this.resourceGenerate = g.resourceGenerate;
        this.resourceNeed = g.resourceNeed;
        this.numberOfWorker = g.numberOfWorker;
    }

    public Generator clone() {
        return new Generator(this);
    }
}
