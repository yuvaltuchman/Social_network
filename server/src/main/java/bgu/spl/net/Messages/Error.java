package bgu.spl.net.Messages;

public class Error extends Message{
    private short sentop;
    public Error(short sentop){
        this.sentop = sentop;
        short op = 11;
        super.setOpcode(op);
    }

    public short getSentop() {
        return sentop;
    }
}
