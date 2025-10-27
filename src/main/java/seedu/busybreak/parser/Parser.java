package seedu.busybreak.parser;

import seedu.busybreak.Ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {
    public static GetCommand parseUserInput() {
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

    public static int parseActivityIndex(String activityIndexString) {
        return Integer.parseInt(activityIndexString) - 1;
    }

    public static ParseEditDetails parseEditActivityDetails(String[] userInputArray) {
        String[] inputDetailsArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
        String inputDetails = String.join(" ", inputDetailsArray).trim();
        String[] editDetails = inputDetails.split("\\s+(?=desc/|d/|t/|c/)");

        String date = null;
        String time = null;
        String description = null;
        String cost = null;
        boolean hasInvalidDetail = false;

        for (String editDetail : editDetails) {
            int slash = editDetail.trim().indexOf("/");
            String detailName = editDetail.trim().substring(0, slash);
            String detailValue = editDetail.trim().substring(slash + 1);

            switch (detailName) {
            case "c":
                cost = detailValue;
                break;
            case "desc":
                description = detailValue;
                break;
            case "t":
                time = detailValue;
                break;
            case "d":
                date = detailValue;
                break;
            default:
                hasInvalidDetail = true;
            }
        }

        return new ParseEditDetails(date, time, description, cost, hasInvalidDetail);
    }

    public static String parseFindInput(String[] userInput) {
        return Arrays.stream(userInput, 1, userInput.length).collect(Collectors.joining(" "));
    }


    public record ParseEditDetails(String date, String time, String description, String cost,
                                   boolean hasInvalidDetail) {
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

    public record GetCommand(String userInput, String[] userInputArray, String command) {
    }
}
