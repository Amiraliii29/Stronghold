import Controller.GameMenuController;
import Model.DataBase;
import Model.Map;
import Model.Units.Troop;
import Model.Units.Unit;
import org.junit.jupiter.api.BeforeAll;
import org.scalatest.compatible.Assertion;
import Model.Government;
import Model.User;
import jdk.jfr.Experimental;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

public class TestClass {
    User user = new User("kiarash", "123", "dahga", "lakh;g;", "afjhgk");
    Government government = new Government(user, 0);
    User user1 = new User("ali", "123", "adjgg", "al;khgdjkafg", "agkfhdgkd");
    Government government1 = new Government(user1, 100);
    Map map = new Map("map1", 100, 100);


    @Test
    public void testGovernment() {
    }

    @Test
    public void testSimpleMove() {
        Troop newTroop = Troop.createTroop(government, "Archer", 10, 10);
        System.out.println(map.getSquareFromMap(10,10).getUnits());
        ArrayList<Unit> troops = new ArrayList<>();
        troops.add(newTroop);
        DataBase.setSelectedUnit(troops);
        GameMenuController.moveUnitController("-x 12 -y 12");
        System.out.println(map.getSquareFromMap(10,10).getUnits());
        System.out.println(map.getSquareFromMap(12,12).getUnits());
        Assert.assertArrayEquals();
    }

}