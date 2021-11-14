package dtu.auth;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtu.common.IAuth;
import dtu.crypto.SimulatedCrypto;
import dtu.database.Database;

public class Auth extends UnicastRemoteObject implements IAuth {

  private Database db = new Database();
  private String defikey = "Y";
  private String identity = "Auth";
  private String service = "Printer";

  public Auth() throws RemoteException {
    super();
  }

  public String requestKey(String message) throws RemoteException {
    System.out.println("Auth: Recived Message " + message);
    String msg = SimulatedCrypto.unSign(message);
    msg = msg.substring(0, msg.lastIndexOf(","));
    msg = SimulatedCrypto.Sign(msg + "," + defikey + ")", identity);
    System.out.println("Auth: Returning: " + msg);

    return msg;
  }

  public String loginACL(String message) throws RemoteException {
    String ACLList = new String();
    System.out.println("Auth: Recieved: " + message);
    message = SimulatedCrypto.expDencrypt(message);
    message = SimulatedCrypto.unSign(message);
    String[] credentials = message.split(",");
    if (db.getUser(credentials[0], credentials[1])) {
      System.out.println("Auth: user " + credentials[0] + " succesfully logged in.");
      ACLList = db.getACLList(credentials[0], credentials[1]);
      // {|ath,p,KCG,T1,C,{|ath,C,p,KCG,T1,ACLList|}skag|}exp(exp(ge,X),Y)
      String response = SimulatedCrypto.EncryptSharedKey(identity + "," + service + ",KCG" + "," + System.currentTimeMillis(),
          identity + "," + credentials[0] + ",KCG" + "," + System.currentTimeMillis() + ",[" + ACLList+"]", "X", "Y");
      System.out.println("Auth: Returning: " + response);
      return response;
    }

    return null;
  }
 
  public String loginRBAC(String message) throws RemoteException {
    String RBACFunctions = new String();
    System.out.println("Auth: Recieved: " + message);
    message = SimulatedCrypto.expDencrypt(message);
    message = SimulatedCrypto.unSign(message);
    String[] credentials = message.split(",");
    if (db.getUser(credentials[0], credentials[1])) {
      System.out.println("Auth: user " + credentials[0] + " succesfully logged in.");
      RBACFunctions = db.getRBACRoleFunctions(credentials[0], credentials[1]);
      // {|ath,p,KCG,T1,C,{|ath,C,p,KCG,T1,ACLList|}skag|}exp(exp(ge,X),Y)
      String response = SimulatedCrypto.EncryptSharedKey(identity + "," + service + ",KCG" + "," + System.currentTimeMillis(),
          identity + "," + credentials[0] + ",KCG" + "," + System.currentTimeMillis() + ",[" + RBACFunctions+"]", "X", "Y");
      System.out.println("Auth: Returning: " + response);
      return response;
    }

    return null;
  }

}
