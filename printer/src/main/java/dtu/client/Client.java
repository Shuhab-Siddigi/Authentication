package dtu.client;

// Step 4
// Lookup specific endpoint from server
// Lhen recive object 
import java.rmi.Naming;
import java.rmi.RemoteException;

import dtu.common.IAuth;
import dtu.common.IPrinter;
import dtu.common.Ticket;
import dtu.common.User;

public class Client {

  public static void main(String args[]) throws RemoteException {

    String connectionString = "rmi://localhost:1900";
    IPrinter printer;
    Ticket ticket;
    User client = new User("admin", "admin");

    try { // Try to get authentication acces

      IAuth auth = (IAuth) Naming.lookup(connectionString + "/Auth");

      boolean login = auth.login(client);

      System.out.println(login);
      if (login) {
        try {
          
          String path = auth.path();
          printer = (IPrinter) Naming.lookup(connectionString + path);
          ticket = auth.requestTicket();
          String res = printer.command(ticket);
          System.out.println(res);

        } catch (Exception e) {
          System.out.println(e);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
