package ch.heig.dai.lab.smtp;

public class Victim {
    private String email;

    public Victim(String email) {
        if(!email.contains("@")){
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

}
