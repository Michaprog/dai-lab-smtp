package ch.heig.dai.lab.smtp;

public class Sender extends Victim {
    private String username;

    public Sender(String email, String username) {
        super(email);
        this.username = username;
    }

    public static Sender castToSender(Victim victim){
        return new Sender(victim.getEmail(), victim.getEmail().substring(0,6)); // pas sur de la methodologie pour le username
    }

}
