// Step 1 
// Creating a IHello interface

import java.rmi.*;
public interface IHello extends Remote
{
    // Declaring the method prototype
    public String query(String greeting) throws RemoteException;
}
