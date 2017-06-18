package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class ConnectionRunnable implements Runnable {

    private static volatile ConnectionRunnable instance;

     static ConnectionRunnable getInstance(SSLSocket socket) throws Exception {
        ConnectionRunnable localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionRunnable.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionRunnable(socket);
                }
            }
        }
        return localInstance;
    }

    SSLSocket getSocket() {
        return socket;
    }

    private static SSLSocket socket;
    private static Long clientId;

    ConnectionRunnable(SSLSocket currentSocket) {
        socket = currentSocket;
    }

    public void run() {
        Storage storage = Storage.getInstance();
        try {
            while (!socket.isClosed()){
                String xmlPacket = listen(socket.getInputStream());
                Packet packet = new Unpacker().unpack(xmlPacket);
                int attempts = 0;
                if (packet.getType().equals("message")){
                        storage.getRooms().get(packet.getMessage().roomid).getMessages().add(packet.getMessage());
                        Interface.roomObservableMap.get(packet.getMessage().roomid).get().add(packet.getMessage());
                }
                if (packet.getType().equals("room")){
                    storage.getRooms().put(packet.getRoom().getRoomId(), packet.getRoom());
                    Interface.rooms.add( packet.getRoom());
                    Interface.roomObservableMap.get().put(packet.getRoom().getRoomId(), new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>())));
                }

                if (packet.getType().equals("roomInvitation")){ // TODO: 19.06.2017   Повторяет код выше, допилить
                    storage.getRooms().put(packet.getRoom().getRoomId(), packet.getRoom());
                    Interface.rooms.add( packet.getRoom());
                    Interface.roomObservableMap.get().put(packet.getRoom().getRoomId(), new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>())));
                }

                if (packet.getType().equals("auth")){
                    if (packet.getAuthData().isLogged()){
                        clientId = packet.getAuthData().getClientId();
                        storage.setClientId(clientId);
                        System.out.println("Authorised as " + clientId);
                        for(Room room: packet.getRoomMap().values()) {
                            if (room != null){
                                room.setMessages(new LinkedList<>());
                                Interface.roomObservableMap.get().put(room.getRoomId(), new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>())));
                                if (Interface.defaultRoomId == null) Interface.defaultRoomId = room.getRoomId();
                            }
                        }
                        storage.setRooms(packet.getRoomMap());
                        Interface.setLogged(true);

                    }else {
                        attempts++;
                        if (attempts == 3){
                            socket.close();
                        }
                    }
                }
                if (packet.getType().equals("servicemessage")){
                    storage.getServiceMessages().add(packet.getServiceMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param socketInputStream - Поток вывода прослушиваемого сокета
     * @return <code>String</code> with XML structure
     * @throws Exception
     */
    private String listen(InputStream socketInputStream) throws Exception{

        DataInputStream dataInputStream = new DataInputStream(socketInputStream);
        String line = dataInputStream.readUTF();
        System.out.println("\nGET:\n" + line + "\n");
        return line;

    }
}
