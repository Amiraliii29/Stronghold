package Model.Buildings;

import Model.Government;
import Model.Square;
import Model.Resources.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.GameMenuController;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Generator extends Building {
    private static ArrayList<Generator> generators;
    private static ArrayList<String> generatorsName;
    private int usingRate;
    private int generatingRate;
    private Resource resourceGenerate;
    private Resource resourceNeed;
    private int numberOfWorker;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Generator>>() {}.getType();
            generators = gson.fromJson(new FileReader("src/main/resources/Buildings/Generator.json"), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatorsName = new ArrayList<>();
        for (Generator generator : generators) {
            generatorsName.add(generator.name);
            GameMenuController.addToGameBuildings(generator);
        }
    }

    private Generator(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp, ArrayList<String> lands,
                     int hp, Resource resource, int numberOfResource, int cost, boolean canPass, int usingRate, int generatingRate,
                     Resource resourceGenerate, Resource resourceNeed, int numberOfWorker) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
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

    public static ArrayList<String> getGeneratorsName() {
        return generatorsName;
    }

    public static ArrayList<Generator> getGenerators(){
        return generators;
    }

    public static ArrayList<String> getBuildingsLandsByName(String buildingName){
        for (Generator generator : generators) {
            if(generator.name.equals(buildingName))
                return generator.lands;
        }
        return null;
    }

    public static Generator createGenerator(Government owner, int xCoordinateLeft, int yCoordinateUp, String generatorName) {
        for (Generator generator : generators) {
            if (generator.name.equals(generatorName)) {
                Generator newGenerator = new Generator(owner, generator.name, generator.width, generator.length, xCoordinateLeft, yCoordinateUp,
                        generator.lands, generator.hp, generator.resource, generator.numberOfResource, generator.cost, generator.canPass,
                        generator.usingRate, generator.generatingRate, generator.resourceGenerate, generator.resourceNeed, generator.numberOfWorker);
                return newGenerator;
            }
        }
        return null;
    }
}
