package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SELECT_UNIT("^\\s*select\\s+unit\\s+(?<option>.+)$"),
    MOVE_UNIT("^\\s*move\\s+unit\\s+to\\s+(?<coordinate>.+)$"),
    PATRON_UNIT("^\\s*patrol\\s+unit\\s+(<coordinate>.+)$"),
    SET_STATE("^\\s*set\\s+(?<option>.+)$"),
    ATTACK_GROUND("^\\s*attack\\s+-e\\s+(?<x>\\d+)\\s+(?<y>\\d+)$"),
    ATTACK_AIR("^\\s*attack\\s+(?<coordinate>.+)$"),
    DIG_TUNNEL("^\\s*dig\\s+tunnel\\s+(?<coordinate>.+)$"),
    POUR_OIL("^\\s*pour\\s+oil\\s+-d\\s+(?<direction>.+)$"),
    BUILD_SIEGE("^\\s*build\\s+-q\\s+(?<siegeName>.+)$"),
    DISBAND("^\\s*disband\\s+unit\\s*$"),
    ;
    private String regex;

    private GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
