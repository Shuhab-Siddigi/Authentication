package dtu.printer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtu.common.IPrinter;
import dtu.crypto.SimulatedCrypto;
public class Printer extends UnicastRemoteObject implements IPrinter  {

    public Printer() throws RemoteException {
        super();
        
    }

    public String command(String command) throws RemoteException {
        System.out.println("Printer: Recived Command: "+command);
        command = SimulatedCrypto.DeKCG(command);
        String functions = SimulatedCrypto.getFunctions(command);
        
        String[] commands = command.split(",");
        if (!functions.contains(commands[1])) {
            return SimulatedCrypto.KCG("Clearence not valid");
        }else{
            System.out.println("Printer: Decrypted messages contains function");
        }
        switch(commands[1]){
            case "start":
                return SimulatedCrypto.KCG(start());
            case "stop":
                return SimulatedCrypto.KCG(stop());
            case "print":
                return SimulatedCrypto.KCG(print(commands[2], commands[3]));
            case "queue":
                return SimulatedCrypto.KCG(queue(commands[2]));
            case "topQueue":
                return SimulatedCrypto.KCG(topQueue(commands[2], commands[3]));
            case "restart":
                return SimulatedCrypto.KCG(restart());
            case "status":
                return SimulatedCrypto.KCG(status(commands[2]));
            case "readConfig":
                return SimulatedCrypto.KCG(readConfig(commands[2]));
            case "setConfig":
                return SimulatedCrypto.KCG(setConfig(commands[2], commands[3]));
            default:
                return SimulatedCrypto.KCG("Command does not exist!!!");
        }
    };

    // Implementation of the query interface
    public String print(String filename, String printer)  {
        String command = "Printer: Running Function print(String " + filename + ", String " + printer + ")";
        System.out.println(command);
        return command;
    }

    // lists the print queue on the user's display in lines of the form <job number>
    // <file name>
    public String queue(String printer)  {
        String command = "Printer: Running Function queue(" + printer + ")";
        System.out.println(command);
        return command;
    };

    // moves job to the top of the queue
    public String topQueue(String printer, String job)  {
        String command = "Printer: Running Function topQueue(" + printer + "," + job + ")";
        System.out.println(command);
        return command;
    };

    // starts the print server
    public String start()  {
        String command = "Printer: Running Function start()";
        System.out.println(command);
        return command;
    };

    // stops the print server
    public String stop()  {
        String command = "Printer: Running Function stop()";
        System.out.println(command);
        return command;
    };

    // stops the print server, clears the print queue and starts the print server
    // again
    public String restart()  {
        String command = "Printer: Running Function restart()";
        System.out.println(command);
        return command;
    };

    // prints status of printer on the user's display
    public String status(String printer)  {
        String command = "Printer: Running Function status(String " + printer + ")";
        System.out.println(command);
        return command;
    };

    // prints the value of the parameter on the user's display
    public String readConfig(String parameter)  {
        String command = "Printer: Running Function readConfig(String " + parameter + ")";
        System.out.println(command);
        return command;
    };

    // sets the parameter to value
    public String setConfig(String parameter, String value) {
        String command = "Printer: Running Function setConfig(" + value + "," + value + ")";
        System.out.println(command);
        return command;
    }

    public String reciveTicket(String ticket) throws RemoteException {
        System.out.println("Printer: Recieved Ticket: "+ticket);
        ticket = SimulatedCrypto.DecryptSharedKey(ticket);
        //System.out.println(ticket);
        return "{|ACK|}KCG";
    }

 

}
