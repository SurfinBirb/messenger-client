package model;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by SurfinBirb on 08.05.2017.
 */
public class Authorization {
    private static volatile Authorization instance;

    static Authorization getInstance() {
        Authorization localInstance = instance;
        if (localInstance == null) {
            synchronized (Authorization.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Authorization();
                }
            }
        }
        return localInstance;
    }

    void attempt(String login, String password){
        String hash = DigestUtils.md5Hex(password);
        AuthData authData = new AuthData(login,hash,null,false);
        try {
            Sender.send(new Packer().pack(new Packet("auth",null,null,null, null, authData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
