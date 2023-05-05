package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShowMapMenuCommands {

    SHOW_MAP("^\\s*show\\s*map\\s*(?<options>.+)$"),
    MOVE_MAP("^\\s*map\\s*(?<direction>\\S+)\\s*(?<direction2>\\S+)?\\s*(?<amount>\\d+)?\\s*$"),
    SHOW_DETAILS("^\\s*show\\s*details(?<options>\\.+)\\s*$"),
    EXIT("^\\s*exit\\s*$"),
    VALID_NUMBER("^\\s*\\d+\\s*$"),
    ;
    private String regex;

    private ShowMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ShowMapMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
