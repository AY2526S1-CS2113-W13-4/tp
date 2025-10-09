package seedu.duke;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static final String LINE = "______________________________________________________________________";
    static ArrayList<Activity> list = new ArrayList<>();

    public static void intro() {
        System.out.println(LINE);
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        System.out.println(LINE);
    }

    public static void handleUserInput() {
        String userInput;
        Scanner in = new Scanner(System.in);
        if (!in.hasNextLine()) {
            System.out.println(LINE);
            System.out.println("Please Input a Command.");
            System.out.println(LINE);
            return;
        }
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
        case "add": //add itinerary entry
            ParseActivityData activityData = getParseActivityData(userInputArray);
            addActivityDataToList(activityData);
            break;

        default:
            invalidInput();
        }
    }

    private static void invalidInput() {
        System.out.println(LINE);
        System.out.println("Invalid Command.");
        System.out.println(LINE);
    }

    private static void addActivityDataToList(ParseActivityData activityData) {
        list.add(new Activity(activityData.date(), activityData.time(),
                activityData.description(), activityData.cost()));
        System.out.println(LINE);
        System.out.print("Added Activity to Itinerary: ");
        System.out.print("Date: " + activityData.date() + "|");
        System.out.print("Time: " + activityData.time() + "|");
        System.out.print("Description: " + activityData.description() + "|");
        System.out.println("Cost: $" + activityData.cost());
        System.out.println(LINE);
    }

    private static ParseActivityData getParseActivityData(String[] userInputArray) {
        //truncate the command from userInput
        String[] parsedUserInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);
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
        if (list.isEmpty()) {
            System.out.println(LINE);
            System.out.println("Itinerary is Empty!");
            System.out.println(LINE);
            return;
        }
        System.out.println(LINE);
        for (int index = 0; index < list.size(); index++) {
            System.out.println((index + 1) + ". ");
            System.out.println("Date: " + list.get(index).getDate());
            System.out.println("Time: " + list.get(index).getTime());
            System.out.println("Description: " + list.get(index).getDescription());
            System.out.println("Cost: $" + list.get(index).getCost());
            System.out.println(LINE);
        }
    }

    private static void terminateProgram() {
        System.out.println(LINE);
        System.out.println("Program Terminated.");
        System.out.println(LINE);
        System.exit(0);
    }


    public static void main(String[] args) {
        intro();
        while (true) {
            handleUserInput();
        }
    }
}

