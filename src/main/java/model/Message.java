package model;

/**
 * Created by SurfinBirb on 21.04.2017.
 */
public class Message {
     Long roomid;
     Long timestamp;
     Long sender;
     String text;

    Message(long roomid, Long timestamp, long sender, String text) {
        this.roomid = roomid;
        this.timestamp = timestamp;
        this.sender = sender;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
