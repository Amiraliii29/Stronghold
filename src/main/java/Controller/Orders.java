package Controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Orders {

    public static String yellowNotifErrorColor="rgba(193, 174, 32, 0.628);";
    public static String greenNotifSuccesColor="rgba(50, 158, 20, 0.664);";
    public static String redNotifErrorColor="rgba(188, 25, 25, 0.623);";

    public static Matcher createMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    private static String findRawFlagOption(String flag, String input) {
        String optionRegex = "(?<option>(\"[^\"]+\")|([^\\s]+))";
        String flagRegex = flag + "\\s+" + optionRegex;
        Matcher flagMatcher = createMatcher(flagRegex, input);
        if (!flagMatcher.find()) return null;

        return flagMatcher.group("option");
    }

    public static boolean doesFlagExist(String flag, String input) {
        Matcher matcher = createMatcher(flag, input);
        if (matcher.find())
            return true;

        return false;
    }

    public static String findFlagOption(String flag, String input) {

        String option = findRawFlagOption(flag, input);
        if (option == null)
            return null;

        option = trimIfNeeded(option);
        return option;
    }

    public static String trimIfNeeded(String input) {
        if (input.charAt(0) == '\"')
            return trimEndAndStartOfString(input);
        else return input;
    }

    public static String findWordAfterFlagSequence(String flag, String input) {
        String nextWordRegex = "(?<nextWord>(\"[^\"]+\")|([^\\s]+))";

        String flagOption = findRawFlagOption(flag, input);
        if (flagOption == null) return null;


        String nextWordSearchRegex = flag + "\\s+" + flagOption + "\\s+" + nextWordRegex;
        Matcher nextWordMatcher = createMatcher(nextWordSearchRegex, input);
        if (!nextWordMatcher.find()) return null;


        String nextWord = nextWordMatcher.group("nextWord");
        nextWord = trimIfNeeded(nextWord);

        return nextWord;
    }


    public static boolean isInputInteger(String input){
        String integerRegex="\\d+";
        createMatcher(integerRegex, input);
        
        if(input.matches(integerRegex))
            return true;
        else return false;
    }

    public static Boolean isOrderJunky(String order,boolean hasConfirmPass,String ... flags) {
        
        if(hasConfirmPass){
            String password=findFlagOption("-p", order);
            if(!password.equals("random")){
                String repeat=findWordAfterFlagSequence("-p",order);
                order=removeSubstring(order, repeat);
            }
        }

        for (String flag : flags) {
            String option=findRawFlagOption(flag, order);
            order=removeSubstring(order, option);

            if(doesFlagExist(flag, order))
                order=removeSubstring(order, flag);
        }
        String emptyRegex="\\s*";
        Matcher matcher=createMatcher(emptyRegex, order);
        if(matcher.matches())
            return false;
    
         return true;
    }

    private static String removeSubstring(String input, String substring){
        if(substring==null)
            return input;
        StringBuffer buffer = new StringBuffer(input);
        int startIndex=input.indexOf(substring);
        buffer.replace(startIndex, startIndex+substring.length(), "");
        return buffer.toString();
    }

    private static String trimEndAndStartOfString(String input) {
        String output = "";
        for (int i = 1; i < input.length() - 1; i++) {
            output = output.concat("" + input.charAt(i));
        }
        return output;
    }

    public static ArrayList<int[]> concatCoords(ArrayList<int[]> coords1, ArrayList<int[]> coords2 ){
        coords1.addAll(coords2);
        return coords1;
    } 
}