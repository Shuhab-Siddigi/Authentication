package dtu.common;

import java.io.Serializable;

public class User implements Serializable {
    String username,password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return username;
    }

    public String getUsername() {
        return password;
    }


}
