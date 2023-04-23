package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SHOW_PRICE_LIST("^\\s*show\\s*price\\s*list\\s*$"),
    BUY_ITEM("^buy\\s*[(?<nameOption>-i (?<name>.+))(?<amountOption>-a (?<amount>.+))]{2}$"),
    SELL_ITEM("^sell\\s*[(?<nameOption>-i (?<name>.+))(?<amountOption>-a (?<amount>.+))]{2}$");
    private String regex;

    private ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ShopMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
