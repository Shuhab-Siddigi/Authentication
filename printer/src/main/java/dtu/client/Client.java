package dtu.client;
// Step 4
// Lookup specific endpoint from server
// Lhen recive object 
import java.rmi.*;

import dtu.printer.IHello;

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
      IHello remoteEndpoint = (IHello) Naming.lookup(connectionString);
      
      System.out.println("Sendt message: " + message);
      // query message to remote end point
      String answer = remoteEndpoint.query(message);

      System.out.println("Got answer: " + answer);

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
