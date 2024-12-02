package ch.heig.dai.lab.smtp;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Client {

    List<Group> groups;
    List<Message> messages;

    public static void main(String[] args) {

        try {
            Main client = new Main();

            client.loadVictims("src/main/java/ch/heig/dai/lab/smtp/victims.txt");
            client.loadMessages("src/main/java/ch/heig/dai/lab/smtp/messages.txt");

            for (Group group : client.getGroups()) {
                System.out.println(group);
                String sender = group.getSender().getEmail();
                List<Victim> receivers = group.getVictims();
                String message = SmtpClient.selectRandomMessage(client.getMessages());

                SmtpClient.sendEmail(sender, receivers, message);
            }

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

    public List<Group> getGroups() {
        return groups;
    }

    public List<Message> getMessages() {
        return messages;
    }

}