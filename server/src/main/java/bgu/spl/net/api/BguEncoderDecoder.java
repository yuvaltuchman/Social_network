package bgu.spl.net.api;

import bgu.spl.net.Messages.*;
import bgu.spl.net.Messages.Error;
import bgu.spl.net.api.MessageEncoderDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class BguEncoderDecoder implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private byte[] opCode = new byte[1 << 1];
    private short op;
    private int opcodelen = 0;
    private Message msg;

    @Override
    public Message decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == 59) {
            if(op == 1) {
                int startIn = 0, z = 0;
                String u = "";
                String p = "";
                String bd = "";
                for (int j = 0; j < len; j++) {
                    if (bytes[j] == 0) {
                        if (z == 0) {
                            u = new String(bytes, startIn, j -startIn);
                            startIn = j + 1;
                            z++;
                        } else if (z==1) {
                            p = new String(bytes, startIn, j - startIn);
                            startIn = j + 1;
                            z++;
                        }
                        else if(z==2) {
                            bd = new String(bytes, startIn, j-startIn);
                        }
                    }

                }
                msg = new Register(u,p,bd);
                msg.setOpcode(op);
            }
            else if(op == 2){
                int startIn = 0, z = 0;
                String u = "";
                String p = "";
                byte captcha = 0;
                for (int j = 0; j < len; j++) {
                    if (j == len - 1) {
                        captcha = bytes[j];
                    }
                    else if (bytes[j] == 0) {
                            if (z == 0) {
                                u = new String(bytes, startIn, j-startIn);
                                startIn = j + 1;
                                z++;
                            } else if (z == 1) {
                                p = new String(bytes, startIn, j -startIn);
                                startIn = j + 1;
                                z++;
                            }
                        }
                    }
                msg = new Login(u, p, captcha);
                msg.setOpcode(op);
            }
            else if(op == 3){
                msg = new Logout();
                msg.setOpcode(op);
            }
            else if(op == 4){
                byte followflag = bytes[0];
                String username = new String(bytes, 1, len-1);
                msg = new Follow(followflag,username);
                msg.setOpcode(op);
            }
            else if(op == 5){
                String content = new String(bytes, 0, len-1);
                msg = new Post(content);
                msg.setOpcode(op);
                while (content.contains("@")){
                        content = content.substring(content.indexOf('@') + 1);
                        ((Post)msg).addTag(content.substring(0,content.indexOf(' ')));
                        content = content.substring(content.indexOf(' ') + 1);
                }
            }
            else if(op == 6){

                int startIn = 0, z = 0;
                String u = "";
                String c = "";
                String dt = "";
                for (int j = 0; j < len; j++) {
                    if (bytes[j] == 0) {
                        if (z == 0) {
                            u = new String(bytes, startIn, j -startIn);
                            startIn = j + 1;
                            z++;
                        } else if (z==1) {
                            c = new String(bytes, startIn, j - startIn);
                            startIn = j + 1;
                            z++;
                        }
                        else if(z==2) {
                            dt = new String(bytes, startIn, j-startIn);
                        }
                    }

                }
                msg = new PM(u,c,dt);
                msg.setOpcode(op);
            }
            else if(op == 7){
                msg = new LogStat();
                msg.setOpcode(op);
            }
            else if(op == 8){
                String content = new String(bytes, 0, len-1);
                msg = new Stat();
                msg.setOpcode(op);
                while (content.contains("|")){
                    ((Stat)msg).addUser(content.substring(0,content.indexOf('|')));
                    content = content.substring(content.indexOf('|') + 1);
                }
                ((Stat)msg).addUser(content);
            }
            else if(op == 12){
                String username = new String(bytes, 0, len-1);
                msg = new Block(username);
                msg.setOpcode(op);
            }
            Message currmsg = msg;
            msg = null;
            opcodelen = 0;
            op = 0;
            len = 0;
            return currmsg;
        }
        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Message message) {
        short op = message.getOpcode();
        LinkedList<Byte> enco = new LinkedList<Byte>();
        byte[] Code = shortToBytes(op);
        enco.add(Code[0]);
        enco.add(Code[1]);
        if(op == 9){
            Notification n = (Notification) message;
            enco.add(n.getNotType());
            for (int i = 0; i < n.getPostingUser().length(); i++) {
                enco.add((byte)(n.getPostingUser().charAt(i)));
            }
            enco.add(((byte)0));
            for (int i = 0; i < n.getContent().length(); i++) {
                enco.add((byte)(n.getContent().charAt(i)));
            }
            enco.add(((byte)0));
            for (int i = 0; i < n.getDateTime().length(); i++) {
                enco.add((byte)(n.getDateTime().charAt(i)));
            }
            enco.add(((byte)0));
            enco.add(((byte)';'));
        }
        else if(op == 10){
            ACK ack = (ACK) message;
            byte[] sentcode = shortToBytes(ack.getMessageop());
            enco.add(sentcode[0]);
            enco.add(sentcode[1]);
            for (int i = 0; i < ack.getOptional().length; i++) {
                enco.add(ack.getOptional()[i]);
            }
            enco.add(((byte)';'));
        }
        else if(op == 11) {
            Error e = (Error) message;

            byte[] sentcode = shortToBytes(e.getSentop());
            enco.add(sentcode[0]);
            enco.add(sentcode[1]);
            enco.add(((byte)';'));
        }
        byte[] ans = new byte[enco.size()];
        for (int i = 0; i < enco.size(); i++) {
            ans[i] = enco.get(i);
        }
        return ans; //uses utf8 by default
    }

    private void pushByte(byte nextByte) {//private
        if(opcodelen == 0){
            opCode[opcodelen++] = nextByte;
        }
        else if(opcodelen == 1){
            opCode[opcodelen++] = nextByte;
            op = bytesToShort(opCode);

        }
        else {
            if (len >= bytes.length) {
                bytes = Arrays.copyOf(bytes, len * 2);
            }
            bytes[len++] = nextByte;
        }

    }
    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

}

