package Model.PeoplePac;

import Model.Government;
import Model.Resources.Resource;
import Model.Resources.ResourceEnum;

import java.util.ArrayList;

public enum TroopEnums {
    Archer("Archer", 4, 70, 30, 15, ResourceEnum.Bow, null, 12, true, true),
    CrossBowMan("CrossBowMan", 2, 90, 30, 15, ResourceEnum.CrossBow, ResourceEnum.LeatherArmor, 12, false, false),
    SpearMan("SpearMan", 3, 50, 40, 1, ResourceEnum.Spear, null, 12, true, true),
    PikeMan("PikeMan", 2, 110, 40, 1, ResourceEnum.Pike, ResourceEnum.MetalArmor, 12, false, true),
    MaceMan("MaceMan", 3, 90, 50, 1, ResourceEnum.Mace, ResourceEnum.LeatherArmor, 12, true, true),
    SwordMan("SwordMan", 1, 110, 60, 1, ResourceEnum.Sword, ResourceEnum.MetalArmor, 12, false, false),
    Knight("Knight", 5, 110, 60, 1, ResourceEnum.Sword, ResourceEnum.Horse, 12, false, false),
    BlackMonk("BlackMonk", 2, 90, 40, 1, null, null, 12, false, false),

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

    private TroopEnums(String name, int speed, int hitPoint, int damage, int attackRange,
               ResourceEnum resource1, ResourceEnum resource2, int cost, boolean climbLadder, boolean digMoat) {
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.cost = cost;
        this.climbLadder = climbLadder;
        this.digMoat = digMoat;
        weapons = new ArrayList<>();
        if (resource1 != null) weapons.add(ResourceEnum.createResources(resource1));
        if (resource2 != null) weapons.add(ResourceEnum.createResources(resource2));
    }

    public static Troop createUnit(Government owner, TroopEnums troop) {
        return new Troop(owner, troop.name, troop.speed, troop.hitPoint, troop.damage,
                troop.attackRange, troop.weapons, State.Stan_Ground, troop.cost, troop.climbLadder, troop.digMoat);
    }
}
