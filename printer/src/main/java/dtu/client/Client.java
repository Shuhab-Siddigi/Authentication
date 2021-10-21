package dtu.client;

// Step 4
// Lookup specific endpoint from server
// Lhen recive object 
import java.rmi.*;

import dtu.auth.IAuth;
import dtu.auth.User;
import dtu.printer.IPrinter;
import dtu.tgs.ITGS;

public class Client {

  public static void main(String args[]) throws RemoteException {
    // Create a connection string equal to
    // the one in the server class
    String connectionString = "rmi://localhost:1900";

    // Clear txt message
    // 1 Message -> Kerberos
    User client = new User("admin", "admin");

    try { // Try to get authentication acces

      IAuth auth = (IAuth) Naming.lookup(connectionString + "/Auth");

      if (auth.access(client)) {
        System.out.println("Access Granted!");
        try { // Try to get ticket from ticket granting service
         // System.out.println("Got Path: "+auth.tgspath(client));
         // ITGS tgs = (ITGS) Naming.lookup(connectionString + auth.tgspath(client));
         // System.out.println(tgs.token(client));
        } catch (Exception e) {
          System.out.println(e);
        }
      }

      // try { // try to get result from printer

      //   IPrinter printer = (IPrinter) Naming.lookup(connectionString + "/Printer");
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));
      //   System.out.println(printer.print("filename", "printer"));

      // } catch (Exception printex) {
      //   System.out.println(printex);
      // }

    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
