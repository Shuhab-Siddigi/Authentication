import java.rmi.*;
import java.rmi.registry.*;

public class Server {
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
            IHello hello = new Hello();

            // rmiregistry within the server JVM with
            // port number 1900
            LocateRegistry.createRegistry(1900);

            // Binds the remote object by the name
            // geeksforgeeks
            Naming.rebind(connectionString, hello);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
