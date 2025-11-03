package seedu.busybreak.parser;

import seedu.busybreak.Ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Handles the parsing of user inputs.
 */
public class Parser {
    /**
     * Parse user input from the command line.
     * Reads a line of user input, trims trailing whitespaces.
     * Splits user input.
     *
     * @return GetCommand record which contains userInput,userInputArray and command, or null if no input.
     *
     */
    public static GetCommand parseUserInput() {
        Scanner in = getScanner();
        if (in == null) {
            return null;
        }
        String userInput;

        userInput = in.nextLine();

        userInput = userInput.trim();
        String[] userInputArray = userInput.split("\\s+");
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

    private static boolean hasAtLeastOneEditField(String[] userInputArray) {
        for(int i = 2; i < userInputArray.length; i++){
            String input = userInputArray[i].trim();
            if (input.startsWith("d/") || input.startsWith("t/") ||
                    input.startsWith("desc/") || input.startsWith("c/")){
                return true;
            }
        }
        return false;
    }

    public static void checkValidDeleteInput(String[] userInputArray) {
        if (userInputArray.length == 1) {
            throw new IllegalArgumentException("Input must contain a valid index to be deleted.");
        }
    }

    private static void checkValidEditInput(String[] userInputArray) {
        if (userInputArray.length == 1 || !hasAtLeastOneEditField(userInputArray)) {
            throw new IllegalArgumentException("Input must contain a valid index and valid fields to be edited.");
        }
    }

    private static String[] getEditDetails(String[] userInputArray) {
        String[] inputDetailsArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
        String inputDetails = String.join(" ", inputDetailsArray).trim();
        return inputDetails.split("\\s+(?=desc/|d/|t/|c/)");
    }

    private static String extractDetailValue(String editDetail) {
        int slash = editDetail.trim().indexOf("/");
        if (slash <= 0) {
            return null;
        }

        String detailValue = editDetail.trim().substring(slash + 1).trim();
        return detailValue.isEmpty() ? null : detailValue;
    }

    private static String extractDetailName(String editDetail) {
        int slash = editDetail.trim().indexOf("/");
        if (slash <= 0) {
            return null;
        }

        return editDetail.trim().substring(0, slash).trim();
    }

    private static boolean isNumeric(String str) {
        try{
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

     private static ParseEditDetails processEditDetails(String[] editDetails) {
         String date = null;
         String time = null;
         String description = null;
         String cost = null;
         boolean hasInvalidDetail = false;

         for (String editDetail : editDetails) {
             String detailName = extractDetailName(editDetail);
             String detailValue = extractDetailValue(editDetail);

             if (detailName == null || detailValue == null) {
                 hasInvalidDetail = true;
                 continue;
             }

             switch (detailName) {
                 case "c":
                     if (isNumeric(detailValue) && Double.parseDouble(detailValue) >= 0) {
                         cost = detailValue;
                     } else {
                         hasInvalidDetail = true;
                     }
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

    public static int parseActivityIndex(String activityIndexString) {
        return Integer.parseInt(activityIndexString) - 1;
    }

    public static ParseEditDetails parseEditActivityDetails(String[] userInputArray) {
        try {
            checkValidEditInput(userInputArray);
            String[] editDetails = getEditDetails(userInputArray);
            return processEditDetails(editDetails);
        } catch (IllegalArgumentException e) {
            Ui.showLine();
            System.out.println(e.getMessage());
            Ui.showLine();
            return null;
        }
    }

    /**
     * Parse the search keyword from user input for the find command.
     *
     * @param userInput Array of strings containing the command and
     *                  search keyword.
     * @return parsed search keyword with command truncated from input.
     *
     */
    public static String parseFindInput(String[] userInput) {
        return Arrays.stream(userInput, 1, userInput.length).collect(Collectors.joining(" "));
    }

    public record ParseEditDetails(String date, String time, String description,
                                   String cost, boolean hasInvalidDetail) {
    }

    /**
     * Parse user input from the command line.
     * Extracts date,time,description and cost fields.
     * Ensures proper format of required fields.
     *
     * @param userInputArray array of strings containing the command
     *                       and the respective activity fields.
     * @return ParseActivityData record containing date,time,description and cost  or null if fields are invalid.
     */
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

            if (description.contains("|")) {
                throw new IllegalArgumentException("Description cannot contain the '|' character.");
            }

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

    /**
     * Contains parsed activity data.
     *
     * @param date        Activity date.
     * @param time        Activity time.
     * @param description Activity description.
     * @param cost        Activity cost.
     */
    public record ParseActivityData(String date, String time, String description, String cost) {
    }

    /**
     * Contains parsed command data.
     *
     * @param userInput      original user input.
     * @param userInputArray Array containing split user input.
     * @param command        the first word of the input which represents the user command.
     */
    public record GetCommand(String userInput, String[] userInputArray, String command) {
    }
}
