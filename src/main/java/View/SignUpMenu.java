package View;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import Controller.SignUpMenuController;
import Controller.UserInfoOperator;
import Model.User;
import Controller.Orders;
import View.Enums.Commands.SignUpMenuCommands;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenu {

    public static void run() throws NoSuchAlgorithmException {
        String input;
        Input_Output.outPut("SIGNUP MENU:");

        while (User.getCurrentUser() == null) {
            Matcher matcher;
            input = Input_Output.getInput();

            if (input.equals("exit")) break;

            if (SignUpMenuController.getPenalty() > 0)
                Input_Output.outPut("error: you have to wait " + SignUpMenuController.getPenalty() + " seconds before next order!");

            else if ((matcher = SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.SIGNUP)) != null)
                createUser(matcher);

            else if ((matcher = SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.FORGOT_PASSWORD)) != null)
                forgotMyPassWord();

            else if ((matcher = SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.LOGIN)) != null)
                userLogin(matcher);

            else Input_Output.outPut("error: invalid command!");
        }
        handleLoginProcess();
        LoginMenu.run();
    }

    private static void createUser(Matcher matcher) throws NoSuchAlgorithmException {

        String signupComponentsInput = matcher.group("signupComponents");
        SignUpMenuMessages message = SignUpMenuController.runControllerSignupFunction(signupComponentsInput);

        switch (message) {

            case EMPTY_FIELDS_SIGNUP_ERROR:
                Input_Output.outPut("error: you have left some nessecary fields empty!");
                break;

            case INVALID_EMAIL_SIGNUP_ERROR:
                Input_Output.outPut("error: invalid email!");
                break;

            case DUPLICATE_EMAIL_SIGNUP_ERROR:
                Input_Output.outPut("error: email already in use!");
                break;

            case DUPLICATE_USERNAME_SIGNUP_ERROR:
                Input_Output.outPut("error: your username was already in use!");
                break;

            case INVALID_USERNAME_SIGNUP_ERROR:
                Input_Output.outPut("error: invalid username!");
                break;

            case WEAK_PASSWORD_ERROR:
                Input_Output.outPut("error: your password is weak!");
                break;

            case WRONG_PASSWORD_REPEAT_SIGNUP_ERROR:
                Input_Output.outPut("error: password was not repeated correctly!");
                break;

            case SUCCESFUL_SIGNUP_STEP:
                Input_Output.outPut("Congratulations! you succesfully signed in.");
                break;

            default:
                break;
        }

    }

    private static void userLogin(Matcher matcher) throws NoSuchAlgorithmException {
        String loginComponents = matcher.group("loginComponents");
        String username = Orders.findFlagOption("-u", loginComponents);
        String password = Orders.findFlagOption("-p", loginComponents);
        boolean stayLoggidInFlag = Orders.doesFlagExist("--stay-logged-in", loginComponents);

        SignUpMenuMessages result = SignUpMenuController.userLoginController(username, password, stayLoggidInFlag);
        switch (result) {
            case LOGIN_EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty username or password field!");
                break;
            case LOGIN_INCORRECT_PASSWORD_ERROR:
                Input_Output.outPut("error: incorrect password!");
                break;
            case LOGIN_INVALID_USERNAME_ERROR:
                Input_Output.outPut("error: invalid username!");
                break;
            case SUCCESFUL_LOGIN:
                Input_Output.outPut("login succesful!");
                break;
            default:
                Input_Output.outPut("error logging in!");
                break;
        }

        if (!result.equals(SignUpMenuMessages.SUCCESFUL_LOGIN))
            SignUpMenuController.setNewPenalty();

    }

    private static void forgotMyPassWord() throws NoSuchAlgorithmException {
        Input_Output.outPut("Please enter your email below:");
        String userEmail = Input_Output.getInput();

        Input_Output.outPut("try to remember the security question you have answered!");
        Input_Output.outPut("===the questions where: ");
        displaySecurityQuestions();

        Input_Output.outPut("===please enter your security answer to start recovery: ");
        String answer = Input_Output.getInput();

        SignUpMenuMessages message = SignUpMenuController.forgotMyPassWordController(userEmail, answer);

        switch (message) {
            case SUCCESFUL_FORGET_PASSWORD:
                Input_Output.outPut("your new password has been saved succesfuly!");
                break;

            case INVALID_EMAIL_FORGET_PASSWORD_ERROR:
                Input_Output.outPut("error: there is no user associated with the entered email!");
                break;

            case INCORRECT_SECURITY_FORGET_PASSWORD_ERROR:
                Input_Output.outPut("error: the security answer doesnt match!");
                break;

            default:
                Input_Output.outPut("error restoring your password!");
                break;
        }
    }

    public static String suggestNewUsername(String username) {

        Input_Output.outPut("username is already in use!");

        username = UserInfoOperator.addRandomizationToString(username);
        Input_Output.outPut("would you like " + username + " to be your username instead?(yes to proceed)");
        String userAnswer = Input_Output.getInput();

        if (userAnswer.equals("yes"))
            return username;

        return null;
    }

    public static void chooseSecurityQuestionForUser(User user) {

        Input_Output.outPut("sign up was succesful! now please answer a security question of your choice to finish:");
        displaySecurityQuestions();
        // HANDLING USER RESPONSE:
        while (true) {
            String input = Input_Output.getInput();

            Matcher matcher;
            if ((matcher = SignUpMenuCommands.getMatcher(input, SignUpMenuCommands.SECURITY)) == null) {
                Input_Output.outPut("error: invalid input!");
                continue;
            }


            String inputComponents = matcher.group("securityComponents");
            SignUpMenuMessages response = SignUpMenuController.UserSecurityAnswerController(inputComponents, user);


            if (response.equals(SignUpMenuMessages.SUCCESFUL_SECURITY_ANSWER))
                return;
            else
                Input_Output.outPut("error: invalid form of answer or wrong answer repetition!");
        }

    }

    public static String confirmRandomPassword(String randomChosenPassword) {
        Input_Output.outPut("your random password is: " + randomChosenPassword);
        Input_Output.outPut("please re-enter the password chosen for you:");
        return Input_Output.getInput();
    }

    public static void displayRandomSlogan(String slogan) {
        Input_Output.outPut("your random slogan is: " + slogan);
    }

    public static String getNewPasswordFromUser() {
        Input_Output.outPut("identification was succesful!");

        String input;
        while (true) {
            Input_Output.outPut("enter and repeat a new password!(-newp <password> <repeat>)");
            input = Input_Output.getInput();
            String password = Orders.findFlagOption("-newp", input);
            String passwordConfirmation = Orders.findWordAfterFlagSequence("-newp", input);
            SignUpMenuMessages message = SignUpMenuController.handleNewPasswordEntry(password, passwordConfirmation);

            if (message.equals(SignUpMenuMessages.SUCCESFUL_FORGET_PASSWORD))
                return password;

            else if (message.equals(SignUpMenuMessages.WEAK_PASSWORD_ERROR))
                Input_Output.outPut("error: your new password is weak!");

            else
                Input_Output.outPut("error: invalid input or unmatching password repeat!");

        }
    }

    private static void displaySecurityQuestions() {
        String securityQuestion;
        int i = 1;
        while (true) {
            securityQuestion = UserInfoOperator.getSecurityQuestionByIndex(i);
            if (securityQuestion != null)
                Input_Output.outPut("" + i + ") " + securityQuestion);
            else break;
            i++;
        }
    }

    private static void handleLoginProcess() {
        User welcomedUser = User.getCurrentUser();
        Input_Output.outPut("welcome: " + welcomedUser.getNickName() + "! ");
    }

}