package dtu.printer;
// Step 3
// Use Interface and class
// Create rmiregistry
// Bind class interface obj to endpoint

import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;

public class Server {

    
    ArrayList<User> users = new ArrayList<>();

    public static void main(String args[]) {

        String IP = "localhost:";
        String port = "1900";
        String endpoint = "/greeting";
        String connectionString = "rmi://" + IP + port + endpoint;

        try {
            // Create an object of the interface
            // implementation class
            // OPS
            // (Iterface = new Class )
            Printer printer = new Printer();
            
            // rmiregistry within the server JVM with
            // port number 1900
            LocateRegistry.createRegistry(1900);

            // Binds the remote object by the name
            // geeksforgeeks
            Naming.rebind(connectionString, printer);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
