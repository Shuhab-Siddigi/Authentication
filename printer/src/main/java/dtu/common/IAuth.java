package dtu.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuth extends Remote {

  // login 
  public String requestKey(String key) throws RemoteException;
  public String loginACL(String message) throws RemoteException;
  public String loginRBAC(String message) throws RemoteException;
  

}
