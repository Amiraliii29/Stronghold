package Model.PeoplePac.Enums;

import Model.Government;
import Model.PeoplePac.Troop;
import Model.Resources.Resource;
import Model.Resources.ResourceEnum;

import java.util.ArrayList;

public enum TroopEnums {
    Archer("Archer", 4, 70, 30, 15, ResourceEnum.Bow, null, 12, true, true, false),
    CrossBowMan("CrossBowMan", 2, 90, 30, 15, ResourceEnum.CrossBow, ResourceEnum.LeatherArmor, 12, false, false, false),
    SpearMan("SpearMan", 3, 50, 40, 1, ResourceEnum.Spear, null, 12, true, true, false),
    PikeMan("PikeMan", 2, 110, 40, 1, ResourceEnum.Pike, ResourceEnum.MetalArmor, 12, false, true, false),
    MaceMan("MaceMan", 3, 90, 50, 1, ResourceEnum.Mace, ResourceEnum.LeatherArmor, 12, true, true, false),
    SwordMan("SwordMan", 1, 110, 60, 1, ResourceEnum.Sword, ResourceEnum.MetalArmor, 12, false, false, false),
    Knight("Knight", 5, 110, 60, 1, ResourceEnum.Sword, null, 12, false, false, true),
    BlackMonk("BlackMonk", 2, 90, 40, 1, null, null, 12, false, false, false),
    ArcherBow("ArcherBow", 4, 70, 30, 15, null, null, 80, false, true, false),
    Slave("Slave", 4, 30, 10, 1, null, null, 5, false, true, false),
    Slinger("Slinger", 4, 50, 30, 10, null, null, 10, false, false, false),
    Assassin("Assassin", 3, 90, 3, 1, null, null, 75, false, false, false),
    HorseArcher("HorseArcher", 5, 90, 30, 15, null, null, 80, false, false, false),
    ArabianSwordMan("ArabianSwordMan", 1, 110, 50, 1, null, null, 75, false, false, false),
    FireThrower("FireThrower", 3, 70, 50, 10, null, null, 100, false, false, false)
    ;

    private String name;
    private int speed;
    private int hitPoint;
    private int damage;
    private int attackRange;
    private ArrayList<Resource> weapons;
    private int cost;
    private boolean climbLadder;
    private boolean digMoat;
    private boolean needHorse;

    private TroopEnums(String name, int speed, int hitPoint, int damage, int attackRange,
               ResourceEnum resource1, ResourceEnum resource2, int cost, boolean climbLadder, boolean digMoat, boolean needHorse) {
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.cost = cost;
        this.climbLadder = climbLadder;
        this.digMoat = digMoat;
        this.needHorse = needHorse;
        weapons = new ArrayList<>();
        if (resource1 != null) weapons.add(ResourceEnum.createResources(resource1));
        if (resource2 != null) weapons.add(ResourceEnum.createResources(resource2));
    }

    public static Troop createUnit(Government owner, TroopEnums troop) {
        Troop newTroop = new Troop(owner, troop.name, troop.speed, troop.hitPoint, troop.damage,
                troop.attackRange, troop.weapons, State.Stan_Ground, troop.cost, troop.climbLadder, troop.digMoat);
        newTroop.setNeedHorse(troop.needHorse);
        return newTroop;
    }
}
