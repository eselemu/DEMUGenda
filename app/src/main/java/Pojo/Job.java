package Pojo;

public class Job {
    private String id;
    private String title;
    private String description;
    private String subject;
    private int day;
    private int month;
    private int year;
    private boolean completed;

    public Job(){
        completed = false;
    }

    public Job(String id, String title, String description, String subject, int year, int month, int day) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.year = year;
        this.month = month;
        this.day = day;
        completed = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
