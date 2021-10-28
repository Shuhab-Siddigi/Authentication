package dtu.printer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtu.common.IPrinter;
import dtu.common.Ticket;
public class Printer extends UnicastRemoteObject implements IPrinter  {
    public String PublicStringPrinter = "PUBKP";
    private String PrivateKeyPrinter = "PRIVKP";

    public Printer() throws RemoteException {
        super();
        
    }

    public String command(Ticket ticket) throws RemoteException {
        
        System.out.println(ticket.getCommand());
        return "Command executed";
    };

    // Implementation of the query interface
    public String print(String filename, String printer)  {
        String command = "String print(String " + filename + ", String " + printer + ")";
        System.out.println(command);
        return command;
    }

    // lists the print queue on the user's display in lines of the form <job number>
    // <file name>
    public String queue(String printer)  {
        String command = "String queue(" + printer + ")";
        System.err.println(command);
        return command;
    };

    // moves job to the top of the queue
    public String topQueue(String printer, int job)  {
        String command = "String topQueue(" + printer + "," + job + ")";
        System.err.println(command);
        return command;
    };

    // starts the print server
    public String start()  {
        String command = "String start()";
        System.err.println(command);
        return command;
    };

    // stops the print server
    public String stop()  {
        String command = "String stop()";
        System.err.println(command);
        return command;
    };

    // stops the print server, clears the print queue and starts the print server
    // again
    public String restart()  {
        String command = "String restart()";
        System.err.println(command);
        return command;
    };

    // prints status of printer on the user's display
    public String status(String printer)  {
        String command = "String status(String " + printer + ")";
        System.err.println(command);
        return command;
    };

    // prints the value of the parameter on the user's display
    public String readConfig(String parameter)  {
        String command = "String readConfig(String " + parameter + ")";
        System.err.println(command);
        return command;
    };

    // sets the parameter to value
    public String setConfig(String parameter, String value) {
        String command = "String setConfig(" + value + "," + value + ")";
        System.err.println(command);
        return command;
    }

 

}
