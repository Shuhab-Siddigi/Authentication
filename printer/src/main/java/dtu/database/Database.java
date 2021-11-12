package dtu.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import dtu.crypto.Crypto;

public class Database {

  private Connection db = null;
  private String url = "jdbc:sqlite:sql.db";


  private String USERS = "CREATE TABLE IF NOT EXISTS Users(" +
      "User VARCHAR(20)," +
      "Password VARCHAR(256)," +
      "Roles VARCHAR(256)" +
      ");";

  private String RBAC = "CREATE TABLE IF NOT EXISTS RBAC (" +
      "Roles VARCHAR(256)," +
      "Functions VARCHAR(256)" +
      ");";
    private String ACL = "CREATE TABLE IF NOT EXISTS ACL (" +
        "User VARCHAR(256)," +
        "Password VARCHAR(256)," +
        "Functions VARCHAR(256)" +
    ");";
  public Database() {

    try {
      this.db = DriverManager.getConnection(url);
      System.out.println("Auth: Instantiated DB");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    createTable(USERS);
    System.out.println("DB: Read Users");
    createTable(RBAC);
    System.out.println("DB: Got RBAC Table");
    createTable(ACL);
    System.out.println("DB: Got ACL Table");
    instantiateDB();
  }

  public void createTable(String table) {
    try {
      PreparedStatement stmt = db.prepareStatement(table);
      stmt.executeUpdate();
    } catch (Exception e) {
      System.out.println("Could not create table");
    }
  }

  private void instantiateDB() {
    // Users for RBAC
    // User ,password ,Role(s)
    if (addUser("Alice", "Alice", "Admin")
        && addUser("Bob", "Bob", "Janitor,ServiceTechnician")
        && addUser("Cecilia", "Cecilia", "PowerUser")
        && addUser("David", "David", "OrdinaryUser")
        && addUser("Erica", "Erica", "OrdinaryUser")
        && addUser("Fred", "Fred", "OrdinaryUser")
        && addUser("George", "George", "OrdinaryUser")) {
      System.out.println("Clients Added");
    }
    // Roles for RBAC
    // Role ,Functions
    if (addRBAC("Admin", "print,queue,topQueue,start,stop,restart,status,readConfig,setConfig")
        && addRBAC("Janitor", "start,stop,restart")
        && addRBAC("ServiceTechnician", "status,readConfig,setConfig")
        && addRBAC("PowerUser", "print,queue,topQueue,restart")
        && addRBAC("OrdinaryUser", "print,queue")) {
      System.out.println("RBAC added");
    }

    // List for ACL
    //              User        , Password  , List of functions  
    if (addACL(     "Alice"     , "Alice"   ,"print,queue,topQueue,start,stop,restart,status,readConfig,setConfig")
        && addACL(  "Bob"       , "Bob"     ,"start,stop,restart,status,readConfig,setConfig")
        && addACL(  "Cecilia"   , "Cecilia" ,"print,queue,topQueue,restart")
        && addACL(  "David"     , "David"   ,"print,queue")
        && addACL(  "Erica"     , "Erica"   ,"print,queue")
        && addACL(  "Fred"      , "Fred"    ,"print,queue")
        && addACL(  "George"    , "George"  ,"print,queue")){
      System.out.println("ACL added");
    }
  }

  public boolean addUser(String user, String password, String permission) {
    String query1, query2;
    PreparedStatement stmt1, stmt2;
    try {

      query1 = "SELECT * FROM Users WHERE Users.User = ?";
      stmt1 = db.prepareStatement(query1);
      stmt1.setString(1, user);
      if (stmt1.executeQuery().next()) {
        return false;
      }

      query2 = "INSERT INTO Users(User, Password, Roles) VALUES (?, ?, ?)";
      stmt2 = db.prepareStatement(query2);
      stmt2.setString(1, user);
      stmt2.setString(2, Crypto.SHA256(password));
      stmt2.setString(3, permission);
      stmt2.executeUpdate();

      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public boolean getUser(String user, String password) {
    String query1;
    PreparedStatement stmt1;
    try {
      query1 = "SELECT user FROM Users WHERE Users.User = ? AND Users.Password = ?";
      stmt1 = db.prepareStatement(query1);
      stmt1.setString(1, user);
      stmt1.setString(2, Crypto.SHA256(password));
      if (stmt1.executeQuery().next()) {
        return true;
      }
      return false;
    } catch (SQLException e) {
      return false;
    }
  }

  public boolean addRBAC(String role, String functions) {
    String query1, query2;
    PreparedStatement stmt1, stmt2;
    try {
      query1 = "SELECT * FROM RBAC WHERE RBAC.Roles = ?";
      stmt1 = db.prepareStatement(query1);
      stmt1.setString(1, role);
      if (stmt1.executeQuery().next()) {
        return false;
      }
      query2 = "INSERT INTO RBAC(Roles, Functions) VALUES (?, ?)";
      stmt2 = db.prepareStatement(query2);
      stmt2.setString(1, role);
      stmt2.setString(2, functions);
      stmt2.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;

    }
  }

  public boolean getRBACRoleFunctions(String user, String password) {
    String query1;
    PreparedStatement stmt1;
    try {
      query1 = "SELECT User FROM Users WHERE Users.User = ? AND Users.Password = ?";
      stmt1 = db.prepareStatement(query1);
      stmt1.setString(1, user);
      stmt1.setString(2, Crypto.SHA256(password));
      if (stmt1.executeQuery().next()) {
        return true;
      }
      return false;
    } catch (SQLException e) {
      return false;
    }
  }

  public boolean addACL(String user, String password, String list) {
    String query1, query2;
    PreparedStatement stmt1, stmt2;
    try {
      query1 = "SELECT * FROM ACL WHERE ACL.User = ?";
      stmt1 = db.prepareStatement(query1);
      stmt1.setString(1, user);
      if (stmt1.executeQuery().next()) {
        return false;
      }
      query2 = "INSERT INTO ACL(User, Password, Functions) VALUES (?, ?, ?)";
      stmt2 = db.prepareStatement(query2);
      stmt2.setString(1, user);
      stmt2.setString(2, Crypto.SHA256(password));
      stmt2.setString(3, list);
      stmt2.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
  public String getACLList(String user, String password) {

    String query = "SELECT Functions FROM ACL WHERE User = ? AND Password = ?";
    String result = new String();
    try {
    
      PreparedStatement stmt = db.prepareStatement(query);
      stmt.setString(1, user);
      stmt.setString(2, Crypto.SHA256(password));
      ResultSet rs = stmt.executeQuery();
      result = rs.getString("Functions");
    } catch (SQLException e) {
      System.out.println(e);
    }

    return result;
    
  }


}
