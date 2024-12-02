package ch.heig.dai.lab.smtp;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerOI {

    private static int port = 1025;
    private static String server = "localhost";
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void getConnection() {
        try{
            Socket socket = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try{
            in.close();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendMail(Group group, Message messgae){
        try{
            // Connection
            out.write("HELO " + server + "\r\n");
            if(in.readLine().startsWith("250")){
                System.out.println("Connection established for group number " + group.getId());
            } else {
                System.out.println("Connection not established for group number " + group.getId());
                throw(new Exception("Connection not established"));
            }

            // Sender
            out.write("MAIL FROM: " + group.getSender().getEmail() + "\r\n");
            if(in.readLine().startsWith("250")){
                System.out.println("Sender " + group.getSender().getEmail() + " found");
            } else {
                System.out.println("Sender " + group.getSender().getEmail() + " not found");
                throw(new Exception("Sender not found"));
            }

            // Recipients
            for (Victim victim : group.getVictims()) {
                out.write("RCPT TO: " + victim.getEmail() + "\r\n");
                if(in.readLine().startsWith("250")){
                    System.out.println("Recipient " + victim.getEmail() + " found");
                } else {
                    System.out.println("Recipient " + victim.getEmail() + " not found");
                }
            }

            // Data
            out.write("DATA\r\n");
            if(in.readLine().startsWith("354")){
                System.out.println("Data command accepted");
            } else {
                System.out.println("Data command not accepted");
                throw(new Exception("Data command not accepted"));
            }
            out.write("From: " + group.getSender().getEmail() + "\r\n");
            out.write("To: " + group.getVictims().toString() + "\r\n");
            out.write("Subject: " + messgae.getSubject() + "\r\n");
            out.write(messgae.getBody() + "\r\n");
            out.write(".\r\n");
            if(in.readLine().startsWith("250")){
                System.out.println("Mail sent for group number " + group.getId());
            } else {
                System.out.println("Mail not sent for group number " + group.getId());
                throw(new Exception("Mail not sent"));
            }
            out.write("QUIT\r\n");
            if(in.readLine().startsWith("221")){
                System.out.println("Connection closed for group number " + group.getId());
            } else {
                System.out.println("Connection not closed for group number " + group.getId());
                throw(new Exception("Connection not closed"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
