package hndit.atigampaha.messageapp;

public class Messages {

    String message;
    String senderId;
    long timestamp;
    String currentTime;


    public Messages() {
    }


    public Messages(String message, String senderId, long timestamp, String currenttime) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.currentTime = currenttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrenttime() {
        return currentTime;
    }

    public void setCurrenttime(String currenttime) {
        this.currentTime = currenttime;
    }
}
