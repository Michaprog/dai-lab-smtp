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

            client.loadVictims("src/main/java/ch/heig/dai/lab/smtp/victims.json");
            client.loadMessages("src/main/java/ch/heig/dai/lab/smtp/messages.json");

            //TODO: Impliment the SMTP client


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadVictims(String filePath) throws IOException {
        File jsonFile = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedList<Victim> totalvictims = objectMapper.readValue(jsonFile, LinkedList.class);

        groups = new LinkedList<Group>();
        Group currentGroup = new Group();

        for (Victim victim : totalvictims) {
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

    private void loadMessages(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream(filePath);
        messages = objectMapper.readValue(inputStream, LinkedList.class);
        inputStream.close();
    }

}