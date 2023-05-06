package View;

import java.util.Scanner;

public class Input_Output {
    private static Scanner scanner;

    static{
        scanner=new Scanner(System.in);
    }
    public static String getInput() {
        String input = scanner.nextLine();
        return input;
    }

    public static void outPut(String output) {
        System.out.println(output);
    }

    public static void setScanner(Scanner scanner) {
        Input_Output.scanner = scanner;
    }
}
