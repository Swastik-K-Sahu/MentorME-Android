package com.example.mentorme;

public class MsgModel {
    String senderUid;
    String receiverUid;
    String message;

    public MsgModel(String senderUid, String receiverUid, String message) {
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
    }

    public MsgModel() {
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
