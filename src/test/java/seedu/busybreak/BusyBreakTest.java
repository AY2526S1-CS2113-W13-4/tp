package seedu.busybreak;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import seedu.busybreak.activity.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusyBreakTest {
    @Test
    public void addActivityDataToList() {
        //test basic adding to list
        BusyBreak.list = new ArrayList<>();
        Activity a = new Activity("2025-10-09", "09:00", "visit museum", "67");
        BusyBreak.list.add(a);
        assertEquals(1, BusyBreak.list.size());
        assertTrue(BusyBreak.list.contains(a));
    }

    @Test
    public void editActivityDataInList() {
        BusyBreak.list = new ArrayList<>();
        Activity a = new Activity("2025-10-10", "11:11", "film tiktok video", "69");
        Activity b = new Activity("2025-11-11", "11:11", "farm aura", "67");
        BusyBreak.list.add(a);
        BusyBreak.list.set(0, b);
        assertEquals(1, BusyBreak.list.size());
        assertTrue(BusyBreak.list.contains(b));
    }

    @Test
    public void deleteActivityDataFromList() {
        BusyBreak.list = new ArrayList<>();
        Activity a = new Activity("2025-10-10", "11:11", "film tiktok video", "69");
        Activity b = new Activity("2025-11-11", "11:11", "farm aura", "67");
        BusyBreak.list.add(a);
        BusyBreak.list.add(b);
        BusyBreak.list.remove(a);
        assertEquals(1, BusyBreak.list.size());
        assertTrue(BusyBreak.list.contains(b));
    }

    @Test
    void viewSpecificDate() {
        BusyBreak.list = new ArrayList<>();
        Activity b = new Activity("2025-10-09", "09:00", "visit museum", "67");
        Activity c = new Activity("2025-10-10", "14:00", "go shopping", "100");
        BusyBreak.list.add(b);
        BusyBreak.list.add(c);

        String targetDate = "2025-10-09";
        ArrayList<Activity> result = new ArrayList<>();
        for (Activity a : BusyBreak.list) {
            if (a.getDate().equals(targetDate)) {
                result.add(a);
            }
        }

        assertEquals(1, result.size());
        assertEquals("visit museum", result.get(0).getDescription());
    }
}
