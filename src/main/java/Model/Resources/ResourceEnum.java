package Model.Resources;

public enum ResourceEnum {
    Bread("Bread", 100, "Granary"),
    Meat("Meat", 100, "Granary"),
    Apples("Apples", 100, "Granary"),
    Cheese("Cheese", 100, "Granary"),
    Sword("Sword", 100, "Armoury"),
    Spear("Spear", 100, "Armoury"),
    Pike("Pike", 100, "Armoury"),
    MetalArmor("MetalArmor", 100, "Armoury"),
    Bow("Bow", 100, "Armoury"),
    CrossBow("CrossBow", 100, "Armoury"),
    Mace("Mace", 100, "Armoury"),
    LeatherArmor("LeatherArmor", 100, "Armoury"),
    Wood("Wood", 100, "Stockpile"),
    Iron("Iron", 100, "Stockpile"),
    Flour("Flour", 100, "Stockpile"),
    Hops("Hops", 100, "Stockpile"),
    Ale("Ale", 100, "Stockpile"),
    Stone("Stone", 100, "Stockpile"),
    Pitch("Pitch", 100, "Stockpile");

    private String name;
    private int price;
    private String storage;

    private ResourceEnum(String name, int price, String storage) {
        this.name = name;
        this.price = price;
        this.storage = storage;
    }

    public static void addToResources(ResourceEnum resourceEnum) {
        Resource resource = new Resource(resourceEnum.name, resourceEnum.price, resourceEnum.storage);
        Resource.addToResources(resource);
    }
}
