package dtu.server;
// Step 3

// Use Interface and class
// Create rmiregistry
// Bind class interface obj to endpoint
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import dtu.auth.Auth;
import dtu.printer.Printer;
import dtu.crypto.SimulatedCrypto;

public class Server {

  public static String Sign(String message, String author) {


    return  "{" + message + "}" + "K_private_"+ author;
}

public static String unSign(String signedMessage) {

    String substring = signedMessage.substring(1,signedMessage.length());
    return signedMessage.substring(1,substring.lastIndexOf("}")+1);
}

public static String Encrypt(String message, String receiver) {


  return  "{" + message + "}" + "K_public_"+ receiver;
}

public static String Decrypt(String signedMessage) {

  String substring = signedMessage.substring(1,signedMessage.length());
  return signedMessage.substring(1,substring.lastIndexOf("}")+1);
}


  public static void main(String args[]) {

    String connectionString = "rmi://localhost:1900";

    String message = "message";
    String author = "Server";
    String receiver = "Client";
    
    System.out.println(message);
    String signed_message =  Sign( message, author); 
    String encrypted_message = Encrypt(signed_message, receiver);
    System.out.println(encrypted_message);
    String decrypted_message = Decrypt(encrypted_message);
    System.out.println(decrypted_message);
    String unsigned_message =  unSign(decrypted_message); 
    System.out.println(unsigned_message);

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
