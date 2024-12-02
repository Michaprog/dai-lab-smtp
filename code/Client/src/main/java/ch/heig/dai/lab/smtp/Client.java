package ch.heig.dai.lab.smtp;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Client {

    private LinkedList<Group> groups;
    private LinkedList<Message> messages;

    public static void main(String[] args) {

        try {

            Client client = new Client();
            Random random = new Random();

            client.loadVictims("src/main/java/ch/heig/dai/lab/smtp/victims.txt");
            client.loadMessages("src/main/java/ch/heig/dai/lab/smtp/messages.txt");

            ServerOI.getConnection();

            for (Group group : client.getGroups()) {
                int messageIndex = random.nextInt(client.getMessages().size());
                Message message = client.getMessages().get(messageIndex);
                ServerOI.sendMail(group, message);
            }

            ServerOI.closeConnection();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVictims(String filePath) throws IOException {
        LinkedList<Victim> totalvictims = new LinkedList<>();

        try{BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()){
                    totalvictims.add(new Victim(line));
                } else {
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace(); // Might inditace a missing @ in the email
        }

        groups = new LinkedList<>();
        Group currentGroup = new Group();

        for (Victim victim : totalvictims) {
            if(currentGroup.getSender() == null){
                currentGroup.AddSender(Sender.castToSender(victim));
            }
            else if (currentGroup.getVictimsCount() < currentGroup.getNumberByGroup()) {
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

        messages = new LinkedList<>();

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