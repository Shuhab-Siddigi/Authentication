package dtu.auth;
import dtu.database.Database;

public class DatabaseGenerator {

  
  private static  Database db = new Database();
    public static void main(String args[]) {

        if(db.creatTable()){
            System.out.println("Created Database");
          };
          
          if( db.tryAdd("Alice","Alice","Admin") 
          && db.tryAdd("Bob","Bob","Janitor,ServiceTechnician")
          && db.tryAdd("Cecilia","Cecilia","PowerUser")
          && db.tryAdd("David","David","OrdinaryUser")
          && db.tryAdd("Erica","Erica","OrdinaryUser")
          && db.tryAdd("Fred","Fred","OrdinaryUser")
          && db.tryAdd("George","George","OrdinaryUser")  
          ){
            System.out.println("Clients Added");
          }
          if(db.AddRole("Admin", "print,queue,topQueue,start,stop,restart,status,readConfig,setConfig")
          && db.AddRole("Janitor", "start,stop,restart")
          && db.AddRole("ServiceTechnician", "status,readConfig,setConfig")
          && db.AddRole("PowerUser", "print,queue,topQueue,restart")
          && db.AddRole("OrdinaryUser", "print,queue")
          ){
            System.out.println("Roles added");
          }



    }


}
