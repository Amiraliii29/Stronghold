package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    
    SIGNUP("\\s*user\\s+create\\s+(?<signupComponents>.+)"),
    
    
    ;
    private String regex;

    private SignUpMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, SignUpMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
