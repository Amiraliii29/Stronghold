package Model.PeoplePac.Enums;

import Model.Government;
import Model.PeoplePac.Siege;

public enum SiegeEnums {
    PortableShield("PortableShield", 2, 100, 0, 0, 100, 1),
    BatteringRam("BatteringRam", 2, 400, 100, 1, 200, 4),
    SiegeTower("SiegeTower", 2, 300, 0, 7, 200, 4),
    Catapult("Catapult", 2, 300, 75, 13, 250, 2),
    Trebuchet("Trebuchet", 0, 300, 100, 17, 300, 3),
    FireBallista("FireBallista", 2, 300, 60, 17, 250, 2)
    ;

    private String name;
    private int speed;
    private int hitPoint;
    private int damage;
    private int attackRange;
    private int cost;
    private int engineerNeed;

    private SiegeEnums(String name, int speed, int hitPoint, int damage, int attackRange, int cost, int engineerNeed) {
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.cost = cost;
        this.engineerNeed = engineerNeed;
    }

    public static Siege createSiege(Government government, SiegeEnums siege) {
        Siege newSiege = new Siege(government, siege.name, siege.speed, siege.hitPoint,
                siege.damage, siege.attackRange, State.Stan_Ground, siege.cost, siege.engineerNeed);
        return newSiege;
    }
}
