package seedu.duke;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DukeTest {
    @Test
    public void addActivityDataToList() {
        //test basic adding to list
        Duke.list = new ArrayList<>();
        Activity a = new Activity("2025-10-09", "09:00", "visit museum", "67");
        Duke.list.add(a);
        assertEquals(1, Duke.list.size());
        assertTrue(Duke.list.contains(a));
    }

    @Test
    void viewSpecificDate() {
        Duke.list = new ArrayList<>();
        Activity b = new Activity("2025-10-09", "09:00", "visit museum", "67");
        Activity c = new Activity("2025-10-10", "14:00", "go shopping", "100");
        Duke.list.add(b);
        Duke.list.add(c);

        String targetDate = "2025-10-09";
        ArrayList<Activity> result = new ArrayList<>();
        for (Activity a : Duke.list) {
            if (a.getDate().equals(targetDate)) {
                result.add(a);
            }
        }

        assertEquals(1, result.size());
        assertEquals("visit museum", result.get(0).getDescription());
    }
}
