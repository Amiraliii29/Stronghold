package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomizeMapCommands {
    CREATE_NEW_MAP("^\\s*create\\s*new\\s*map(?<options>.+)$"),
    VALID_NUMBER("^\\d+$"),
    SELECT_MAP("^\\s*select\\s*map\\s*(?<mapName>\\S+)\\s*$"),
    SET_TEXTURE("^\\s*set\\s*texture\\s*(?<options>.+)\\s*$"),
    CLEAR("^\\s*clear\\s*(?<options>.+)$"),
    DROP_TREE("^\\s*drop\\s*tree\\s*(?<options>.+)\\s*$"),
    DROP_CLIFF("^\\s*drop\\s*rock\\s*(?<options>.+)\\s*$"),
    DROP_BUILDING("^\\s*drop\\s*building\\s*(?<options>.+)$"),
    DROP_UNIT("^\\s*drop\\s*unit\\s*(?<options>.+)$"),
    BACK("^\\s*back\\s*$"),
    SHOW_MAP("^\\s*enter\\s*show\\s*map\\s*menu\\s*$")
    ;
    private String regex;

    private CustomizeMapCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, CustomizeMapCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
