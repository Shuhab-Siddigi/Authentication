package dtu.common;

import java.io.Serializable;

public class User implements Serializable {
    String username,password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


}
