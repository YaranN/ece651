package com.example.think.eduhelper.Post.model;

import com.example.think.eduhelper.Chat.models.User;

public class Post {
    private String course;

    public User getUser() {
        return user;
    }

    private User user;

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

    private String title;
    private String content;
    private String uid;

    public String getTopic() {
        return topic;
    }

    private String topic;

    public long getTimestamp() {
        return timestamp;
    }

    public long timestamp;

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status = false;

    public Post(){}

    public Post(String course, String title, String content, String uid, long timestamp, String topic, User user) {
        this.course = course;
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.timestamp = timestamp;
        this.topic = topic;
        this.user = user;
    }


}
