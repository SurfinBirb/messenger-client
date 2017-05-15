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

             if (dataOutputStream == null) {
                 dataOutputStream =  new DataOutputStream(Client.getInstance().getSocket().getOutputStream());
             }
             dataOutputStream.writeUTF(xmlPacket);
             dataOutputStream.flush();
             System.out.println("\nPOST:\n" + xmlPacket + "\n");

         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
