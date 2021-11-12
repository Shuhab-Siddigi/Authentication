package dtu.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuth extends Remote {

  // login 
  public String requestKey(String key) throws RemoteException;
  public String login(String message) throws RemoteException;

}
