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
import Model.Government;
import Model.User;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class TestClass {
     User userTest1 = new User("kiarash", "123", "dahga", "lakh;g;", "afjhgk");
     Government governmentTest1 = new Government( 0);
     User userTest2 = new User("ali", "123", "adjgg", "al;khgdjkafg", "agkfhdgkd");
     Government governmentTest2 = new Government( 100);
     Map mapTest = new Map("mapTest1", 100, 100);

    @BeforeAll
    public void beforeAll(){
        governmentTest1.setOwner(userTest1);
        governmentTest2.setOwner(userTest2);
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
        SignUpMenuController.userLoginController("tempUser", "aA@123", false);
        Assertions.assertEquals(User.getCurrentUser(), User.getUserByUserName("tempUser"));

        ProfileMenuController.setCurrentUser(User.getCurrentUser());

        ProfileMenuMessages result;
        result=  ProfileMenuController.changeUsername("oi oi");
        Assertions.assertEquals(result, ProfileMenuMessages.INVALID_USERNAME_ERROR);

        ProfileMenuController.changeUsername("testt");
        Assertions.assertEquals(User.getCurrentUser().getUsername(), "testt");

        ProfileMenuController.changeUsername("TERMALL");
        Assertions.assertEquals(User.getCurrentUser().getUsername(), "TERMALL");

        ProfileMenuController.changeUsername("test");
    }

}