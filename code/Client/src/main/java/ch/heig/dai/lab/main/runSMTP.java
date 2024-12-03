package ch.heig.dai.lab.main;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class runSMTP {

    public static void run(String sender, List<String> victims, Message message, String HOST, int PORT) {
            try (
                Socket socket = new Socket(HOST, PORT);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))
            ) {
                reader.readLine();
                // Send EHLO command
                writer.write("EHLO " + HOST + "\r\n");
                writer.flush();

                // Send MAIL FROM command
                writer.write("MAIL FROM: <" + sender + ">\r\n");
                writer.flush();
                reader.readLine();

                // Send RCPT TO commands
                for (String receiver : victims) {
                    writer.write("RCPT TO: <" + receiver + ">\r\n");
                    writer.flush();
                    reader.readLine();
                }

                // Send DATA command
                writer.write("DATA\r\n");
                writer.flush();
                reader.readLine();

                // Send email content
                writer.write("From: " + sender + "\r\n");
                writer.write("To: " + String.join(", ", victims) + "\r\n");
                writer.write("Subject: " + message.getSubject() + "\r\n");
                writer.write("\r\n");
                writer.write(message.getMessage() + "\r\n");
                writer.write(".\r\n");
                writer.flush();
                reader.readLine();

                // Send QUIT command
                writer.write("QUIT\r\n");
                writer.flush();
                reader.readLine();

            } catch (IOException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
}