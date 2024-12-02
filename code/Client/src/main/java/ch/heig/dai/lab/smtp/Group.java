package ch.heig.dai.lab.smtp;

import java.util.LinkedList;

public class Group {
    private int id;
    private Sender sender;
    private LinkedList<Victim> victims;
    private static int idCounter = 0;

    public Group() {
        id = idCounter++;
        sender = null;
        victims = new LinkedList<>();
    }

    void AddSender(Sender sender) {
        this.sender = sender;
    }

    void AddVictim(Victim victim) {
        if(this.victims.size() < 4) {
            victims.add(victim);
        }
    }

    Sender getSender() {
        return sender;
    }

    LinkedList<Victim> getVictims() {
        return victims;
    }

    int getVictimsCount() {
        return victims.size();
    }

}
