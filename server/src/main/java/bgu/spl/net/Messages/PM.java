package bgu.spl.net.Messages;

import java.util.LinkedList;

public class PM extends Message{
    private String userName;
    private String content;
    private String dateTime;
    private LinkedList<Byte> bytelist;
    public PM(String u, String c, String dt){
        userName = u;
        content = c;
        dateTime = dt;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void filterWords(String filter) {
        content = content.replaceAll(filter,"<filter>");
    }
}
