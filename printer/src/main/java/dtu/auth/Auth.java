package dtu.auth;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Auth extends UnicastRemoteObject implements IAuth {

    private ArrayList<User> users = new ArrayList<>();

    public Auth() throws RemoteException {
        super();
        users.add(new User("admin", "admin"));
    }

    public boolean access(User user) {

        for (User u : users) {

            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                System.out.println("Got User: " + u.getUsername() + " " + "Got Password: " + u.getPassword());
                user.setGranted(true);
                return true;
            }

        }
        return false;
    }

    public String tgspath(User user) throws RemoteException {
        // @WIP (returns null)
        System.out.println(user.isGranted());
        if(user.isGranted()){
            return "/TGS";
        }else {
            
            return null;
        }
        
    }

}
