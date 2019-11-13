package com.bri64.uno;

import java.util.Scanner;

public class Prompt {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String ANSI_RED_BOLD = "\033[1;31m";    // RED
    public static final String ANSI_GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String ANSI_YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String ANSI_BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String ANSI_PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String ANSI_CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String ANSI_WHITE_BOLD = "\033[1;37m";  // WHITE

    public static String getCardColored(Card card) {
        String color = ANSI_WHITE_BOLD;
        switch ((card.getSelected() == null) ? card.getColor() : card.getSelected()) {
            case RED:
                color = ANSI_RED_BOLD;
                break;
            case GREEN:
                color = ANSI_GREEN_BOLD;
                break;
            case BLUE:
                color = ANSI_CYAN_BOLD;
                break;
            case YELLOW:
                color = ANSI_YELLOW_BOLD;
                break;
            case BLACK:
                color = ANSI_PURPLE_BOLD;
                break;
        }
        return color + card.getFaceName() + ANSI_RESET;
    }


    public static String getString() {
        return new Scanner(System.in).nextLine();
    }

    public static int getInt() {
        try {
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a number!");
            return getInt();
        }

    }

    public static String promptString(String prompt) {
        return promptString(prompt, false);
    }

    public static String promptString(String prompt, Boolean newLine) {
        System.out.print(prompt + ((newLine) ? "\n" : ""));
        return getString();
    }

    public static int promptInt(String prompt) {
        return promptInt(prompt, false);
    }

    public static int promptInt(String prompt, Boolean newLine) {
        System.out.print(prompt + ((newLine) ? "\n" : ""));
        return getInt();
    }
}
