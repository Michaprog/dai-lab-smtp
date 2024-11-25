package ch.heig.dai.lab.smtp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.*;
import java.util.LinkedList;


public class Client {

    LinkedList<Group> groups;

    public static void main(String[] args) {

        try {

            Client client = new Client();

            client.loadVictims("victims.json");

            //TODO: Impliment the SMTP client


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loadVictims(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream(filePath);
        LinkedList<Person> totalvictims = objectMapper.readValue(inputStream, LinkedList.class);
        inputStream.close();

        groups = new LinkedList<>();
        Group currentGroup = new Group();

        for (Person victim : totalvictims) {
            if(currentGroup.getSender() == null){
                currentGroup.AddSender((Sender) victim);
            }
            else if (currentGroup.getVictimsCount() < 4) {
                currentGroup.AddVictim(victim);
            }
            else {
                groups.add(currentGroup);
                currentGroup = new Group();
            }
        }

        if (currentGroup.getSender() != null) {
            groups.add(currentGroup);
        }
    }

}

class Group {
    private int id;
    private Sender sender;
    private LinkedList<Person> victims;
    private static int idCounter = 0;

    public Group() {
        id = idCounter++;
        sender = null;
        victims = new LinkedList<>();
    }

    void AddSender(Sender sender) {
        this.sender = sender;
    }

    void AddVictim(Person victim) {
        if(this.victims.size() < 4) {
            victims.add(victim);
        }
    }

    Sender getSender() {
        return sender;
    }

    LinkedList<Person> getVictims() {
        return victims;
    }

    int getVictimsCount() {
        return victims.size();
    }

}

class Sender extends Person{
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