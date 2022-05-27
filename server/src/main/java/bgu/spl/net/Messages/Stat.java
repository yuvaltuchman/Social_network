package bgu.spl.net.Messages;

import java.util.LinkedList;

public class Stat extends Message {
    private LinkedList<String> statUsers;
    public Stat(){
        statUsers = new LinkedList<String>();
    }

    public void addUser(String user) {
        this.statUsers.add(user);
    }

    public LinkedList<String> getStatUsers() {
        return statUsers;
    }
}
