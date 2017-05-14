package model;

/**
 * Created by SurfinBirb on 27.04.2017.
 */
public class OutputRunnable implements Runnable {
    private static volatile OutputRunnable instance;

    public static OutputRunnable getInstance() {
        OutputRunnable localInstance = instance;
        if (localInstance == null) {
            synchronized (Storage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OutputRunnable();
                }
            }
        }
        return localInstance;
    }

    public void run() {
        Storage storage = Storage.getInstance();

        try {
            while (true) {
                Packet packet = storage.getOutputQueue().poll();
                while (packet != null) {
                    Sender.send(new Packer().pack(packet));
                    packet = storage.getOutputQueue().poll();
                    }
                Thread.sleep(250);
                }
        } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


