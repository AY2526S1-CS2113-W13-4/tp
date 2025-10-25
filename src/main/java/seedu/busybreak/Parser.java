package seedu.busybreak;

import java.util.Arrays;
import java.util.Scanner;

public class Parser {
    static GetCommand parseUserInput() {
        Scanner in = getScanner();
        if (in == null) {
            return null;
        }
        String userInput;

        userInput = in.nextLine();
        String[] userInputArray = userInput.split(" ");
        String command = userInputArray[0]; //read first word of input as command
        return new GetCommand(userInput, userInputArray, command);
    }

    private static Scanner getScanner() {
        Scanner in = new Scanner(System.in);

        if (!in.hasNextLine()) {
            Ui.showLine();
            System.out.println("Please Input a Command.");
            Ui.showLine();
            return null;
        }
        return in;
    }

    public static ParseActivityData getParseActivityData(String[] userInputArray) {

        try {
            //truncate the command from userInput
            String[] parsedUserInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);
            String parsedUserInput = String.join(" ", parsedUserInputArray).trim();

            assert !parsedUserInput.isEmpty() : "User input cannot be empty.";

            String[] parseDate = getParsedDate(parsedUserInput);
            String[] parseTime = getParsedTime(parseDate);
            String[] parseDescription = getParsedDescription(parseTime);
            String[] parseCost = getParsedCost(parseDescription);

            String date = parseTime[0].trim();
            String time = parseDescription[0].trim();
            String description = parseCost[0].trim();
            String cost = parseCost[1].trim();
            checkEmptyFields(date, time, description, cost);

            assert !date.isEmpty() : "Date cannot be empty.";
            assert !time.isEmpty() : "Time cannot be empty.";
            assert !description.isEmpty() : "Description cannot be empty.";
            assert !cost.isEmpty() : "Cost cannot be empty.";

            return new ParseActivityData(date, time, description, cost);
        } catch (IllegalArgumentException | AssertionError e) {
            Ui.showLine();
            System.out.println(e.getMessage());
            Ui.showLine();
            return null;
        }
    }

    private static void checkEmptyFields(String date, String time, String description, String cost) {
        if (date.isEmpty() || time.isEmpty() || description.isEmpty() || cost.isEmpty()) {
            throw new IllegalArgumentException("User input is invalid! One or more fields :" +
                    " date, time, description or cost is empty.");
        }
    }

    private static String[] getParsedCost(String[] parseDescription) {
        String[] parseCost = parseDescription[1].split("c/", 2);
        if (parseCost.length != 2) {
            throw new IllegalArgumentException("Missing cost! Set one with c/");
        }
        return parseCost;
    }

    private static String[] getParsedDescription(String[] parseTime) {
        String[] parseDescription = parseTime[1].split("desc/", 2);
        if (parseDescription.length != 2) {
            throw new IllegalArgumentException("Missing description! Set one with desc/");
        }
        return parseDescription;
    }

    private static String[] getParsedTime(String[] parseDate) {
        String[] parseTime = parseDate[1].split("t/", 2);
        if (parseTime.length != 2) {
            throw new IllegalArgumentException("Missing time! Set one with t/");
        }
        return parseTime;
    }

    private static String[] getParsedDate(String parsedUserInput) {
        String[] parseDate = parsedUserInput.split("d/", 2);
        if (parseDate.length != 2) {
            throw new IllegalArgumentException("Missing date! Set one with d/");
        }
        return parseDate;
    }

    public record ParseActivityData(String date, String time, String description, String cost) {
    }

    record GetCommand(String userInput, String[] userInputArray, String command) {
    }
}
