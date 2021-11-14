package dtu.common;
// Step 1 

// Creating a IHello interface

import java.rmi.*;

public interface IPrinter extends Remote {
  // Declaring the method prototype
  public String command(String command) throws RemoteException;

  public String reciveTicket(String ticket) throws RemoteException;

}
