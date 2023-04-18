package Controller;


import java.util.regex.Matcher;

import Model.DataBase;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenuController {

    //username password email slogan
    public static SignUpMenuMessages createUserController(String userName , String passWord,
                            String nickName, String passWordConfirmation ,String email , String slogan){
        email=email.toLowerCase();

        if(DataBase.getUserByUserName(userName) != null)
         return SignUpMenuMessages.DUPLICATE_USERNAME_SIGNUP_ERROR;

        if(!isUsernameFormatValid(userName))
         return SignUpMenuMessages.INVALID_USERNAME_SIGNUP_ERROR;

        if(DataBase.getUserByEmail(email) != null)
         return SignUpMenuMessages.DUPLICATE_EMAIL_SIGNUP_ERROR;

        if(!isPasswordStrong(passWord)) 
         return SignUpMenuMessages.WEAK_PASSWORD_SIGNUP_ERROR;
        
        if(!passWord.equals(passWordConfirmation))
         return SignUpMenuMessages.WRONG_PASSWORD_REPEAT_SIGNUP_ERROR;

        if(!isEmailFormatValid(email))
         return SignUpMenuMessages.INVALID_EMAIL_SIGNUP_ERROR;
        


         
        return SignUpMenuMessages.SUCCESFUL_SIGNUP_STEP; 
    }

    public static SignUpMenuMessages userLoginController(String userName , String passWord , String option){
    }

    public static SignUpMenuMessages forgotMyPassWordController(String userName){
    }

    private static boolean isEmailFormatValid(String inputEmail){
        String spaceCheckerRegex="\\s";
        Matcher spaceCheckerMatcher=Orders.createMatcher(spaceCheckerRegex, inputEmail);
        if(spaceCheckerMatcher.find()) return false;

        String correctFormatRegex="(?<emailBody>[^\\@]+)\\@(?<firstEmailHead>[^\\.]+)\\.(?<secondEmailHead>.+)";
        Matcher correctFormatMatcher=Orders.createMatcher(correctFormatRegex, inputEmail);
        if(!correctFormatMatcher.matches()) return false;

        return true;
    }

    private static boolean isPasswordStrong(String password){
        if(password.length()<6) return false;

        String bigAlphabetRegex="[A-Z]";
        Matcher bigAlphabetMatcher=Orders.createMatcher(bigAlphabetRegex, password);
        if(!bigAlphabetMatcher.find()) return false;

        String smallAlphabetRegex="[a-z]";
        Matcher smallAlphabetMatcher=Orders.createMatcher(smallAlphabetRegex, password);
        if(!smallAlphabetMatcher.find()) return false;

        String numberRegex="[0-9]";
        Matcher numberMatcher=Orders.createMatcher(numberRegex, password);
        if(!numberMatcher.find()) return false;

        String specialCharRegex="[^a-zA-Z0-9]";
        Matcher specialCharMatcher=Orders.createMatcher(specialCharRegex, password);
        if(!specialCharMatcher.find()) return false;

        return true;
    }

    private static boolean isUsernameFormatValid(String inputUsername){
        String invalidCharRegex="[^A-Za-z0-9\\_]";
        Matcher invalidCharMatcher=Orders.createMatcher(invalidCharRegex, inputUsername);
        if(invalidCharMatcher.find()) return false;

        return true;
    }

}
