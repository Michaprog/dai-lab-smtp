package ch.heig.dai.lab.smtp;

public class Message {
    private int id;
    private String from;
    private String to;
    private String subject;
    private String body;
    private static int idCounter = 0;

    public Message(String from, String to, String subject, String body){
        id = idCounter++;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Message(String subject, String body){
        id = idCounter++;
        this.from = "";
        this.to = "";
        this.subject = subject;
        this.body = body;
    }

    public String getMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("From: " + from + "\n");
        sb.append("To: " + to + "\n");
        sb.append("Subject: " + subject + "\n");
        sb.append(body + "\n");

        return sb.toString();
    }

    public void setFrom(String from){
        this.from = from;
    }

    public void setTo(String to){
        this.to = to;
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
