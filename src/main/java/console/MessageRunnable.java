package console;

import model.Interface;

/**
 * Created by SurfinBirb on 08.05.2017.
 */
public class MessageRunnable implements Runnable {

    private Long currentRoomId;

    public MessageRunnable(Long roomId) {
        this.currentRoomId = roomId;
    }

    public void run() {
        while (true){
            if(Interface.haveIncomingMessagesForRoom(currentRoomId)) {
                Interface.printNewMessages(currentRoomId);
                Interface.refreshRoomMessageList(currentRoomId);
            }
            try {
                this.wait(500);
            } catch (Exception ignore) {}
        }
    }
}