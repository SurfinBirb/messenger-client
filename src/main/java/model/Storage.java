package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class Storage {
    private static volatile Storage instance;
    private volatile BlockingDeque<Packet> outputQueue;
    private volatile TreeMap<Long,Room> rooms;
    private volatile boolean dataLoaded;

    public Long getClientId() {
        return clientId;
    }

    private volatile Long clientId;

     BlockingDeque<ServiceMessage> getServiceMessages() {
        return serviceMessages;
    }

    private volatile BlockingDeque<ServiceMessage>  serviceMessages;

    private Storage() {
        outputQueue = new LinkedBlockingDeque<>();
        rooms = new TreeMap<>();
    }

     static Storage getInstance(){
        Storage localInstance = instance;
        if (localInstance == null) {
            synchronized (Storage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Storage();
                }
            }
        }
        return localInstance;
    }

     TreeMap<Long,Room> getRooms() {
        return rooms;
    }

     Queue<Packet> getOutputQueue() {
        return outputQueue;
    }

     void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setRooms(TreeMap<Long,Room> roomTreeMap){
         rooms.putAll(roomTreeMap);
         setDataLoaded(true);
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }
}

