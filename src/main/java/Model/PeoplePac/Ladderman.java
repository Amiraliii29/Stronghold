package Model.PeoplePac;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Ladderman extends Unit{
    public Ladderman(Government owner) {
        super(owner, name, speed, hitPoint, damage, attackRange, resources, state, cost); //TODO
    }
}
