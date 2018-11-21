package com.example.think.eduhelper.Post.model.ItemView;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;
import java.util.UUID;

public class TitleParentAccepted implements Parent<TitleChildAccepted>{
    List<Object> childrenList;
    private UUID uuid;
    private String title;
    private String mainTopic;
    private String posterEmail;


    public String getPosterEmail() {
        return posterEmail;
    }

    public void setPosterEmail(String posterEmail) {
        this.posterEmail = posterEmail;
    }

    public TitleParentAccepted(String title, String mainTopic, String posterEmail){
        this.title = title;
        this.mainTopic = mainTopic;
        this.posterEmail = posterEmail;
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaintopic() {
        return mainTopic;
    }

    public void setMaintopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    @Override
    public List getChildList() {
        return childrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public void setChildrenList(List<Object> childrenList) {
        this.childrenList = childrenList;
    }
}
