package model;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class Interface {

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
