package Model.Buildings.Enums;

import Model.Buildings.Defence;
import Model.Resources.Resource;
import Model.Resources.ResourceEnum;

public enum DefenceEnums {

    ;

    private String name;
    private int hp;
    private Resource resource;
    private int numberOfResource;
    private int cost;
    private boolean canPass;
    private int defenceRange;
    private int attackRange;
    private int capacity;

    private DefenceEnums(String name, int hp, ResourceEnum resource, int numberOfResource, int cost,
                 boolean canPass, int defenceRange, int attackRange, int capacity) {
        this.name = name;
        this.hp = hp;
        this.resource = ResourceEnum.createResources(resource);
        this.numberOfResource = numberOfResource;
        this.cost = cost;
        this.canPass = canPass;
        this.defenceRange = defenceRange;
        this.attackRange = attackRange;
        this.capacity = capacity;
    }

    public Defence createDefence(DefenceEnums defence) {
        return new Defence(defence.name, defence.hp, defence.resource, defence.numberOfResource, defence.cost,
                defence.canPass, defence.defenceRange, defence.attackRange, defence.capacity);

    }
}
