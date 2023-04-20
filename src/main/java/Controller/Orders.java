package Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.DataBase;

import java.security.SecureRandom;

public class Orders {
    private static Scanner scanner = new Scanner(System.in);

    public static Matcher createMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static String findFlagOption(String flag, String input) {
        String optionRegex = "(?<option>(\"[^\"]+\")|([^\\s]+))";
        String flagRegex = flag + "\\s+" + optionRegex;
        Matcher flagMatcher = createMatcher(flagRegex, input);
        if (!flagMatcher.find()) return null;


        String output = flagMatcher.group("option");
        if (output.charAt(0) == '\"')
            output = trimEndAndStartOfString(output);

        return output;
    }

    public static String findWordAfterFlagSequence(String flag, String input) {
        String nextWordRegex = "(?<nextWord>(\"[^\"]+\")|([^\\s]+))";

        String flagOption = findFlagOption(flag, input);
        if (flagOption == null) return null;

        String nextWordSearchRegex = flag + "\\s+" + flagOption + "\\s+" + nextWordRegex;
        Matcher nextWordMatcher = createMatcher(nextWordSearchRegex, input);
        if (!nextWordMatcher.find()) return null;

        return nextWordMatcher.group("nextWord");
    }

    public static Boolean isOrderNotJunky(String order) {
        //ToDo

        //note: aksare ordera, bayad hameye flag haye valid va optioneshon ke joda shodan az string,
        //      kamel khali she va chizi azash namone; vagarna yani vasate dastor
        //      chize cherto pert vared karde va error bayad begire
    }

    public static String trimEndAndStartOfString(String input) {
        String output = "";
        for (int i = 1; i < input.length() - 1; i++) {
            output = output.concat("" + input.charAt(i));
        }
        return output;
    }

    public static String getNextlineInput() {
        return scanner.nextLine();
    }
}
