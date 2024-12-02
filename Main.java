import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 54321;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) {


        if (args.length != 3) {
            System.out.println("Usage: java SMTPClient <victims_file> <messages_file> <number_of_groups>");
            return;
        }

        String victimsFile = args[0];
        String messagesFile = args[1];
        int numberOfGroups = Integer.parseInt(args[2]);

        List<String> victims = readLinesFromFile(victimsFile);
        List<String> messages = readLinesFromFile(messagesFile);

        if (!validateInput(victims, messages, numberOfGroups)) {
            return;
        }

        List<List<String>> groups = formGroups(victims, numberOfGroups);

        for (List<String> group : groups) {
            String sender = group.get(0);
            List<String> receivers = group.subList(1, group.size());
            String message = selectRandomMessage(messages);

            System.out.println();

            sendEmail(sender, receivers, message);
        }

        System.out.println("Done!");
    }

    private static List<String> readLinesFromFile(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename), CHARSET);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            return new ArrayList<>();
        }
    }

    private static boolean validateInput(List<String> victims, List<String> messages, int numberOfGroups) {
        if (victims.isEmpty() || messages.isEmpty() || numberOfGroups <= 0) {
            System.out.println("Invalid input: check victims file, messages file, and number of groups.");
            return false;
        }

        if (numberOfGroups * 2 > victims.size()) {
            System.out.println("Not enough victims for the number of groups.");
            return false;
        }

        return true;
    }

    private static List<List<String>> formGroups(List<String> victims, int numberOfGroups) {
        List<List<String>> groups = new ArrayList<>();
        Random random = new Random();

        List<String> remainingVictims = new ArrayList<>(victims);

        for (int i = 0; i < numberOfGroups; i++) {
            int groupSize = random.nextInt(4) + 2;
            List<String> group = new ArrayList<>();

            for (int j = 0; j < groupSize; j++) {
                if (remainingVictims.isEmpty()) {
                    break;
                }
                int victimIndex = random.nextInt(remainingVictims.size());
                group.add(remainingVictims.remove(victimIndex));
            }

            groups.add(group);
        }

        return groups;
    }

    private static String selectRandomMessage(List<String> messages) {
        Random random = new Random();
        return messages.get(random.nextInt(messages.size()));
    }

    private static void sendEmail(String sender, List<String> receivers, String message) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET))
        ) {
            // Send EHLO command
            writer.write("EHLO " + sender.split("@")[1] + "\r\n");
            writer.flush();
            readResponse(reader);

            // Send MAIL FROM command
            writer.write("MAIL FROM:<" + sender + ">\r\n");
            writer.flush();
            readResponse(reader);

            // Send RCPT TO commands
            for (String receiver : receivers) {
                writer.write("RCPT TO:<" + receiver + ">\r\n");
                writer.flush();
                readResponse(reader);
            }

            // Send DATA command
            writer.write("DATA\r\n");
            writer.flush();
            readResponse(reader);

            // Send email content
            writer.write("Subject: Prank\r\n");
            writer.write(message + "\r\n");
            writer.write(".\r\n");
            writer.flush();
            readResponse(reader);

            // Send QUIT command
            writer.write("QUIT\r\n");
            writer.flush();
            readResponse(reader);

        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private static void readResponse(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Received: " + line);
            if (line.startsWith("250 ") || line.startsWith("354 ") || line.startsWith("221 ")) {
                break;
            }
        }
    }
}
