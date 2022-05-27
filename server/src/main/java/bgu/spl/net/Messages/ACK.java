package bgu.spl.net.Messages;

import java.util.LinkedList;

public class ACK extends Message{
    private byte[] optional;
    private short messageop;
    public ACK(byte[] optional, short messageop){
        this.messageop = messageop;
        this.optional = optional;
        short op = 10;
        super.setOpcode(op);
    }

    public short getMessageop() {
        return messageop;
    }

    public byte[] getOptional() {
        return optional;
    }
}
