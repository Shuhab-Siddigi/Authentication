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

    String message = "Hello Friend!";

    try {

      // lookup method to find reference of remote object
      IPrinter remoteEndpoint = (IPrinter) Naming.lookup(connectionString);
      
      System.out.println("Sendt message: " + message);
      // query message to remote end point
      String answer = remoteEndpoint.query(message);
      String answer2 = remoteEndpoint.print(message);

      System.out.println("Got answer: " + answer);
      System.out.println("Got answer2: " + answer2);

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
