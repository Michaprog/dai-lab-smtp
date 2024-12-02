package ch.heig.dai.lab.smtp;

public class Message {
    private int id;
    private String from;
    private String to;
    private String subject;
    private String body;
    private static int idCounter = 0;

    public Message(String from, String to, String subject, String body) {
        id = idCounter++;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
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
