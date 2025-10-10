package seedu.duke;

public class Activity {
    protected Time dateTime;
    protected String description;
    protected String cost;

    public Activity(String date, String time, String description, String cost) {
        this.dateTime = new Time(date, time);
        this.description = description;
        this.cost = cost;
    }

    public String getDate() {
        return dateTime.getDateString();
    }

    public void setDate(String date) {
        this.dateTime = new Time(date, this.getTime());
    }

    public String getTime() {
        return dateTime.getTimeString();
    }

    public void setTime(String time) {
        this.dateTime = new Time(this.getDate(), time);
    }

    public Time getDateTimeObject() {
        return dateTime;
    }

    public void setDateTimeObject(Time dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
