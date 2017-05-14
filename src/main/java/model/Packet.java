package model;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Created by SurfinBirb on 22.04.2017.
 */
public class Packet {
    private String type;
    private Message message;
    private Room room;
    private Long clientId;
    private ServiceMessage serviceMessage;
    private AuthData authData;
    private TreeMap<Long,Room> roomMap;

     Packet(String type, Message message, Room room, Long clientId, ServiceMessage serviceMessage, AuthData auth) {
        this.type = type;
        this.message = message;
        this.room = room;
        this.clientId = clientId;
        this.serviceMessage = serviceMessage;
        this.authData = auth;
    }

    AuthData getAuthData() {
        return authData;
    }

    String getType() {
        return type;
    }

    Message getMessage() {
        return message;
    }

    Room getRoom() {
        return room;
    }

    Long getClientId() {
        return clientId;
    }

    ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public TreeMap<Long, Room> getRoomMap() {
        return roomMap;
    }
}
