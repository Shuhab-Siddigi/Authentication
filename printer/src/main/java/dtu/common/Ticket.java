package dtu.common;

import java.io.Serializable;

public class Ticket implements Serializable {
    
    private String command, token, time;

    public Ticket (String command, String token,String time) {
        this.setCommand(command);
        this.setToken(token);
        this.setTime(time);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
