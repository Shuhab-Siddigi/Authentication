
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Printer extends Remote {

  public String print(String input) throws RemoteException;

}


