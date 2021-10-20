// Step 2 
// Implement the IHello interface in a Hello class

import java.rmi.*;
import java.rmi.server.*;

public class Hello extends UnicastRemoteObject implements IHello {

    // Default constructor to throw RemoteException
    // from its parent constructor
    Hello() throws RemoteException {
        super();
    }

    // Implementation of the query interface
    public String query(String greeting) throws RemoteException {

        String answer;

        System.out.println("Got Message: " + greeting);
        // OPS == does not work!
        if (greeting.equals("Hello Friend!")) {
            answer = "Good day to you sir!";
        } else {
            answer = "Whae?";
        }

        System.out.println("Returned answer: "+ answer);

        return answer;
    }
}
