package model;

import javax.net.ssl.SSLSocket;
import java.io.InputStream;

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
                    if (packet.getType().equals("Message")) {
                        storage.getRooms().get(packet.getRoom().getRoomId()).getMessages().add(packet.getMessage());
                    }
                }
                if (packet.getType().equals("room")){
                    storage.getRooms().put(packet.getRoom().getRoomId(), packet.getRoom());
                    Interface.rooms.add( packet.getRoom());
                }
                if (packet.getType().equals("auth")){
                    if (packet.getAuthData().isLogged()){
                        clientId = packet.getAuthData().getId();
                        storage.setClientId(clientId);
                        storage.setRooms(packet.getRoomMap());
                        Interface.rooms.addAll(packet.getRoomMap().values());
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
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while ( (c = socketInputStream.read()) != -1){
            stringBuilder.append((char) c);
        }
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(0);
        System.out.println("\nGET:\n" + stringBuilder.toString() + "\n");
        return stringBuilder.toString();
    }
}
