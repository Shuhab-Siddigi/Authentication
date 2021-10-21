package dtu.interfaces;
// Step 1 
// Creating a IHello interface

import java.rmi.*;
public interface IPrinter extends Remote
{
    // Declaring the method prototype
    public String query(String greeting) throws RemoteException;

    public String print(String greeting) throws RemoteException;
}

