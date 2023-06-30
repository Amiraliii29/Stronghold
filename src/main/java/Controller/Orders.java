package Controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import View.SignUpMenu;
import javafx.animation.FadeTransition;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

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

    public static void createNotificationDialog(String title,String header,String outputText,String Color){
        Dialog dialog=new Dialog<>();
        dialog.initOwner(SignUpMenu.stage);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        DialogPane dialogPane = dialog.getDialogPane();
        Text output=new Text(outputText);
        VBox vbox=new VBox(8, output);
        vbox.setStyle("-fx-background-color:"+Color);
        dialogPane.setContent(vbox);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialog.showAndWait();
    }

    public static void sendTextNotification(Text text, String output, String VboxColor, VBox vbox) {
        vbox.setStyle("-fx-background-color:" + VboxColor);
        text.setVisible(true);
        double minWidth = vbox.getMinWidth();
        double maxWidth = vbox.getMaxWidth();
        text.setText(output);
        text.setOpacity(1);
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(3), text);
        fadeTrans.setDelay(Duration.seconds(1));
        fadeTrans.setFromValue(1);
        fadeTrans.setToValue(0.2);
        fadeTrans.setOnFinished(event -> {
            vbox.setStyle("");
            vbox.setMinWidth(minWidth);
            vbox.setMaxWidth(maxWidth);
            text.setText("");
            text.setVisible(false);
        });
        fadeTrans.play();
    }

}