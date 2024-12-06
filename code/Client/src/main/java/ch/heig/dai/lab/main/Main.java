package ch.heig.dai.lab.main;

import java.io.*;
import java.util.List;

public class Main {

    final static String HOST = "localhost";
    final static int PORT = 1025;

    final static String victimsFile ="src/main/java/ch/heig/dai/lab/files/victims.txt";
    final static String messagesFile = "src/main/java/ch/heig/dai/lab/files/messages.txt";
    
    public static void main(String[] args) {

        try {
            int numberOfGroups = Integer.parseInt(args[0]);

            if (numberOfGroups <= 0) {
                throw new IllegalArgumentException("number of groups must be greater than 0");
            }

            List<String> victims = LoadFiles.loadVictims(victimsFile);
            List<List<String>> groups = LoadFiles.formGroups(victims, numberOfGroups);
            List<Message> messages = LoadFiles.loadMessages(messagesFile);

            for (List<String> group : groups) {
                String sender = group.get(0);
                List<String> receivers = group.subList(1, group.size());
                Message message = Message.selectRandomMessage(messages);

                runSMTP.run(sender, receivers, message, HOST, PORT);
            }

            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}