package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws Exception {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Field listField = Duke.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.clear();
    }

    @Test
    void setByTime_setAndPrint() throws Exception {
        Field listField = Duke.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);

        Activity a1 = new Activity("2025-01-02", "10:00",
                "Activity A", "10");
        Activity a2 = new Activity("2025-01-01", "09:00",
                "Activity B", "20");
        Activity a3 = new Activity("2025-01-01", "08:00",
                "Activity C", "30");

        list.add(a1);
        list.add(a2);
        list.add(a3);

        var method = Duke.class.getDeclaredMethod("setByTime");
        method.setAccessible(true);
        method.invoke(null);

        String output = outputStream.toString();

        int indexC = output.indexOf("Activity C");
        int indexB = output.indexOf("Activity B");
        int indexA = output.indexOf("Activity A");

        assertTrue(indexC < indexB && indexB < indexA,
                "Activities should be printed in chronological order");

        assertTrue(output.contains("Your Activities are sorted by time now!"));
    }

    @Test
    void setByTime_forEmptyList() throws Exception {
        Field listField = Duke.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.clear();

        var method = Duke.class.getDeclaredMethod("setByTime");
        method.setAccessible(true);
        method.invoke(null);

        String output = outputStream.toString();

        assertTrue(output.contains("Itinerary is Empty! Nothing to sort."));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
