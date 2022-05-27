package bgu.spl.net.api;

import bgu.spl.net.Messages.*;
import bgu.spl.net.Messages.Error;
import bgu.spl.net.srv.BguConnections;
import bgu.spl.net.srv.Connections;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class BguMessageProtocol implements BidiMessagingProtocol<Message>{
    private  boolean should_terminate;
    private BguConnections<Message> connections;
    private int connectionId;
    private User currUser;
    private boolean errorAck = false;
    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connections = (BguConnections) connections;
        this.connectionId = connectionId;
    }

    @Override
    public void process(Message message) {
        errorAck = false;
        if(message.getOpcode() == 1){
            Register r = (Register) message;
            if(!connections.getUsernameMap().keySet().contains(r.getUserName())){
                User user = new User(r.getUserName(), r.getPassWord(), r.getBirthDay());
                connections.registerUser(user);
                errorAck = true;

            }
            if (errorAck){
                ACK a = new ACK(new byte[0], message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 2){
            Login l = (Login) message;
            if(currUser == null && l.getCaptcha() == 1) {
                if(connections.getUsernameMap().keySet().contains(l.getUsername())){
                    User u = connections.getUsernameMap().get(l.getUsername());
                    if (u.getUsername().equals(l.getUsername()) && u.getPassword().equals(l.getPassword()) && !u.isLoggedIn()) {
                        errorAck = true;
                        currUser = u;
                        u.setLoggedIn(true);
                        connections.connectUser(u, connectionId);
                        for (Message m:connections.getUsermessageMap().get(currUser)) {
                            if(m.getOpcode() == 5){
                                Notification n = new Notification(m.getOpcode(), currUser.getUsername(), ((Post)m).getContent(), "");
                                short s = 9;
                                n.setOpcode(s);
                                connections.send(connections.getConnecteduserMap().get(u), n);
                            }
                            else if(m.getOpcode() == 6){
                                Notification n = new Notification(m.getOpcode(), currUser.getUsername(), ((PM)m).getContent(), ((PM)m).getDateTime());
                                short s = 9;
                                n.setOpcode(s);
                                connections.send(connections.getConnecteduserMap().get(u), n);
                            }
                        }
                        connections.ClearUserMessages(u);
                    }
                }
            }
            if (errorAck){
                ACK a = new ACK(new byte[0], message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 3){
            if (currUser != null) {
                errorAck = true;
                if (errorAck){
                    ACK a = new ACK(new byte[0], message.getOpcode());
                    connections.send(connectionId, a);
                }
                connections.getConnecteduserMap().remove(currUser);
                currUser.setLoggedIn(false);
                connections.disconnect(connectionId);
                currUser = null;
                should_terminate = true;
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }

        }
        else if(message.getOpcode() == 4){
            Follow f = (Follow) message;
            User u = connections.getUsernameMap().get(f.getUsername());
            if (currUser != null && u != null) {
                if (f.getFollowflag() == 0) {
                    if (!u.getFollowers().contains(currUser) && !connections.getBlockingMap().get(u).contains(currUser)) {
                        u.getFollowers().add(currUser);
                        currUser.getFollowing().add(u);
                        errorAck = true;
                    }
                } else {
                    if (u.getFollowers().contains(currUser)) {
                        u.getFollowers().remove(currUser);
                        currUser.getFollowing().remove(u);
                        errorAck = true;
                    }
                }
            }
            if(errorAck){
                byte[] needZero = f.getUsername().getBytes(StandardCharsets.UTF_8);
                needZero = Arrays.copyOf( needZero, needZero.length+1);
                ACK a = new ACK(needZero, message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 5) {
            Post p = (Post) message;
            if (currUser != null) {
                connections.savePost(p);
                for (User u : currUser.getFollowers()) {
                    if (!p.getTags().contains(u.getUsername())) {
                        if (!u.isLoggedIn()) {
                            connections.addMessage(p, u);
                        } else {
                            Notification n = new Notification(p.getOpcode(), currUser.getUsername(), p.getContent(),"");
                            connections.send(connections.getConnecteduserMap().get(u), n);
                        }
                    }
                }
                for (String tag : p.getTags()) {
                    User u = connections.getUsernameMap().get(tag);
                    if (u != null && !u.isLoggedIn()) {
                        connections.addMessage(p, u);
                    }
                    else {
                        Notification n = new Notification(p.getOpcode(), currUser.getUsername(), p.getContent(), "");
                        connections.send(connections.getConnecteduserMap().get(u),n);
                    }
                }
                errorAck = true;
            }
            if (errorAck){
                currUser.setNumPosts();
                ACK a = new ACK(new byte[0], message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 6) {
            PM pm = (PM)message;
            if(currUser != null){
                User u = connections.getUsernameMap().get(pm.getUserName());
                if(u != null && currUser.getFollowing().contains(u)){
                    for (String s:connections.getFilters()) {
                        if(pm.getContent().contains(s)){
                            pm.filterWords(s);
                        }
                    }
                    connections.savePost(pm);
                    if (!u.isLoggedIn()) {
                        connections.addMessage(pm, u);
                    }
                    else {
                        Notification n = new Notification(pm.getOpcode(), currUser.getUsername(), pm.getContent(), pm.getDateTime());
                        connections.send(connections.getConnecteduserMap().get(u),n);
                    }
                    errorAck = true;
                }
            }
            if (errorAck){
                ACK a = new ACK(new byte[0], message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 7){
            if (currUser != null){
                for (User u:connections.getConnecteduserMap().keySet()) {
                    if(!connections.getBlockingMap().get(u).contains(currUser) && !connections.getBlockingMap().get(currUser).contains(u)) {
                        byte[] stat = new byte[8];
                        short age = (short) u.calculateAge();
                        byte[] tmp = shortToBytes(age);
                        stat[0] = tmp[0];
                        stat[1] = tmp[1];
                        short numPo = (short) u.getNumPosts();
                        tmp = shortToBytes(numPo);
                        stat[2] = tmp[0];
                        stat[3] = tmp[1];
                        short numFollr = (short) u.getFollowers().size();
                        tmp = shortToBytes(numFollr);
                        stat[4] = tmp[0];
                        stat[5] = tmp[1];
                        short numFollw = (short) u.getFollowing().size();
                        tmp = shortToBytes(numFollw);
                        stat[6] = tmp[0];
                        stat[7] = tmp[1];
                        ACK a = new ACK(stat, message.getOpcode());
                        connections.send(connectionId, a);
                    }
                    else {
                        Error er = new Error(message.getOpcode());
                        connections.send(connectionId, er);
                    }
                }
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 8){
            Stat s = (Stat) message;
            if (currUser != null){
                for (String name : s.getStatUsers()) {
                    User u = connections.getUsernameMap().get(name);
                    if(u != null && !connections.getBlockingMap().get(u).contains(currUser) && !connections.getBlockingMap().get(currUser).contains(u)) {
                            byte[] stat = new byte[8];
                            short age = (short) u.calculateAge();
                            byte[] tmp = shortToBytes(age);
                            stat[0] = tmp[0];
                            stat[1] = tmp[1];
                            short numPo = (short) u.getNumPosts();
                            tmp = shortToBytes(numPo);
                            stat[2] = tmp[0];
                            stat[3] = tmp[1];
                            short numFollr = (short) u.getFollowers().size();
                            tmp = shortToBytes(numFollr);
                            stat[4] = tmp[0];
                            stat[5] = tmp[1];
                            short numFollw = (short) u.getFollowing().size();
                            tmp = shortToBytes(numFollw);
                            stat[6] = tmp[0];
                            stat[7] = tmp[1];
                            ACK a = new ACK(stat, message.getOpcode());
                            connections.send(connectionId, a);
                    }
                    else {
                        Error er = new Error(message.getOpcode());
                        connections.send(connectionId, er);
                    }
                }
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
        else if(message.getOpcode() == 12){
            Block bl = (Block) message;
            User toBlock = connections.getUsernameMap().get(bl.getUserName());
            if(currUser != null && toBlock != null && !connections.getBlockingMap().get(currUser).contains(toBlock)) {
                connections.addBlock(currUser, toBlock);
                toBlock.getFollowing().remove(currUser);
                toBlock.getFollowers().remove(currUser);
                currUser.getFollowing().remove(toBlock);
                currUser.getFollowers().remove(toBlock);
                errorAck = true;
            }
            if (errorAck){
                ACK a = new ACK(new byte[0], message.getOpcode());
                connections.send(connectionId, a);
            }
            else {
                Error er = new Error(message.getOpcode());
                connections.send(connectionId, er);
            }
        }
    }

    @Override
    public boolean shouldTerminate() {
        return should_terminate;
    }
    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
}
