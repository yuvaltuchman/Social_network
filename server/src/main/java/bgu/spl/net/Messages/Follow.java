package bgu.spl.net.Messages;

public class Follow extends Message{
    private String username;
    byte followflag;
    public Follow(byte followflag, String username){
        this.followflag = followflag;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public byte getFollowflag() {
        return followflag;
    }
}
