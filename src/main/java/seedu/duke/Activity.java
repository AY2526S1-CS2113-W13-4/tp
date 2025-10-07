package seedu.duke;

public class Activity {
    protected String date;
    protected String time;
    protected String description;
    protected String cost;

    public Activity(String date, String time, String description, String cost) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
