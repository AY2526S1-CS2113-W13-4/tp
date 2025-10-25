package seedu.busybreak;
import java.util.ArrayList;
public class Ui {

    private static final String LINE = "______________________________________________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        showLine();
    }

    public void showTerminateProgram() {
        showLine();
        System.out.println("Program Terminated.");
        showLine();
    }

    // list function
    public void showItineraryList(ArrayList<Activity> list) {
        showLine();
        for (int index = 0; index < list.size(); index++) {
            System.out.println((index + 1) + ". ");
            System.out.println("Date: " + list.get(index).getDate());
            System.out.println("Time: " + list.get(index).getTime());
            System.out.println("Description: " + list.get(index).getDescription());
            System.out.println("Cost: $" + list.get(index).getCost());
            System.out.println(LINE);
        }
    }

    // view function
    public void showInvalidDate(String date) {
        showLine();
        System.out.println("Invalid date: \"" + date + "\"");
        System.out.println("Please use the command in the following format: view YYYY-MM-DD");
        showLine();
    }

    // view function
    public void showEmptyItinerary() {
        showLine();
        System.out.println("Itinerary is Empty!");
        showLine();
    }

    // view function
    public void showInvalidViewFormat() {
        showLine();
        System.out.println("Invalid command format.");
        System.out.println("Please use the command in the following format: view YYYY-MM-DD");
        showLine();
    }

    // view function
    public void showNoActivitiesFor(String date) {
        showLine();
        System.out.println("No activities found for " + date + ".");
        showLine();

    }

    // view function
    public void showItineraryFor(String date, ArrayList<Activity> matches) {
        showLine();
        System.out.println("Itinerary for " + date + ":");
        for (int i = 0; i < matches.size(); i++) {
            Activity a = matches.get(i);
            System.out.println((i + 1) + ". ");
            System.out.println("Date: " + a.getDate());
            System.out.println("Time: " + a.getTime());
            System.out.println("Description: " + a.getDescription());
            System.out.println("Cost: $" + a.getCost());
            showLine();
        }
    }

}