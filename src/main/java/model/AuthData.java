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

     Long getId() {
        return id;
    }

    private Long id;

    boolean isLogged() {
        return logged;
    }

    private boolean logged = false;

     AuthData(String login, String hash, Long id, boolean logged) {
        this.login = login;
        this.hash = hash;
        this.id = id;
        this.logged = logged;
    }

}
