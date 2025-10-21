package seedu.busybreak;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Time {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final Logger logger = Logger.getLogger(Time.class.getName());
    private LocalDateTime dateTime;

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
            throw new IllegalArgumentException("Invalid date or time format."
                    + " Use yyyy-MM-dd for date and HH:mm for time.");
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getDateString() {
        return dateTime.format(DATE_FORMATTER);
    }

    public String getTimeString() {
        return dateTime.format(TIME_FORMATTER);
    }

    public String getDateTimeString() {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return getDateTimeString();
    }
}
