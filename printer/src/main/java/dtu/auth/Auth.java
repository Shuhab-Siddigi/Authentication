package dtu.auth;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtu.common.IAuth;
import dtu.crypto.SimulatedCrypto;
import dtu.database.Database;

public class Auth extends UnicastRemoteObject implements IAuth {
  
  private  Database db = new Database();
  private String defikey = "Y";
  private String identity = "Auth";
  private String service = "Printer";


  public Auth() throws RemoteException {
    super();
    if(db.creatTable()){
      System.out.println("Created Database");
    };
    
    if( db.tryAdd("admin","admin","MASTER,SLAVE,JANITOR")){
      System.out.println("Client Added");
    }
  }

  public String requestKey(String message) throws RemoteException {
    System.out.println("Recived Message "+message);
    String msg = SimulatedCrypto.unSign(message);
    msg = msg.substring(0, msg.lastIndexOf(","));
    msg = SimulatedCrypto.Sign(msg+","+defikey+")", identity);
    System.out.println("Returning: "+msg);

    return msg;
  }
  
  public String login(String message) throws RemoteException {
    System.out.println("Recieved: "+message);
    message = SimulatedCrypto.expDencrypt(message);
    message = SimulatedCrypto.unSign(message);
    String[] credentials =  message.split(",");
    if(db.getUser(credentials[0], credentials[1])){
      System.out.println("User: "+credentials[0]+" succesfully logged in.");
       //{|ath,p,KCG,T1,C,{|ath,C,p,KCG,T1|}skag|}exp(exp(ge,X),Y)
      return SimulatedCrypto.EncryptSharedKey(identity+","+service+",KCG"+","+System.currentTimeMillis(), identity+","+credentials[0]+",KCG"+","+System.currentTimeMillis(), "X", "Y");
    }

    return null;
  }



}
