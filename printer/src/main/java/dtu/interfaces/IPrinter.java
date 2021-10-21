package dtu.interfaces;
// Step 1 

// Creating a IHello interface

import java.rmi.*;

public interface IPrinter extends Remote {
    // Declaring the method prototype

    // prints file filename on the specified printer
    public String print(String filename, String printer) throws RemoteException;

    // lists the print queue on the user's display in lines of the form <job number>
    // <file name>
    public String queue(String printer) throws RemoteException;

    // moves job to the top of the queue
    public String topQueue(String printer, int job) throws RemoteException;

    // starts the print server
    public String start() throws RemoteException;

    // stops the print server
    public String stop() throws RemoteException;

    // stops the print server, clears the print queue and starts the print server again
    public String restart() throws RemoteException;

    // prints status of printer on the user's display
    public String status(String printer) throws RemoteException;

    // prints the value of the parameter on the user's display
    public String readConfig(String parameter) throws RemoteException;

    // sets the parameter to value
    public String setConfig(String parameter, String value) throws RemoteException;

    // login to be able to use printer
    public boolean login(String username, String password) throws RemoteException;

}
