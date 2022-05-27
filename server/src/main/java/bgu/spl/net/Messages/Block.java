package bgu.spl.net.Messages;

public class Block extends Message{
    private String userName;
    public Block(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
