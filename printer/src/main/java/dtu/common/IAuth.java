package dtu.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuth extends Remote {

  // login 
  public boolean login(User client) throws RemoteException;

  public String path() throws RemoteException;
  public Ticket requestTicket() throws RemoteException;

}
