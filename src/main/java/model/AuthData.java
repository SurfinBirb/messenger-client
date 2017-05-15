package model;

/**
 * Created by SurfinBirb on 26.04.2017.
 */
public class AuthData {

    public String getLogin() {
        return login;
    }

    public String getHash() {
        return hash;
    }

    private String login;
    private String hash;

     Long getClientId() {
        return clientId;
    }

    private Long clientId;

    boolean isLogged() {
        return logged;
    }

    private boolean logged = false;

     AuthData(String login, String hash, Long clientId, boolean logged) {
        this.login = login;
        this.hash = hash;
        this.clientId = clientId;
        this.logged = logged;
    }

}
