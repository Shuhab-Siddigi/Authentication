package dtu.client;
// Step 4
// Lookup specific endpoint from server
// Lhen recive object 
import java.rmi.*;

import dtu.interfaces.IPrinter;

public class Client {
  public static void main(String args[]) {
    // Create a connection string equal to
    // the one in the server class
    String IP = "localhost:";
    String port = "1900";
    String endpoint = "/greeting";
    String connectionString = "rmi://" + IP + port + endpoint;

    String filename = "myfile.txt";
    String printer = "XEROX X23 OVERPOWER!!";
    try {

      // lookup method to find reference of remote object
      IPrinter remoteEndpoint = (IPrinter) Naming.lookup(connectionString);
      
      System.out.println("Sendt file: " + filename + " To: "+printer);
      
      // query message to remote end point
      String respone = remoteEndpoint.print(filename,printer);

      System.out.println("Got respone: " + respone);

      // query message to remote end point
      respone = remoteEndpoint.topQueue(filename,4);

      System.out.println("Got respone: " + respone);

      // query message to remote end point
      boolean login = remoteEndpoint.login("admin1","admin1");

      System.out.println("Logged in: " + login);

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
