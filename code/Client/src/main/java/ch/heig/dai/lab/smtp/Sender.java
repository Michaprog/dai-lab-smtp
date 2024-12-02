package ch.heig.dai.lab.smtp;

public class Sender extends Victim {
    private String username;
    static private String password;
    static private int port = 1234;
    static private String host = "TODO";

    public Sender(String email, String username, String password) {
        super(email);
        this.username = username;
    }

    public static Sender castToSender(Victim victim){
        return new Sender(victim.getEmail(), victim.getEmail().substring(0,6), password); // pas sur de la methodologie pour le username
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    int getPort() {
        return port;
    }

    String getHost() {
        return host;
    }

}
