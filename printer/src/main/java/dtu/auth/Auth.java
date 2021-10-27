package dtu.auth;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dtu.common.IAuth;
import dtu.common.Ticket;
import dtu.common.User;

public class Auth extends UnicastRemoteObject implements IAuth {

  private ArrayList<User> users = new ArrayList<>();

  public Auth() throws RemoteException {
    super();
    users.add(new User("admin", "admin"));
  }


  public boolean login(User client) throws RemoteException {
    for (User user : users) {
      System.out.println(user.getUsername());
      if (client.getUsername().equals(client.getUsername()) &&
          client.getPassword().equals(client.getPassword())){
       return true;
      }
    }
    return false;
  }

  public String path() throws RemoteException {
    return "/Printer" ;
  }



  public Ticket requestTicket() throws RemoteException {

    return new Ticket("command", "token", "time");
  }



}
