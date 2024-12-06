package ch.heig.dai.lab.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadFiles {


    /**
     * Loads the list of victims (emails) from a .txt file
     * @param filePath the path to the file
     * @return the list of victims
     * @throws IOException 
     */
    protected static List<String> loadVictims(String filePath) throws IOException {
        List<String> totalvictims = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            while ((line = br.readLine()) != null) {
    
                if (!(line.contains("@") && line.endsWith(".com") && line.endsWith(".ch"))) {
                    throw new IllegalArgumentException("Invalid email address : " + line);
                }
                totalvictims.add(line); 
            }
        }
    
        return totalvictims;
    }

    /**
     * Loads the list of prank messages from a .txt file
     * @param filePath the path to the file
     * @return the list of messages, composed by a header and a body
     * @throws IOException
     */
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
            br.close();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return messages;
    }

    /**
     * Divides the list of victims into a certain number of groups (given by the user in command line)
     * @param victims the list of victims
     * @param numberOfGroups the number of groups
     * @return
     */
    protected static List<List<String>> formGroups(List<String> victims, int numberOfGroups) {
    List<List<String>> groups = new ArrayList<>();

    Random random = new Random();
    int index = 0;

    for (int i = 0; i < numberOfGroups; i++) {
        int groupSize = random.nextInt(3) + 3;
        List <String> group = new ArrayList<>();
        group.add(victims.get(index));

        for (int j = 1; j < groupSize; j++) { // Changed i to j
            group.add(victims.get((index + j) % victims.size()));
        }

        groups.add(group);
        index = (index + groupSize) % victims.size();
    }

    return groups;
}
}