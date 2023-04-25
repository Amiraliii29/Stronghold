package Model.Units.Enums;

import Model.Government;
import Model.Resources.Resource;
import Model.Resources.ResourceEnum;
import Model.Units.Siege;

public enum SiegeEnums {
    PortableShield("PortableShield", 2, 100, 0, 0, 100, 1, null, 0),
    BatteringRam("BatteringRam", 2, 400, 100, 1, 200, 4, null, 0),
    SiegeTower("SiegeTower", 2, 300, 0, 7, 200, 4, null, 0),
    Catapult("Catapult", 2, 300, 75, 13, 250, 2, ResourceEnum.Stone, 20),
    Trebuchet("Trebuchet", 0, 300, 100, 17, 300, 3, ResourceEnum.Stone, 20),
    FireBallista("FireBallista", 2, 300, 60, 17, 250, 2, null, 0)
    ;

    private String name;
    private int speed;
    private int hitPoint;
    private int damage;
    private int attackRange;
    private int cost;
    private int engineerNeed;
    private Resource whatTheyThrow;
    private int howManyLeft;

    private SiegeEnums(String name, int speed, int hitPoint, int damage, int attackRange, int cost, int engineerNeed, ResourceEnum whatTheyThrow ,int howManyLeft) {
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.cost = cost;
        this.engineerNeed = engineerNeed;
        this.whatTheyThrow = ResourceEnum.createResources(whatTheyThrow);
        this.howManyLeft = howManyLeft;
    }

    public static Siege createSiege(Government government, SiegeEnums siege) {
        Siege newSiege = new Siege(government, siege.name, siege.speed, siege.hitPoint,
                siege.damage, siege.attackRange, siege.cost, siege.engineerNeed, siege.whatTheyThrow, siege.howManyLeft);
        return newSiege;
    }
}
