package bgu.spl.net.Messages;

public class Notification extends Message {
    private byte notType;
    private String postingUser;
    private String content;
    private String dateTime;
    public Notification(short opCode, String postingUser, String content, String dateTime){
        if(opCode == 6){
            notType = 0;
        }
        else if(opCode == 5){
            notType = 1;
        }
        this.postingUser = postingUser;
        this.content = content;
        this.dateTime = dateTime;
        short op = 9;
        super.setOpcode(op);
    }

    public String getContent() {
        return content;
    }

    public byte getNotType() {
        return notType;
    }

    public String getPostingUser() {
        return postingUser;
    }

    public String getDateTime() {
        return dateTime;
    }
}

