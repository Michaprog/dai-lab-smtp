package ch.heig.dai.lab.smtp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmtpClient {

    private static final String HOST = "localhost";
    private static final int PORT = 1025;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

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

    public static String selectRandomMessage(List<Message> messages) {
        Random random = new Random();
        return messages.get(random.nextInt(messages.size())).getMessage();
    }

    public static void sendEmail(String sender, List<Victim> receivers, String message) {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET))
        ) {
            // Send EHLO command
            writer.write("EHLO " + HOST + "\r\n");
            writer.flush();
            readResponse(reader);

            // Send MAIL FROM command
            writer.write("MAIL FROM:<" + sender + ">\r\n");
            writer.flush();
            readResponse(reader);

            // Send RCPT TO commands
            for (Victim receiver : receivers) {
                writer.write("RCPT TO:<" + receiver.getEmail() + ">\r\n");
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
