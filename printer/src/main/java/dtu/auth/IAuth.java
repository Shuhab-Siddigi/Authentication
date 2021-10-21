package dtu.auth;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuth extends Remote {

   // login to be able to use printer
   public boolean access(User user) throws RemoteException;

   public String tgspath(User user) throws RemoteException;

}
