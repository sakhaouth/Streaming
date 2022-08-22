package com.example.cctvstreaming;

import java.io.Serializable;

public class Notification implements Serializable {
    private String description;
    private String senderName;
    private String subject;
    private String senderId;
    private String recId;
    private String reqTime;
    private String notificationId;
    public Notification(String description,String senderId,String senderName,String reqTime,String subject)
    {

        this.description = description;
        this.senderId = senderId;
        this.senderName = senderName;
        this.reqTime = reqTime;
        this.subject = subject;
    }
    public Notification(String description,String senderId,String senderName,String reqTime,String subject,String recId)
    {

        this.description = description;
        this.senderId = senderId;
        this.senderName = senderName;
        this.reqTime = reqTime;
        this.subject = subject;
        this.recId = recId;
    }
    public Notification(String description,String senderId,String senderName,String reqTime,String subject,String recId,String id)
    {

        this.description = description;
        this.senderId = senderId;
        this.senderName = senderName;
        this.reqTime = reqTime;
        this.subject = subject;
        this.recId = recId;
        this.notificationId = id;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getReqTime() {
        return reqTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSubject() {
        return subject;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
