package dtu.server;
// Step 3
// Use Interface and class
// Create rmiregistry
// Bind class interface obj to endpoint

import java.rmi.*;
import java.rmi.registry.*;

import dtu.auth.Auth;
import dtu.printer.Printer;
import dtu.tgs.TGS;

public class Server {


    public static void main(String args[]) {

        String connectionString = "rmi://localhost:1900"; 

        try {
            // Create authentication server 
            Auth auth = new Auth();
            TGS tgs = new TGS();
            Printer printer = new Printer();

            // rmiregistry within the server JVM with port number 1900
            LocateRegistry.createRegistry(1900);
            Naming.rebind(connectionString+"/Auth", auth);
            Naming.rebind(connectionString+"/TGS", tgs);
            Naming.rebind(connectionString+"/Database", auth);
            Naming.rebind(connectionString+"/Printer", printer);


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
