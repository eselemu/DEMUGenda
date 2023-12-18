package Pojo;

public class Subject {
    private String id;
    private String subject;

    public Subject(){}

    public Subject(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
