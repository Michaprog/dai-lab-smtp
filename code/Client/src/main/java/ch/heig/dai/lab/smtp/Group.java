package ch.heig.dai.lab.smtp;

import java.util.LinkedList;

public class Group {
    private int id;
    private Sender sender;
    private LinkedList<Victim> victims;
    private static int idCounter = 0;
    private static int numberByGroup = 6; // default value

    public Group() {
        id = idCounter++;
        sender = null;
        victims = new LinkedList<>();
    }

    public void AddSender(Sender sender) {
        this.sender = sender;
    }

    public void AddVictim(Victim victim) {
        if(this.victims.size() < numberByGroup) {
            victims.add(victim);
        }
    }

    public Sender getSender() {
        return sender;
    }

    public LinkedList<Victim> getVictims() {
        return victims;
    }

    public int getVictimsCount() {
        return victims.size();
    }

    public void setNumberByGroup(int numberByGroup) {
        this.numberByGroup = (numberByGroup - 1) ; // exclure le sender
    }

    public int getNumberByGroup() {
        return numberByGroup;
    }

    public int getId() {
        return id;
    }

}
