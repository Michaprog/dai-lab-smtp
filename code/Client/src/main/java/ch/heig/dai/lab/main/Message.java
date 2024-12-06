package ch.heig.dai.lab.main;

import java.util.List;
import java.util.Random;

/**
 * Represents a message composed by a header and a body
 */
public class Message {

    private String subject;
    private String body;

    /**
     * Message constructor
     * @param subject header
     * @param body content of the message
     */
    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    /**
     * Selects a random message from the list
     * @param messages list of prank messages
     * @return
     */
    protected static Message selectRandomMessage(List<Message> messages) {
        Random random = new Random();
        int randomIndex = random.nextInt(messages.size());
        return messages.get(randomIndex);
    }

    public String getMessage() {
        return body;
    }

    public String getSubject(){
        return subject;
    }
}
