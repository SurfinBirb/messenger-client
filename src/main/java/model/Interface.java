package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class Interface {


    /**
     * @return true если авторизация прошла успешно
     */
    public static boolean isLogged() {
        return logged.get();
    }

    /**
     * @return logged property
     */
    public static BooleanProperty loggedProperty() {
        return logged;
    }

    /**
     * @param logged true if logged
     */
    static void setLogged(boolean logged) {
        Interface.logged.set(logged);
    }

    /**
     * boolean property - is logged?
     */
    private static BooleanProperty logged = new SimpleBooleanProperty();

    /**
     * Список комнат
     */
    static LinkedList<Room> rooms = new LinkedList<>();

    /**
     *Мапа комнат
     */
    private static TreeMap<Long, Room> roomMap = Storage.getInstance().getRooms();

    /**
     * Мапа комнат, создаваемая конструктором, через нее отслеживаются изменения
     */
    private static TreeMap<Long, Room> oldMap = new TreeMap<>();

    /**
     * Идентификатор комнаты, в которой находится клиент сразу после авторизации
     */
    public static Long defaultRoomId;

    /**
     * @return TreeMap комнат
     */
    public static TreeMap<Long, Room> getRoomMap(){return Storage.getInstance().getRooms();}


    public static MapProperty<Long, ListProperty<Message>> roomObservableMap = new SimpleMapProperty<>(FXCollections.observableHashMap());


    /**
     * Послать сообщение всем в комнате
     * @param roomId  идентификатор комнаты
     * @param text  текст сообщения
     */
    public static void sendMessage(Long roomId, String text){
        Storage storage = Storage.getInstance();
        Message message = new Message(roomId, null, storage.getClientId(), text);
        Packet packet = new Packet("message",message,null, storage.getClientId(), null,null);
        String xmlPacket = new Packer().pack(packet);
        Sender.send(xmlPacket);
    }


    /**
     *
     * @param roomId идентификатор комнаты
     * @return true, если в в комнате есть новые сообщения
     */
    public static boolean haveIncomingMessagesForRoom(Long roomId){
        return !roomMap.get(roomId).equals(oldMap.get(roomId));
    }

    /**
     * Печатает новые сообщения из этой комнаты в консоль
     * @param roomId идентификатор комнаты
     */
    public static void printNewMessages(Long roomId){
        for (int index = oldMap.get(roomId).getMessages().size()+1; index <= roomMap.get(roomId).getMessages().size(); index++){
            System.out.println(roomMap.get(roomId).getMessages().get(index).text);
        }
    }

    /**
     * Обновляет обновляет список сообщений в комнате
     * @param roomId идентификатор комнаты
     */
    public static void refreshRoomMessageList(Long roomId){
        oldMap.get(roomId).setMessages(roomMap.get(roomId).getMessages());
    }

    /**
     *  Залогиниться (3 попытки, после сервер разорвет коннект)
     * @param login логин
     * @param password пароль
     */
    public static void sendAuthData(String login, String password){
        Authorization.getInstance().attempt(login,password);
        for(int i = 0; i <= 10; i++){
            try {
                Thread.sleep(250);
                if(Storage.getInstance().isDataLoaded()){
                    oldMap.putAll(Storage.getInstance().getRooms());
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tryToCreateNewRoom(String name){
        Storage storage = Storage.getInstance();
        List<Long> acesslist = new LinkedList<>();
        acesslist.add(storage.getClientId());
        Room room = new Room(
                storage.getClientId(),
                null,
                name,
                acesslist,
                null
                );
        Packet packet = new Packet(
                "room",
                null,
                room,
                storage.getClientId(),
                null,
                null
                );
        String xmlPacket = new Packer().pack(packet);
        Sender.send(xmlPacket);
    }

    /**
     * Запустить клиент
     */
    public static void turnOn(){
        try {
            Client.getInstance();
            //OutputRunnable outputRunnable = new OutputRunnable();
            //Thread outputThread = new Thread(outputRunnable);
            //outputThread.setDaemon(true);
            //outputThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
