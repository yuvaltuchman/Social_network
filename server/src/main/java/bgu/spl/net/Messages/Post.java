package bgu.spl.net.Messages;

import bgu.spl.net.api.User;

import java.util.LinkedList;

public class Post extends Message{
    private String content;
    public LinkedList<String> tags;
    public Post(String content){
        this.content = content;
        this.tags = new LinkedList<String>();
    }

    public String getContent() {
        return content;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public LinkedList<String> getTags() {
        return tags;
    }
}
