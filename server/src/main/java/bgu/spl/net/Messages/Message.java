package bgu.spl.net.Messages;

public class Message {
    protected short opcode;
    public Message(){}

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public short getOpcode() {
        return opcode;
    }


}
