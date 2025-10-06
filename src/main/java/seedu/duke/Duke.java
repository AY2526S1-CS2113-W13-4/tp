package seedu.duke;

import java.util.Scanner;

public class Duke {
    static String line = "______________________________________________________________________";

    static String[] list = new String[100]; //initialise list of size 100
    static int listLength = 0;

    public static void intro() {
        System.out.println(line);
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        System.out.println(line);
    }

    public static void store_input() {
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        switch (userInput) {
        case "exit":
            System.out.println(line);
            System.out.println("Program Terminated.");
            System.out.println(line);
            System.exit(0);
            break;
        case "list": //list out all items
            int tmp = 0;
            System.out.println(line);
            while (list[tmp] != null) {
                System.out.print((tmp + 1) + ". ");
                System.out.println(list[tmp]);
                tmp++;
            }
            System.out.println(line);
            break;
        default: //default case add userInput to the list
            list[listLength] = userInput;
            listLength++;
            System.out.println("Added to list: " + userInput);
            System.out.println(line);
            break;
        }
    }

    public static void main(String[] args) {
        intro();
        while (true) {
            store_input();
        }
    }
}

