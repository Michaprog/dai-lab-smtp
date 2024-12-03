package ch.heig.dai.lab.main;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EditFiles {

    protected static Message selectRandomMessage(List<Message> messages) {
        Random random = new Random();
        int randomIndex = random.nextInt(messages.size());
        return messages.get(randomIndex);
    }
    
    protected static List<String> loadVictims(String filePath) throws IOException {
        List<String> totalvictims = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                totalvictims.add(line); // No need to create a new String object
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalvictims;
    }

    protected static List<List<String>> formGroups(List<String> victims, int numberOfGroups) {
        List<List<String>> groups = new ArrayList<>();
        Random random = new Random();
        List<String> remainingVictims = new ArrayList<>(victims);
    
        for (int i = 0; i < numberOfGroups; i++) {
            int groupSize = Math.min(random.nextInt(remainingVictims.size()) + 1, 5);
            if (groupSize > remainingVictims.size()) {
                groupSize = remainingVictims.size();
            }
            List<String> group = new ArrayList<>(remainingVictims.subList(0, groupSize));
            groups.add(group);
            remainingVictims = remainingVictims.subList(groupSize, remainingVictims.size());
        }
    
        // If there's only one person left, add them to the first group
        if (!remainingVictims.isEmpty()) {
            groups.get(0).addAll(remainingVictims);
        }
    
        return groups;
    }
    
    protected static List<Message> loadMessages(String filePath) throws IOException {
    
        List<Message> messages = new ArrayList<>();
    
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
    
        return messages;
    }
}