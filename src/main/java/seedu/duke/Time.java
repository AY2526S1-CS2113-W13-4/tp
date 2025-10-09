package seedu.duke;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {
    private LocalDateTime dateTime;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Time(String date, String time) {
        this.dateTime = parseDateTime(date, time);
    }

    private LocalDateTime parseDateTime(String dateStr, String timeStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalTime time = LocalTime.parse(timeStr, TIME_FORMATTER);
            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date or time format. Use yyyy-MM-dd for date and HH:mm for time.");
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Time time = (Time) obj;
        return dateTime.equals(time.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
