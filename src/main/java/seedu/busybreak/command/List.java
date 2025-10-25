package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;

public class List {
    public static void listItems() {

        if (BusyBreak.list.isEmpty()) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Itinerary is Empty!");
            System.out.println(BusyBreak.LINE);
            return;
        }
        System.out.println(BusyBreak.LINE);
        for (int index = 0; index < BusyBreak.list.size(); index++) {
            printItems(index);
        }
    }

    private static void printItems(int index) {
        System.out.println((index + 1) + ". ");
        System.out.println("Date: " + BusyBreak.list.get(index).getDate());
        System.out.println("Time: " + BusyBreak.list.get(index).getTime());
        System.out.println("Description: " + BusyBreak.list.get(index).getDescription());
        System.out.println("Cost: $" + BusyBreak.list.get(index).getCost());
        System.out.println(BusyBreak.LINE);
    }
}
