import java.rmi.*;

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
      System.out.println("Sendt message: " + message);
      // lookup method to find reference of remote object
      IHello access = (IHello) Naming.lookup(connectionString);

      String answer = access.query(message);

      System.out.println("Got answer: " + answer);

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
