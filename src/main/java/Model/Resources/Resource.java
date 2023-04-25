package Model.Resources;

import java.util.ArrayList;
import java.util.Objects;

public class Resource {
    private String name;
    private int price;
    private double count;
    private String storage;//where we store it
    private static ArrayList<Resource> resources;
    private static ArrayList<Resource> foods;
    private static ArrayList<Resource> weapons;

    static {
        resources = new ArrayList<>();
        foods = new ArrayList<>();
        weapons = new ArrayList<>();
    }

    public Resource(String name, int price, String storage) {
        this.name = name;
        this.price = price;
        this.storage = storage;
        this.count = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return ((price * 8) / 10);
    }

    public int getBuyPrice() {
        return (price*1);
    }

    public double getCount() {
        return count;
    }

    public String getStorage() {
        return storage;
    }

    public void changeCount(double count) {
        this.count += count;
    }

    public boolean isThereEnough(double number) {
        return this.count > number;
    }

    public static void addToResources(Resource resource) {
        if (resource.getName().equals("Bread")
                || resource.getName().equals("Meat")
                || resource.getName().equals("Apples")
                || resource.getName().equals("Cheese")) {
            foods.add(resource);
        } else if (resource.getName().equals("Sword")
                || resource.getName().equals("Spear")
                || resource.getName().equals("Pike")
                || resource.getName().equals("Bow")
                || resource.getName().equals("CrossBow")
                || resource.getName().equals("Mace")
                || resource.getName().equals("MetalArmor")
                || resource.getName().equals("LeatherArmor")) {
            weapons.add(resource);
        } else resources.add(resource);
    }

    public static void newAllResources() {
        resources = new ArrayList<>();
        foods = new ArrayList<>();
        weapons = new ArrayList<>();

        for (ResourceEnum en : ResourceEnum.values()) {
            Resource.addToResources(ResourceEnum.createResources(en));
        }
//        //food
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Bread));
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Meat);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Apples);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Cheese);
//        //weapon
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Bow);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.CrossBow);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Sword);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Spear);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Mace);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Pike);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.MetalArmor);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.LeatherArmor);
//        //other
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Wood);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Stone);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Iron);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Pitch);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Ale);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Flour);
//        Resource.addToResources(ResourceEnum.createResources(ResourceEnum.Hops);
    }

    public static ArrayList<Resource> getResources() {
        return resources;
    }

    public static ArrayList<Resource> getFoods() {
        return foods;
    }

    public static ArrayList<Resource> getWeapons() {
        return weapons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return price == resource.price && Double.compare(resource.count, count) == 0 && Objects.equals(name, resource.name) && Objects.equals(storage, resource.storage);
    }
}
