package dtu.tgs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtu.auth.User;

public class TGS extends UnicastRemoteObject implements ITGS{

    public TGS() throws RemoteException  {
        super();
    }

    public String token(User user) throws RemoteException {
        return "Token";
    }

}
