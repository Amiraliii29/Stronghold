package Controller;

import java.security.SecureRandom;
import java.util.ArrayList;

public class UserInfoOperator {
    private static final ArrayList<String> defaultSlogans = new ArrayList<String>();
    private static SecureRandom randomGenerator = new SecureRandom();

    static {
        setDefaultSlogans();
    }

    public static String getRandomSlogan() {
        int randomNumBound = defaultSlogans.size();
        int randomSelectedIndex = randomGenerator.nextInt(randomNumBound);

        return defaultSlogans.get(randomSelectedIndex);
    }

    public static String generateRandomPassword() {
        int size = 6 + randomGenerator.nextInt(4);
        String output = "";

        for (int i = 0; i < size; i++) {
            switch (i) {
                case 0:
                    output = output.concat("" + generateBigAlphabet());
                    break;
                case 1:
                    output = output.concat("" + generateSmallAlphabet());
                    break;
                case 2:
                    output = output.concat("" + generateDecimalNumberInChar());
                    break;
                case 3:
                    output = output.concat("" + generateSpecialChar());
                    break;
                default:
                    output = output.concat("" + generateRandomChar());
                    break;
            }
        }
        return output;
    }

    public static String addRandomizationToString(String targetString) {
        String targetConcat = "";
        int randomizationSize = 2 + randomGenerator.nextInt(2);

        for (int i = 0; i < randomizationSize; i++) {
            targetConcat = targetConcat.concat("" + generateRandomChar());
        }
        targetString = targetString.concat(targetConcat);
        return targetString;
    }

    private static char generateSmallAlphabet() {
        //97 ta 122
        int randomValInInt = randomGenerator.nextInt(26);
        char randomChar = (char) (randomValInInt + 97);

        return randomChar;
    }

    private static char generateBigAlphabet() {

        int randomValInInt = randomGenerator.nextInt(26);
        char randomChar = (char) (randomValInInt + 65);

        return randomChar;
    }

    private static char generateDecimalNumberInChar() {

        int randomValInInt = randomGenerator.nextInt(10);
        char randomChar = (char) (randomValInInt + 48);

        return randomChar;
    }

    private static char generateSpecialChar() {
        int randomValInInt = randomGenerator.nextInt(15);
        char randomChar = (char) (randomValInInt + 33);

        return randomChar;
    }

    private static char generateRandomChar() {
        int randomFormat = randomGenerator.nextInt(4);
        char randomChar = (char) 0;

        switch (randomFormat) {
            case 0:
                randomChar = generateBigAlphabet();
                break;

            case 1:
                randomChar = generateSmallAlphabet();
                break;
            case 2:
                randomChar = generateSpecialChar();
                break;
            case 3:
                randomChar = generateDecimalNumberInChar();
                break;
            default:
                break;
        }

        return randomChar;
    }

    public static String trimEndAndStartOfString(String input) {
        String output = "";
        for (int i = 1; i < input.length() - 1; i++) {
            output = output.concat("" + input.charAt(i));
        }
        return output;
    }

    private static void setDefaultSlogans() {
        defaultSlogans.add("Ours is the fury");
        defaultSlogans.add("We do not sow");
        defaultSlogans.add("Hear me roar");
        defaultSlogans.add("We live to die another day");
        defaultSlogans.add("How many you got? just bring em all, I miss the 'get em gone'");
        defaultSlogans.add("These times murder pays like a part time job");
        defaultSlogans.add("yor woife is in me dms talking hey baby");
    }
}