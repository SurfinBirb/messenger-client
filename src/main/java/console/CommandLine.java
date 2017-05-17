package console;

import model.Interface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by SurfinBirb on 08.05.2017.
 */
public class CommandLine {

    public void launch() throws Exception{
        BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
        String line;
        String[] arguments;
        boolean isLive = false;
        Thread currentRoomMessageThread = null;
        Map<Long,Thread> threadMap = new TreeMap<>();
        while (true){
            if ( (line = in.readLine()) != null) {
                arguments = line.split(" ");

                if (arguments[0].equals("on") && !isLive){
                    Interface.turnOn();
                    isLive = true;
                    System.out.println("Client turned on");
                }

                if (arguments[0].equals("exit")){
                    System.exit(0);
                }

                if(arguments[0].equals("listen") && arguments[1].matches("[0-9][0-9]*") && isLive){
                    if (currentRoomMessageThread != null) {
                        currentRoomMessageThread.interrupt();
                    }
                    Long roomId = Long.parseLong(arguments[1]);
                    if (!threadMap.containsKey(roomId)) {
                       MessageRunnable messageRunnable = new MessageRunnable(roomId);
                       Thread messageThread = new Thread(messageRunnable);
                       messageThread.setDaemon(true);
                       messageThread.start();
                       threadMap.put(roomId,messageThread);
                       currentRoomMessageThread = messageThread;
                    }else {
                       currentRoomMessageThread = threadMap.get(roomId);
                       currentRoomMessageThread.start();
                   }
                }

                if (arguments[0].equals("send") && arguments[1].matches("[0-9][0-9]*") && isLive){
                    StringBuilder sb = new StringBuilder();
                    for (int index = 2; index < arguments.length; index++) sb.append(arguments[index]).append(" ");
                    System.out.println(sb.toString());
                    Interface.sendMessage(Long.parseLong(arguments[1]), sb.toString());
                }

                if (arguments[0].equals("auth") && (arguments.length == 3) && isLive){
                    Interface.sendAuthData(arguments[1], arguments[2]);
                }

                Thread.sleep(250);

            }
        }
    }
}
