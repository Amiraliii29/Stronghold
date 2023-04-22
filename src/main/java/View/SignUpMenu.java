package View;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import Controller.SignUpMenuController;
import Controller.UserInfoOperator;
import Model.DataBase;
import Model.User;
import Controller.Orders;
import View.Enums.Commands.SignUpMenuCommands;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenu {
    
    public static void run(Scanner scanner) throws NoSuchAlgorithmException {
        String input;

        while (DataBase.getCurrentUser()==null) {
            Matcher matcher;
            input=scanner.nextLine();

            if(input.equals("exit"))
             return;
            
            if(SignUpMenuController.getPenalty()>0)
                Orders.printLine("error: you have to wait "+SignUpMenuController.getPenalty()+" seconds before next order!");

            else if( (matcher=SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.SIGNUP)) !=null )
            createUser(matcher);

            else if( (matcher=SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.FORGOT_PASSWORD)) !=null )
                forgotMyPassWord();
            
            else if( (matcher=SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.LOGIN))!=null )
                userLogin(matcher);
            
            else Orders.printLine("error: invalid command!");
        }
        handleLoginProcess();
        LoginMenu.run(scanner);
    }

    private static void createUser(Matcher matcher) throws NoSuchAlgorithmException {
        
        String signupComponentsInput=matcher.group("signupComponents");
        SignUpMenuMessages message=SignUpMenuController.runControllerSignupFunction(signupComponentsInput);
        
        switch (message) {

            case EMPTY_FIELDS_SIGNUP_ERROR:
             System.out.println("error: you have left some nessecary fields empty!");
             break;
            
            case INVALID_EMAIL_SIGNUP_ERROR:
              System.out.println("error: invalid email!");   
                break;
            
            case DUPLICATE_EMAIL_SIGNUP_ERROR:
             System.out.println("error: email already in use!");
             break;

            case DUPLICATE_USERNAME_SIGNUP_ERROR:
             Orders.printLine("error: your username was already in use!");
             break;

            case INVALID_USERNAME_SIGNUP_ERROR:
             System.out.println("error: invalid username!");
             break;
            
            case WEAK_PASSWORD_ERROR:
             System.out.println("error: your password is weak!");
             break;

            case WRONG_PASSWORD_REPEAT_SIGNUP_ERROR:
             System.out.println("error: password was not repeated correctly!");
             break;
        
            case SUCCESFUL_SIGNUP_STEP:
             System.out.println("Congratulations! you succesfully signed in.");
             break;

            default:
                break;
        }

    }

    private static void userLogin(Matcher matcher) throws NoSuchAlgorithmException {
        String loginComponents=matcher.group("loginComponents");
        String username=Orders.findFlagOption("-u", loginComponents);
        String password=Orders.findFlagOption("-p", loginComponents);
        boolean stayLoggidInFlag=Orders.doesFlagExist("--stay-logged-in", loginComponents);

        SignUpMenuMessages result=SignUpMenuController.userLoginController(username, password, stayLoggidInFlag);
        switch (result) {
            case LOGIN_EMPTY_FIELDS_ERROR:
                Orders.printLine("error: empty username or password field!");
                break;
            case LOGIN_INCORRECT_PASSWORD_ERROR:
                Orders.printLine("error: incorrect password!");
                break;
            case LOGIN_INVALID_USERNAME_ERROR:
                Orders.printLine("error: invalid username!");
                break;
            case SUCCESFUL_LOGIN:
                Orders.printLine("login succesful!");
                break;
            default:
                Orders.printLine("error logging in!");
                break;
        }

        if(!result.equals(SignUpMenuMessages.SUCCESFUL_LOGIN))
            SignUpMenuController.setNewPenalty();

    }

    private static void forgotMyPassWord() throws NoSuchAlgorithmException {
        Orders.printLine("Please enter your email below:");
        String userEmail=Orders.getNextlineInput();

        Orders.printLine("try to remember the security question you have answered!");
        Orders.printLine("===the questions where: ");
        displaySecurityQuestions();

        Orders.printLine("===please enter your security answer to start recovery: ");
        String answer=Orders.getNextlineInput();

        SignUpMenuMessages message=SignUpMenuController.forgotMyPassWordController(userEmail,answer);

        switch (message) {
            case SUCCESFUL_FORGET_PASSWORD:
                Orders.printLine("your new password has been saved succesfuly!");
                break;
            
            case INVALID_EMAIL_FORGET_PASSWORD_ERROR:
                Orders.printLine("error: there is no user associated with the entered email!");
                break;
            
            case INCORRECT_SECURITY_FORGET_PASSWORD_ERROR:
                Orders.printLine("error: the security answer doesnt match!");
                break;
            
            default:
                Orders.printLine("error restoring your password!");
                break;
        }
    }

    public static String suggestNewUsername(String username){

        Orders.printLine("username is already in use!");

        username=UserInfoOperator.addRandomizationToString(username);
        Orders.printLine("would you like "+username+" to be your username instead?(yes to proceed)");
        String userAnswer=Orders.getNextlineInput();

        if(userAnswer.equals("yes"))
         return username;
        
        return null;
    }

    public static void chooseSecurityQuestionForUser(User user){

        Orders.printLine("sign up was succesful! now please answer a security question of your choice to finish:");
        displaySecurityQuestions();
        // HANDLING USER RESPONSE:
        while (true) {
            String input=Orders.getNextlineInput();

            Matcher matcher;
            if( (matcher=SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.SECURITY)) ==null ){
                Orders.printLine("error: invalid input!");
                continue;
            }
                
            String inputComponents=matcher.group("securityComponents");
            SignUpMenuMessages response=SignUpMenuController.UserSecurityAnswerController(inputComponents, user);

            if(response.equals(SignUpMenuMessages.SUCCESFUL_SECURITY_ANSWER))
                return;
            else
                Orders.printLine("error: invalid form of answer or wrong answer repetition!");
        }

    }

    public static String confirmRandomPassword(String randomChosenPassword){
        Orders.printLine("your random password is: "+randomChosenPassword);
        Orders.printLine("please re-enter the password chosen for you:");
        return Orders.getNextlineInput();
    }

    public static void displayRandomSlogan(String slogan){
        Orders.printLine("your random slogan is: "+slogan);
    }

    public static String getNewPasswordFromUser(){
        Orders.printLine("identification was succesful!");

        String input;
        while (true) {
            Orders.printLine("enter and repeat a new password!(-newp <password> <repeat>)");
            input=Orders.getNextlineInput();
            String password=Orders.findFlagOption("-newp", input);
            String passwordConfirmation=Orders.findWordAfterFlagSequence("-newp", input);
            SignUpMenuMessages message=SignUpMenuController.handleNewPasswordEntry(password,passwordConfirmation);

            if(message.equals(SignUpMenuMessages.SUCCESFUL_FORGET_PASSWORD))
                return password;
            
            else if(message.equals(SignUpMenuMessages.WEAK_PASSWORD_ERROR))
                Orders.printLine("error: your new password is weak!");

            else
              Orders.printLine("error: invalid input or unmatching password repeat!");

        }
    }

    private static void displaySecurityQuestions(){
        String securityQuestion;
        int i=1;
        while (true) {
            securityQuestion=UserInfoOperator.getSecurityQuestionByIndex(i);
            if(securityQuestion != null)
              Orders.printLine(""+i+") "+securityQuestion);
            else break;
            i++;
        }
    }

    private static void handleLoginProcess(){
        User welcomedUser=DataBase.getCurrentUser();
        Orders.printLine("welcome: "+welcomedUser.getNickName()+"! ");
    }

}