package model;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class Client {
    private static volatile Client instance;

    public SSLSocket getSocket() {
        return socket;
    }

    private volatile SSLSocket socket;

    public static Client getInstance() throws Exception{
        Client localInstance = instance;
        if (localInstance == null) {
            synchronized (Client.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Client();
                }
            }
        }
        return localInstance;
    }

    private Client() throws Exception {

        SSLSocketFactory sslsocketfactory = createSSLContext().getSocketFactory();
        try {
            final Config config = ConfigReader.getInstance().getConfig();
            SSLSocket currentSocket = (SSLSocket) sslsocketfactory.createSocket(config.clientConfiguration.serverAddress, config.clientConfiguration.serverPort);
            currentSocket.setEnabledCipherSuites(currentSocket.getSupportedCipherSuites());
            currentSocket.startHandshake();
            System.out.println(
                            "\nConnection established:\n    address = " + currentSocket.getInetAddress().toString() +
                            "\n    localPort = " + currentSocket.getLocalPort() +
                            "\n    port = " + currentSocket.getPort()
            );
            this.socket = currentSocket;
            Thread connectionThread = new Thread(ConnectionRunnable.getInstance(currentSocket));
            connectionThread.setDaemon(true);
            connectionThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static SSLContext createSSLContext() throws Exception{

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/main/java/key/keystore.jks"),"PraiseKekMahBoi".toCharArray());

        // Create key manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "PraiseKekMahBoi".toCharArray());
        KeyManager[] km = keyManagerFactory.getKeyManagers();

        // Create trust manager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);
        TrustManager[] tm = trustManagerFactory.getTrustManagers();

        // Initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(km,  tm, null);

        return sslContext;
    }
}
