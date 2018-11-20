package com.example.think.eduhelper.Post.model;

import com.example.think.eduhelper.Chat.models.User;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String course;
    private User user;
    private String title;
    private String content;
    private String uid;
    public long timestamp;
    private String topic;
    private boolean status = false;
    private List<User> acceptors;

    public List<User> getAcceptors() {
        return acceptors;
    }

    public void addAcceptors(User user){
        acceptors.add(user);
    }

    public User getUser() {
        return user;
    }


    public String getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUid() {
        return uid;
    }


    public boolean isStatus() {
        return status;
    }


    public String getTopic() {
        return topic;
    }


    public long getTimestamp() {
        return timestamp;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public Post(){}

    public Post(String course, String title, String content, String uid, long timestamp, String topic, User user, List<User> acceptors) {
        this.course = course;
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.timestamp = timestamp;
        this.topic = topic;
        this.user = user;
        this.acceptors = acceptors;
        acceptors.add(user);
    }


}
