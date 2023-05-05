import Controller.GameMenuController;
import Controller.JsonConverter;
import Controller.PenaltyTimer;
import Controller.ProfileMenuController;
import Controller.SignUpMenuController;
import Model.DataBase;
import Model.Map;
import Model.Units.Troop;
import Model.Units.Unit;
import View.SignUpMenu;
import View.Enums.Messages.ProfileMenuMessages;
import View.Enums.Messages.SignUpMenuMessages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.scalatest.compatible.Assertion;
import Model.Government;
import Model.User;
import jdk.jfr.Experimental;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
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
    }

    @Test
    public void testUserLogin() throws NoSuchAlgorithmException{
        String tempUserData="  -u tempUser -p aA@123 -n lol -s \" random ss logan\" --email lol@em.com";
        SignUpMenuMessages result;

        result=SignUpMenuController.userLoginController("tempUserrr", "aA@123", false);
        Assert.assertTrue(result.equals(SignUpMenuMessages.LOGIN_INVALID_USERNAME_ERROR));;

        SignUpMenuController.userLoginController("tempUser", "aA@123", false);
        Assert.assertEquals(User.getCurrentUser(), User.getUserByUserName("tempUser"));

        User.setCurrentUser(null);
        String userName="tempUser",password="aA@1234";
        result=SignUpMenuController.userLoginController(userName, password, false);
        Assert.assertTrue(SignUpMenuController.getPenalty()>4);
        Assert.assertEquals(result, SignUpMenuMessages.LOGIN_INCORRECT_PASSWORD_ERROR);
    }

    @Test
    public void testChangeSlogan() throws NoSuchAlgorithmException{
        SignUpMenuController.userLoginController("test", "@tesT12", false);
        Assertions.assertEquals(User.getCurrentUser(), User.getUserByUserName("test"));

        ProfileMenuController.setCurrentUser(User.getCurrentUser());
        ProfileMenuController.changeSlogan("biu biu");
        Assertions.assertEquals(User.getCurrentUser().getSlogan(), "biu biu");
        ProfileMenuController.removeSlogan();
    }

    @Test
    public void testChangeUsername() throws NoSuchAlgorithmException{
        SignUpMenuController.userLoginController("test", "@tesT12", false);
        Assertions.assertEquals(User.getCurrentUser(), User.getUserByUserName("test"));

        ProfileMenuController.setCurrentUser(User.getCurrentUser());
        
        ProfileMenuMessages result;
        result=  ProfileMenuController.changeUsername("oi oi");
        Assertions.assertEquals(result, ProfileMenuMessages.INVALID_USERNAME_ERROR);

        ProfileMenuController.changeUsername("TERMALL");
        Assertions.assertEquals(User.getCurrentUser().getUsername(), "TERMALL");

        ProfileMenuController.changeUsername("test");
    }

}