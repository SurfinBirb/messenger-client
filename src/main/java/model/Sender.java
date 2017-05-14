package model;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by SurfinBirb on 27.04.2017.
 */
 class Sender {

    public Sender() {
    }

    private static DataOutputStream dataOutputStream = null;
    private static PrintWriter printWriter;
    private static OutputStream outputStream;

    /**
     * Рассылает пакет всем в комнате
     * @param xmlPacket - String made by Packer class
     */
     static void send(String xmlPacket) {
         try {

             /** Вариант 1: сокет закрывается, работа останавливается. При этом на сервер приходит пакет с двумя лишними
             символами в начале, которые нужно изгонять stringBuilder.deleteCharAt(0).deleteCharAt(0);*/

//             if (dataOutputStream == null) {
//                 dataOutputStream =  new DataOutputStream(Client.getInstance().getSocket().getOutputStream());
//             }
//             dataOutputStream.writeUTF(xmlPacket);
//             dataOutputStream.flush();
//             dataOutputStream.close();
//             System.out.println("\nPOST:\n" + xmlPacket + "\n");

                /** Вариант 2: сокет закрывается, работа останавливается. На сервер приходит непонятный (без махинаций
                 c StringBuiler он пуст) пакет, который магией превращается в нормальный. Магия - замена первого символа
                 stringBuilder.setCharAt(0, '<');  Причем иахинации обильно кидают исключения*/

//             if(printWriter == null){
//                 printWriter = new PrintWriter(Client.getInstance().getSocket().getOutputStream(), true);
//                }
//             printWriter.println(xmlPacket);
//             Client.getInstance().getSocket().getOutputStream().close();

             /**Вариант 3: не работает*/

//             if (outputStream == null) {
//                 outputStream =  Client.getInstance().getSocket().getOutputStream();
//             }
//             outputStream.write(xmlPacket.getBytes("UTF-8"));
//             outputStream.write("\r\n".getBytes());
//             outputStream.flush();
//             System.out.println("\nPOST:\n" + xmlPacket + "\n");

         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
