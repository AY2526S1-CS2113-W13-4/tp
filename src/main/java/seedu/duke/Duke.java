package seedu.duke;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Duke {
    public static final String LINE = "______________________________________________________________________";
    static ArrayList<Activity> list = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Duke.class.getName());


    public static void intro() {
        System.out.println(LINE);
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        System.out.println(LINE);
    }

    public static String handleUserInput() {
        String userInput;
        Scanner in = new Scanner(System.in);

        if (!in.hasNextLine()) {
            System.out.println(LINE);
            System.out.println("Please Input a Command.");
            System.out.println(LINE);
            return null;
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
        case "schedule":
            setByTime();
            break;
        case "view":
            view(userInputArray);
            break;
        case "delete":
            deleteActivityDataFromList(userInputArray);
            break;
        case "edit":
            editActivityDataInList(userInputArray);
            break;
        default:
            invalidInput();
        }
        return  userInput;
    }

    private static void invalidInput() {
        System.out.println(LINE);
        System.out.println("Invalid Command.");
        System.out.println(LINE);
    }

    private static void editActivityDataInList(String[] userInputArray) {
        logger.log(Level.INFO, "Editing Activity " +  userInputArray[0]);
        String[] parsedEditedInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);
        assert parsedEditedInputArray.length == userInputArray.length - 1 : "Parsed input must not have the command";

        int index = Integer.parseInt(parsedEditedInputArray[0]) - 1;
        assert index >= 0 && index < list.size() : "Index out of bounds";

        ParseActivityData editedActivityData = getParseActivityData(parsedEditedInputArray);
        assert editedActivityData != null : "Parsed activity data cannot be null";

        list.set(index, new Activity(editedActivityData.date(), editedActivityData.time(),
                editedActivityData.description(), editedActivityData.cost()));

        Activity updatedActivity = list.get(index);
        assert updatedActivity.getDate().equals(editedActivityData.date()) : "Date must match edited date";
        assert updatedActivity.getTime().equals(editedActivityData.time()) : "Time must match edited time";
        assert updatedActivity.getDescription().equals(editedActivityData.description()) : "Description " +
                "must match edited description";
        assert updatedActivity.getCost().equals(editedActivityData.cost()) : "Date must match edited date";

        System.out.println(LINE);
        System.out.println("Activity " + parsedEditedInputArray[0] + " has been edited with the following details:");
        System.out.print("Date: " + editedActivityData.date() + "|");
        System.out.print("Time: " + editedActivityData.time() + "|");
        System.out.print("Description: " + editedActivityData.description() + "|");
        System.out.println("Cost: $" + editedActivityData.cost());
        System.out.println(LINE);
    }

    private static void deleteActivityDataFromList(String[] userInputArray) {
        assert userInputArray.length >= 2 : "User input array must have at least 2 elements" ;
        logger.log(Level.INFO, "Deleting Activity " + userInputArray[0] + " from the list.");

        int index = Integer.parseInt(userInputArray[1]) - 1;
        assert index >= 0 && index < list.size() : "Index out of bounds";

        Activity deletedActivity = list.get(index);
        assert deletedActivity != null : "Activity " + userInputArray[0] + " cannot be null";

        int originalSize = list.size();
        list.remove(index);
        assert list.size() == originalSize - 1: "The list size should decrease by 1 after deletion";

        System.out.println(LINE);
        System.out.println("Deleted activity from Itinerary: ");
        System.out.println((index + 1) + ". " + deletedActivity.getDescription());
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

    private static void setByTime() {
        if (list.isEmpty()) {
            System.out.println(LINE);
            System.out.println("Itinerary is Empty! Nothing to sort.");
            System.out.println(LINE);
            return;
        }

        list.sort(Comparator.comparing(a -> a.getDateTimeObject().getDateTime()));

        System.out.println(LINE);
        System.out.println("Your Activities are sorted by time now!");
        listItems();
    }

    private static void view(String[] userInputArray) {
        if (list.isEmpty()) {
            System.out.println(LINE);
            System.out.println("Itinerary is Empty!");
            System.out.println(LINE);
            return;
        }
        if (userInputArray.length == 1) {
            listItems();
            return;
        }
        String date = userInputArray[1];
        ArrayList<Activity> matches = new ArrayList<>();
        for (Activity a : list) {
            if (date.equals(a.getDate())) {
                matches.add(a);
            }
        }
        if (matches.isEmpty()) {
            System.out.println(LINE);
            System.out.println("No activities found for " + date + ".");
            System.out.println(LINE);
            return;
        }
        System.out.println(LINE);
        System.out.println("Itinerary for " + date + ":");
        for (int i = 0; i < matches.size(); i++) {
            Activity a = matches.get(i);
            System.out.println((i + 1) + ". ");
            System.out.println("Date: " + a.getDate());
            System.out.println("Time: " + a.getTime());
            System.out.println("Description: " + a.getDescription());
            System.out.println("Cost: $" + a.getCost());
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
            String userInput = handleUserInput();
            //detect EOF
            if (userInput == null) {
                break;
            }
        }
    }

}

