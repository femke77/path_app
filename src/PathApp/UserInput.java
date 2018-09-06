/*
Name: Megan Meyers
Date: 11/22/15
CS 282 Project 3 - Directed Weighted Graphs
Class description: This is my standard UserInput class that I use for all projects involving user interface. They include methods for getting any primitive data type from user (including option to limit parameters), error checking, and exception handling.   
 */

package PathApp;


import java.util.*;

public class UserInput {

    public static int getInt() {
        Scanner s = new Scanner(System.in);
        int integer = 0;
        boolean continueInput = true;
        do {
            try {
                //  System.out.println("Input an int:");
                integer = s.nextInt();
                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Reenter your selection.");
                s.nextLine();
            }
        } while (continueInput);
        return integer;
    }

    public static double getDouble() {
        Scanner s = new Scanner(System.in);
        double number = 0;
        boolean continueInput = true;
        do {
            try {
                //System.out.println("Input a double:");
                number = s.nextDouble();
                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Input not of requested type double. Try again.");
                s.nextLine();
            }
        } while (continueInput);
        return number;
    }

    public static char getChar() {
        Scanner s = new Scanner(System.in);
        //System.out.println("Input a char:");
        char character = s.next().toLowerCase().charAt(0);
        return character;
    }

    public static String getString() {
        Scanner s = new Scanner(System.in);
        //    System.out.println("Input a string:");
        String string = s.nextLine();
        return string;
    }

    public static int getInt(int min, int max) {
        Scanner s = new Scanner(System.in);
        int integer = 0;
        boolean continueInput = true;
        do {
            try {
                //   System.out.println("Input an integer between " + min + " and " + max + ":");
                integer = s.nextInt();
                while (integer < min || integer > max) {
                    System.out.println("Integer out of parameter range. Try again.\nInput an integer between " + min + " and " + max + ":");
                    integer = s.nextInt();
                }
                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Input was not of requested type Int.");
                s.nextLine();
            }
        } while (continueInput);
        return integer;
    }

    public static double getDouble(double min, double max) {
        Scanner s = new Scanner(System.in);
        double number = 0;
        boolean continueInput = true;
        do {
            try {
                //System.out.println("Input a double between " + min + " and " + max + ":");
                number = s.nextDouble();
                while (number < min || number > max) {
                    System.out.println("Double out of parameter range. Try again.\nInput a double between " + min + " and " + max + ":");
                    number = s.nextDouble();
                }
                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Input was not of requested type Double.");
                s.nextLine();
            }
        } while (continueInput);
        return number;
    }

    public static char getChar(char min, char max) {
        Scanner s = new Scanner(System.in);
        System.out.println("Input a char (case insensitive) between " + min + " and " + max + ":");
        char character = s.next().toLowerCase().charAt(0);
        while (character < min || character > max) {
            System.out.println("Input outside of parameters. Try again.\nInput a char between " + min + " and " + max + ":");
            character = s.next().toLowerCase().charAt(0);
        }
        return character;
    }

    public static String getString(int min, int max) {
        Scanner s = new Scanner(System.in);
        //      System.out.println("Input a string with length between " + min + " and " + max + " characters:");
        String string = s.nextLine();
        while (string.length() < min || string.length() > max) {
            System.out.println("String input exceeds current available recordsize. Try again.\nInput a string between " + min + " and " + max + " characters:");
            string = s.nextLine();
        }
        return string;
    }

}//end class UserInput
