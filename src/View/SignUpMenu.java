package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import Controller.SignUpMenuController;

import Controller.Orders;
import View.Enums.Commands.SignUpMenuCommands;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenu {
    
    public static void run(Scanner scanner) {

        String input;
        while (true) {
            Matcher matcher;
            input=scanner.nextLine();

            if(input.equals("exit"))
             return;
            
            if( (matcher=SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.SIGNUP)) !=null )
            createUser(matcher,scanner,null);
            //note: subUsername is for handling username suggestment and to call the function back



        }


    }

    private static void createUser(Matcher matcher, Scanner scanner,String subUsername) {
        //note: scanners only us to be passed to suggest username function, and subUsername 
        //      is to call back when pusername is alreadyInUse
        
        String signupComponentsInput=matcher.group("signupComponents");
        SignUpMenuMessages message=runControllerSignup(matcher,signupComponentsInput,subUsername);
        
        switch (message) {

            case EMPTY_FIELDS_SIGNUP_ERROR:
             System.out.println("error: you have left some nessecary fields empty!");
            
            case INVALID_EMAIL_SIGNUP_ERROR:
              System.out.println("error: invalid email!");   
                break;
            
            case DUPLICATE_EMAIL_SIGNUP_ERROR:
             System.out.println("error: email already in use!");
             break;

            case DUPLICATE_USERNAME_SIGNUP_ERROR:
             System.out.println("error: username already in use!");
             suggestNewUsername(signupComponentsInput,scanner,matcher);
             break;

            case INVALID_USERNAME_SIGNUP_ERROR:
             System.out.println("error: invalid username!");
             break;
            
            case WEAK_PASSWORD_SIGNUP_ERROR:
             System.out.println("error: your password is weak!");
             break;

            case WRONG_PASSWORD_REPEAT_SIGNUP_ERROR:
             System.out.println("error: password was not repeated correctly!");
             break;
        
            default:
                break;
        }

    }

    private static void userLogin(Matcher matcher) {
    }

    private static void forgotMyPassWord(Matcher matcher) {
    }

    private static void suggestNewUsername(String signupComponentsInput,Scanner scanner,Matcher matcher){
        String username=Orders.findFlagOption("-u", signupComponentsInput);
        username=Orders.addRandomizationToString(username);
        System.out.println("would you like "+username+" to be your username instead?(yes to proceed)");
        String userAnswer=scanner.nextLine();

        if(userAnswer.equals("yes"))
         createUser(matcher, scanner, username);
    }

    private static SignUpMenuMessages runControllerSignup(Matcher matcher,String signupComponentsInput,String subUsername){
        
        //NOTE: if(!Orders.isOrderNotJunky(signupComponentsInput)) System.out.println("error");

        String username=Orders.findFlagOption("-u", signupComponentsInput);
        String password=Orders.findFlagOption("-p", signupComponentsInput);
        String passwordConfirmation=Orders.findWordAfterFlagSequence("-p", signupComponentsInput);
        String nickName=Orders.findFlagOption("-n", signupComponentsInput);
        String email=Orders.findFlagOption("--email", signupComponentsInput);
        String slogan=Orders.findFlagOption("-s", signupComponentsInput);

        if(subUsername != null) username=subUsername;
        
        if(username.equals(null) || password.equals(null) || email.equals(null)
           || passwordConfirmation.equals(null) || nickName.equals(null)) {
            return SignUpMenuMessages.EMPTY_FIELDS_SIGNUP_ERROR;
        }

        return SignUpMenuController.createUserController(username,
                 password, nickName, passwordConfirmation, email, slogan);
    }
}
