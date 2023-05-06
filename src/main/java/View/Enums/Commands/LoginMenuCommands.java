package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    
    LOGOUT("\\s*logout\\s*"),

    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),

    ENTER_MAP_MENU("\\s*enter\\s+map\\s+menu\\s*"),
    START_GAME("^\\s*start\\s*game\\s*(?<options>.+)$"),
    ;
    private String regex;

    private LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
