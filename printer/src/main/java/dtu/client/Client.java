package dtu.client;

// Step 4
// Lookup specific endpoint from server
// Lhen recive object 
import java.rmi.Naming;
import java.rmi.RemoteException;

import dtu.common.IAuth;
import dtu.common.IPrinter;
import dtu.common.User;
import dtu.crypto.SimulatedCrypto;

public class Client {

  public static void main(String args[]) throws RemoteException {

    String connectionString = "rmi://localhost:1900";

    String defikey = "X";
    User credentials = new User("admin", "admin");
    String identity = "Client";
    String reciver = "Auth";
    String service = "Printer";
    IPrinter printer;

    

    try { // Try to get authentication acces

      IAuth auth = (IAuth) Naming.lookup(connectionString + "/Auth");
      printer = (IPrinter) Naming.lookup(connectionString + "/Printer");
      
      System.out.println("Connected to Auth");

      // C->ath: {C,ath, exp(ge,X)}inv(pk(C))
      System.out.println("Send first half");
      String message = SimulatedCrypto.Sign(credentials.getUsername()+","+reciver+",exp(ge,"+defikey+")",identity);
      System.out.println("Sendt Message:"+message);

      String response = auth.requestKey(message);
      System.out.println("Recieved: "+response);
      
      // C->ath: {|{C,ath,p}inv(pk(C))|}exp(exp(ge,X),Y)
      System.out.println("Login attempt: ");
      message = SimulatedCrypto.Sign(credentials.getUsername()+","+credentials.getPassword()+","+reciver+","+service, identity);
      message = SimulatedCrypto.expEncrypt(message, "X", "Y");
      String ticket = auth.login(message);
      System.out.println("Recieved: "+ticket);

      ticket = SimulatedCrypto.expDencrypt(ticket);
      ticket = ticket.substring(ticket.lastIndexOf("{"),ticket.length());
      
      //{|ath,C,p,KCG,T1|}skag)
      System.out.println("Sendt Message: "+ticket);      
      response = printer.reciveTicket(ticket);
      System.out.println(response);

      String command = "Start";
      command = SimulatedCrypto.KCG(command);
      System.out.println("Sending Command: "+command);
      String result = printer.command(command);
      result = SimulatedCrypto.DeKCG(result);
      System.out.println("Recieved: "+result);


      command = "Print,somefile,Xerox";
      command = SimulatedCrypto.KCG(command);
      System.out.println("Sending Command: "+command);
      result = printer.command(command);
      result = SimulatedCrypto.DeKCG(result);
      System.out.println("Recieved: "+result);
      //C -> p: {|Print_command|}KCG


      //{|Response_command|}KCG

    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
