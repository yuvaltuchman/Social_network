package bgu.spl.net.srv;

import bgu.spl.net.Messages.Message;
import bgu.spl.net.api.User;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class BguConnections<T> implements Connections<T>{
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> C_Hlist;
    private ConcurrentHashMap<User , Integer> connecteduserMap;
    private ConcurrentHashMap<User , ConcurrentLinkedQueue<T>> usermessageMap;
    private ConcurrentHashMap<String, User> usernameMap;
    private ConcurrentLinkedQueue<T> Posts;
    private ConcurrentHashMap<User, ConcurrentLinkedQueue<User>> blockingMap;
    private String[] filters;

    private static class singeltonHolder{
        private static BguConnections instance = new BguConnections();
    }
    private BguConnections(){
        connecteduserMap = new ConcurrentHashMap<User , Integer>();
        C_Hlist = new ConcurrentHashMap<Integer, ConnectionHandler<T>>();
        usernameMap =  new ConcurrentHashMap<String, User>();
        usermessageMap = new  ConcurrentHashMap<User , ConcurrentLinkedQueue<T>>();
        Posts = new ConcurrentLinkedQueue<T>();
        blockingMap = new ConcurrentHashMap<User, ConcurrentLinkedQueue<User>>();
        filters = new String[]{"fuck", "manyak", "SPL", "bibi"};
    }
    public static BguConnections getInstance(){
        return singeltonHolder.instance;
    }
    public boolean send(int connectionId, T msg) {
        if (C_Hlist.keySet().contains(connectionId)) {
            C_Hlist.get(connectionId).send(msg);
            return true;
        }
        return false;
    }
    @Override
    public void broadcast(T msg) {
        for (Integer conId : C_Hlist.keySet()) {
            C_Hlist.get(conId).send(msg);
        }
    }

    @Override
    public void disconnect(int connectionId) {
        C_Hlist.remove(connectionId);
    }

    public void addConnection(Integer Id, ConnectionHandler<T> handler){
        C_Hlist.put(Id,handler);
    }
    public void connectUser(User user, int connId){
        connecteduserMap.put(user,connId);
    }

    public ConcurrentHashMap<User, Integer> getConnecteduserMap() {
        return connecteduserMap;
    }

    public ConcurrentHashMap<User, ConcurrentLinkedQueue<T>> getUsermessageMap() {
        return usermessageMap;
    }

    public void registerUser(User user){
        usermessageMap.put(user, new ConcurrentLinkedQueue<T>());
        usernameMap.put(user.getUsername(), user);
        blockingMap.put(user, new ConcurrentLinkedQueue<User>());
    }
    public void addMessage(T msg, User user){
        usermessageMap.get(user).add(msg);

    }
    public void savePost(T msg){
        Posts.add(msg);
    }

    public ConcurrentHashMap<String, User> getUsernameMap() {
        return usernameMap;
    }
    public void addBlock(User me, User block){
        blockingMap.get(me).add(block);
    }

    public String[] getFilters() {
        return filters;
    }
    public void ClearUserMessages(User u){
        usermessageMap.get(u).clear();
    }

    public ConcurrentHashMap<User, ConcurrentLinkedQueue<User>> getBlockingMap() {
        return blockingMap;
    }
}
