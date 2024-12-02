package ch.heig.dai.lab.smtp;

public class Sender extends Victim {
    private String username;
    private String password;
    static private int port = 1234;
    static private String host = "TODO";

    public Sender(String email, String username, String password) {
        super(email);
        this.username = username;
        this.password = password;
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
