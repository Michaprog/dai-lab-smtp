package ch.heig.dai.lab.smtp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.*;
import java.util.LinkedList;

public class Client {

    LinkedList<Group> groups;
    LinkedList<Message> messages;

    public static void main(String[] args) {

        try {

            Client client = new Client();

            client.loadVictims("src/main/java/ch/heig/dai/lab/smtp/victims.txt");
            client.loadMessages("src/main/java/ch/heig/dai/lab/smtp/messages.json");

            client.getGroups().toString();
            client.getMessages().toString();

            //TODO: Impliment the SMTP client


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadVictims(String filePath) throws IOException {
        LinkedList<Victim> totalvictims = new LinkedList<>();

        try{BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                totalvictims.add(new Victim(line));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        groups = new LinkedList<>();
        Group currentGroup = new Group();

        for (Victim victim : totalvictims) {
            if(currentGroup.getSender() == null){
                currentGroup.AddSender(Sender.castToSender(victim));
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

    private void loadMessages(String filePath) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    messages.add(new Message(parts[0], parts[1]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Group> getGroups() {
        return groups;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

}