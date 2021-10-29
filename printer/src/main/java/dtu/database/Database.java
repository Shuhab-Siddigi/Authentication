package dtu.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

import dtu.crypto.Crypto;


public class Database {


    private static Connection connect() {
        String url = "jdbc:sqlite:sql.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean creatTable(String table){
        try {
            Connection db = connect();
            PreparedStatement statement = db.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(user VARCHAR(20), password VARCHAR(256))" );
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

 
    public boolean tryAdd(String user, String password){
        Connection conn;
        String query1, query2;
        PreparedStatement stmt1, stmt2;
        try {
            conn = connect();
            query1 = "SELECT * FROM Users WHERE Users.user = ?";
            stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, user);
            if(stmt1.executeQuery().next())
            {
                return false;
            }

            query2 = "INSERT INTO Users(user, password) VALUES (?, ?)";
            stmt2 = conn.prepareStatement(query2);
            stmt2.setString(1, user);
            stmt2.setString(2, Crypto.SHA256(password));
            stmt2.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

  

    public boolean getUser(String user, String password){
        Connection conn;
        String query1;
        PreparedStatement stmt1;
        try {
            conn = connect();
            query1 = "SELECT user FROM Users WHERE Users.user = ? AND Users.password = ?";
            stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, user);
            stmt1.setString(2, Crypto.SHA256(password));
            if(stmt1.executeQuery().next())
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    
    // public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

    //     Crypto.SaveKeyPair("Keys", Crypto.RSAKeyGenerator());
    //     KeyPair keyPair = Crypto.LoadKeyPair("Keys", "RSA");
        
    //     //String k = keyPair.getPublic().toString();
    //     byte[] enc = Crypto.encrypt("hello", keyPair.getPublic());
    //     String dec = Crypto.decrypt(enc, keyPair.getPrivate());

    //     //getPublicKey(readFromFile("KEY/private.key"));
    //     //RSAKeyGenerator("KEY");
    //     System.out.println("hello");

    //     System.out.println(Crypto.SHA256("helo"));

    //     if(addUser("svend", "hello")){
    //         System.out.println("added user");
    //     }

    //     if(getUser("svend", "hello")){
    //         System.out.println("got user");
    //     }
    // }
}
