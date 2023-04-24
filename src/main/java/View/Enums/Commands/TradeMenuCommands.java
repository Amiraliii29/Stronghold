package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {

    TRADE_REQUEST("^\\s*trade\\s*(?<options>.+)$"),
    TRADE_LIST("^\\s*trade\\s*list\\s*$"),
    ACCEPT_TRADE("^\\s*trade\\s+accept\\s*(?<options>.+)\\s*$");

    private String regex;

    private TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
