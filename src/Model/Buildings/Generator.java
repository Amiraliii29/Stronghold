package Model.Buildings;

import Model.Resource;

public class Generator extends Building{
    private int rate;
    private Resource resourceGenerate;
    private Resource resourceNeed;
    private int numberOfWorker;

    public Generator(String name, int hp, Resource resource, int numberOfResource, int cost) {
        super(name, hp, resource, numberOfResource, cost);
    }

    public void setRate(int rate) {
        this.rate = rate;
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

    public int getRate() {
        return rate;
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
}