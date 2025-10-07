package seedu.duke;

import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    public static final String line = "______________________________________________________________________";
    public static final int MAX_RECORDS = 100;
    static Activity[] list = new Activity[MAX_RECORDS];
    static int listLength = 0;

    public static void intro() {
        System.out.println(line);
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        System.out.println(line);
    }

    public static void handleUserInput() {
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        String[] userInputArray = userInput.split(" ");
        String command = userInputArray[0]; //read first word of input as command

        switch (command) {
        case "exit":
            terminateProgram();
            break;
        case "list": //list out all items
            listItems();
            break;
        case "add_item": //add itinerary entry
            ParseActivityData activityData = getParseActivityData(userInputArray);
            addActivityDataToList(activityData);
            break;

        default:
            System.out.println(line);
            System.out.println("Invalid Command.");
            System.out.println(line);
        }
    }

    private static void addActivityDataToList(ParseActivityData activityData) {
        list[listLength] = new Activity(activityData.date(), activityData.time(), activityData.description(), activityData.cost());
        System.out.println(line);
        System.out.print("Added Activity to Itinerary: ");
        System.out.print("Date: " + list[listLength].getDate() + "|");
        System.out.print("Time: " + list[listLength].getTime() + "|");
        System.out.print("Description: " + list[listLength].getDescription() + "|");
        System.out.println("Cost: $" + list[listLength].getCost());
        System.out.println(line);
        listLength++;
    }

    private static ParseActivityData getParseActivityData(String[] userInputArray) {
        String[] parsedUserInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);//truncate the command from userInput
        String parsedUserInput = String.join(" ", parsedUserInputArray).trim();

        String[] parseDate = parsedUserInput.split("d/", 2);
        String[] parseTime = parseDate[1].split("t/", 2);
        String[] parseDescription = parseTime[1].split("desc/", 2);
        String[] parseCost = parseDescription[1].split("c/", 2);

        String date = parseTime[0].trim();
        String time = parseDescription[0].trim();
        String description = parseCost[0].trim();
        String cost = parseCost[1].trim();
        return new ParseActivityData(date, time, description, cost);
    }

    private record ParseActivityData(String date, String time, String description, String cost) {
    }

    private static void listItems() {
        int index = 0;
        System.out.println(line);
        while (list[index] != null) {
            System.out.println((index + 1) + ". ");
            System.out.println("Date: " + list[index].getDate());
            System.out.println("Time: " + list[index].getTime());
            System.out.println("Description: " + list[index].getDescription());
            System.out.println("Cost: $" + list[index].getCost());
            System.out.println(line);
            index++;
        }
    }

    private static void terminateProgram() {
        System.out.println(line);
        System.out.println("Program Terminated.");
        System.out.println(line);
        System.exit(0);
    }


    public static void main(String[] args) {
        intro();
        while (true) {
            handleUserInput();
        }
    }
}

