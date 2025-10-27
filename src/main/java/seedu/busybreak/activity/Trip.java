package seedu.busybreak.activity;

import seedu.busybreak.parser.Time;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author msc-123456
public class Trip {
    private static final Logger logger = Logger.getLogger(Trip.class.getName());
    private Time startDateTime;
    private Time endDateTime;
    private String transport;

    public Trip(String startDate, String startTime, String endDate, String endTime, String transport) {
        this.startDateTime = new Time(startDate, startTime);
        this.endDateTime = new Time(endDate, endTime);
        this.transport = transport;

        if (!isStartTimeBeforeEndTime()) {
            logger.log(Level.WARNING, "Start time is not before end time");
            throw new IllegalArgumentException("Start time must be before end time");
        }

        logger.log(Level.INFO, String.format(
                "Created Trip with Start: %s %s | End: %s %s | Transport: %s",
                startDate, startTime, endDate, endTime, transport
        ));
    }

    public boolean isStartTimeBeforeEndTime() {
        LocalDateTime start = startDateTime.getDateTime();
        LocalDateTime end = endDateTime.getDateTime();
        return start.isBefore(end);
    }

    public Time getStartDateTime() {
        return startDateTime;
    }

    public Time getEndDateTime() {
        return endDateTime;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getStartDate() {
        return startDateTime.getDateString();
    }

    public String getStartTime() {
        return startDateTime.getTimeString();
    }

    public String getEndDate() {
        return endDateTime.getDateString();
    }

    public String getEndTime() {
        return endDateTime.getTimeString();
    }

}
