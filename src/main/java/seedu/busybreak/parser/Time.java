package seedu.busybreak.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author msc-123456
/**
 * Handles date and time parsing and management,
 * supporting datetime formatting and validation.
 */
public class Time {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final Logger logger = Logger.getLogger(Time.class.getName());
    private LocalDateTime dateTime;

    /**
     * Constructs a Time instance,
     * parsing date and time strings to initialize a LocalDateTime object.
     * @param date Date string (format: yyyy-MM-dd)
     * @param time Time string (format: HH:mm)
     */
    public Time(String date, String time) {
        assert date != null : "Date parameter cannot be null";
        assert time != null : "Time parameter cannot be null";
        assert !date.trim().isEmpty() : "Date parameter cannot be empty";
        assert !time.trim().isEmpty() : "Time parameter cannot be empty";

        logger.log(Level.INFO, "Creating Time object with date: "
                + date + ", and time: " + time);

        this.dateTime = parseDateTime(date, time);
    }

    private LocalDateTime parseDateTime(String dateStr, String timeStr) {
        try {
            logger.log(Level.FINE, "Parsing date: " + dateStr
                    + ", and time: " + timeStr);

            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalTime time = LocalTime.parse(timeStr, TIME_FORMATTER);
            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "DateTime parsing failed for date: " + dateStr
                    + ", and time: " + timeStr, e);
            throw new IllegalArgumentException("Invalid date or time.\n"
                    + "Please use valid date and valid time in format:\n"
                    + "yyyy-MM-dd for date and HH:mm for time.");
        }
    }

    /**
     * Gets the LocalDateTime object.
     * @return LocalDateTime object containing date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the date part (LocalDate).
     * @return LocalDate object
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Gets the time part (LocalTime).
     * @return LocalTime object
     */
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    /**
     * Gets the formatted date string (yyyy-MM-dd).
     * @return Date string
     */
    public String getDateString() {
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * Gets the formatted time string (HH:mm).
     * @return Time string
     */
    public String getTimeString() {
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * Gets the formatted datetime string (yyyy-MM-dd HH:mm).
     * @return Datetime string
     */
    public String getDateTimeString() {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return getDateTimeString();
    }
}
