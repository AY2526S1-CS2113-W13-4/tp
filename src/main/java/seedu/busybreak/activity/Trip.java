package seedu.busybreak.activity;

import seedu.busybreak.parser.Time;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author msc-123456
/**
 * Indicates a travel activity, including information such as
 * the start and end times of the trip and the mode of transportation.
 */
public class Trip {
    private static final Logger logger = Logger.getLogger(Trip.class.getName());
    private Time startDateTime;
    private Time endDateTime;
    private String transport;

    /**
     * Creates a Trip.
     * @param startDate The start date of the trip, in format yyyy-MM-dd.
     * @param startTime The start time of the trip, in format HH:mm.
     * @param endDate The end date of the trip, in format yyyy-MM-dd.
     * @param endTime The end time of the trip, in format HH:mm.
     * @param transport The transportation of the trip.
     */
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

    /**
     * Checks if the trip's start time is before the end time.
     * @return True if start time is before end time, false otherwise.
     */
    public boolean isStartTimeBeforeEndTime() {
        LocalDateTime start = startDateTime.getDateTime();
        LocalDateTime end = endDateTime.getDateTime();
        return start.isBefore(end);
    }

    /**
     * Gets the start date-time object of the trip.
     * @return Time object containing start date and time.
     */
    public Time getStartDateTime() {
        return startDateTime;
    }

    /**
     * Gets the end date-time object of the trip.
     * @return Time object containing end date and time
     */
    public Time getEndDateTime() {
        return endDateTime;
    }

    /**
     * Gets the transportation method of the trip.
     * @return String representing transportation method
     */
    public String getTransport() {
        return transport;
    }

    /**
     * Gets the start date of the trip as a string.
     * @return Start date (format: yyyy-MM-dd)
     */
    public String getStartDate() {
        return startDateTime.getDateString();
    }

    /**
     * Gets the start time of the trip as a string.
     * @return Start time (format: HH:mm)
     */
    public String getStartTime() {
        return startDateTime.getTimeString();
    }

    /**
     * Gets the end date of the trip as a string.
     * @return End date (format: yyyy-MM-dd)
     */
    public String getEndDate() {
        return endDateTime.getDateString();
    }

    /**
     * Gets the end time of the trip as a string.
     * @return End time (format: HH:mm)
     */
    public String getEndTime() {
        return endDateTime.getTimeString();
    }

}
