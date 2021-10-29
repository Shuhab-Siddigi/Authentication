package dtu.server;
// Step 3

// Use Interface and class
// Create rmiregistry
// Bind class interface obj to endpoint
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import dtu.auth.Auth;
import dtu.printer.Printer;

public class Server {

  public static void main(String args[]) {

    String connectionString = "rmi://localhost:1900";

    try {
      // Create authentication server
      Auth auth = new Auth();
      Printer printer = new Printer();
      // rmiregistry within the server JVM with port number 1900
      LocateRegistry.createRegistry(1900);

      Naming.rebind(connectionString + "/Auth", auth);
      Naming.rebind(connectionString + "/Printer", printer);

    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
