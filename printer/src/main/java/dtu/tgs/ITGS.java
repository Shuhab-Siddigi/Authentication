package dtu.tgs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtu.auth.User;

public interface ITGS extends Remote {
    public String token(User user) throws RemoteException;
}
