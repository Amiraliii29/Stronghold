package Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.DataBase;

import java.security.SecureRandom;

public class Orders {

    private static Scanner scanner = new Scanner(System.in);
 



    public static Matcher createMatcher(String regex,String input){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(input);
        return matcher;
    }

    private static String findRawFlagOption(String flag, String input){
        String optionRegex="(?<option>(\"[^\"]+\")|([^\\s]+))";
        String flagRegex=flag+"\\s+"+optionRegex;
        Matcher flagMatcher = createMatcher(flagRegex, input);
        if(!flagMatcher.find()) return null;

        return flagMatcher.group("option");
    }

    public static boolean doesFlagExist(String flag, String input){
        Matcher matcher = createMatcher(flag, input);
        if(matcher.find())
          return true;

        return false;
    }

    public static String findFlagOption(String flag, String input){
        
        String option =findRawFlagOption(flag, input);
        if(option==null)
            return null;

        if(option.charAt(0)=='\"')
         option=trimEndAndStartOfString(option);

        return option;
    }

    public static String trimIfNeeded(String input){
        if(input.charAt(0)=='\"')
        return trimEndAndStartOfString(input);
        else return input;
    }

    public static String findWordAfterFlagSequence(String flag,String input){
        String nextWordRegex="(?<nextWord>(\"[^\"]+\")|([^\\s]+))";

        String flagOption=findRawFlagOption(flag, input);
        if(flagOption==null) return null;


        String nextWordSearchRegex=flag+"\\s+"+flagOption+"\\s+"+nextWordRegex;
        Matcher nextWordMatcher=createMatcher(nextWordSearchRegex, input);
        if(!nextWordMatcher.find()) return null;

        
        String nextWord= nextWordMatcher.group("nextWord");
        if(nextWord.charAt(0)=='\"')
         nextWord=trimEndAndStartOfString(nextWord);

        return nextWord;
    }

    public static Boolean isOrderNotJunky(String order){
        //ToDo

        //note: aksare ordera, bayad hameye flag haye valid va optioneshon ke joda shodan az string, 
        //      kamel khali she va chizi azash namone; vagarna yani vasate dastor
        //      chize cherto pert vared karde va error bayad begire
    }

    public static String trimEndAndStartOfString(String input){
        String output="";
        for (int i = 1; i < input.length()-1; i++) {
            output=output.concat(""+input.charAt(i));
        }
        return output;
    }
}