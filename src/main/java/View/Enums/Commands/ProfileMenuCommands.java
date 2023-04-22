package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("\\s*profile\\s+change\\s+username\\s+(?<newUsername>.+)\\s*"),

    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+(?<changePasswordComponents>.+)\\s*"),

    CHANGE_NICKNAME("\\s*profile\\s+change\\s+nickname\\s+(?<newNickname>.+)\\s*"),

    CHANGE_EMAIL("\\s*profile\\s+change\\s+email\\s+(?<newEmail>.+)\\s*"),

    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+(?<newSlogan>.+)\\s*"),

    REMOVE_SLOGAN("\\s*profile\\s+change\\s+remove\\s+slogan\\s*"),

    DISPLAY_ENTIRE_PROFILE("\\s*profile\\s+display\\s+entire\\s+profile\\s*"),

    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),

    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),

    DISPLAY_HIGHSCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    ;
    private String regex;

    private ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
