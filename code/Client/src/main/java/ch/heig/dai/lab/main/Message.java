package ch.heig.dai.lab.main;

public class Message {
    private String from;
    private String to;
    private String subject;
    private String body;

    public Message(String from, String to, String subject, String body){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Message(String subject, String body) {
        this.from = "";
        this.to = "";
        this.subject = subject;
        this.body = body;
    }

    public String getMessage() {
        return body;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject(){
        return subject;
    }

    public String getBody(){
        return body;
    }
}
