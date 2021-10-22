import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.beans.Statement;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class dataBase {
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

    public static String getCryptoHash(String input, String algorithm) {
        try {
            //MessageDigest classes Static getInstance method is called with MD5 hashing
            MessageDigest msgDigest = MessageDigest.getInstance(algorithm);

            //digest() method is called to calculate message digest of the input
            //digest() return array of byte.
            byte[] inputDigest = msgDigest.digest(input.getBytes());

            // Convert byte array into signum representation
            // BigInteger class is used, to convert the resultant byte array into its signum representation
            BigInteger inputDigestBigInt = new BigInteger(1, inputDigest);

            // Convert the input digest into hex value
            String hashtext = inputDigestBigInt.toString(16);

            //Add preceding 0's to pad the hashtext to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // Catch block to handle the scenarios when an unsupported message digest algorithm is provided.
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String SHA256(String input){
        return getCryptoHash(input, "SHA-256");
    }

    public static boolean creatTable(String table){
        try {
            Connection db = connect();
            PreparedStatement statement = db.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(user VARCHAR(20), password VARCHAR(256))" );
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addUser(String user, String password){
        Connection conn;
        String query1, query2;
        PreparedStatement stmt1, stmt2;
        try {
            conn = connect();
            query1 = "SELECT * FROM USERS WHERE USERS.user = ?";
            stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, user);
            if(stmt1.executeQuery().next())
            {
                return false;
            }

            query2 = "INSERT INTO USERS(user, password) VALUES (?, ?)";
            stmt2 = conn.prepareStatement(query2);
            stmt2.setString(1, user);
            stmt2.setString(2, password);
            stmt2.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean getUser(String user, String password){
        Connection conn;
        String query1;
        PreparedStatement stmt1;
        try {
            conn = connect();
            query1 = "SELECT user FROM USERS WHERE USERS.user = ? AND USERS.password = ?";
            stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, user);
            stmt1.setString(2, password);
            if(stmt1.executeQuery().next())
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");

        System.out.println(SHA256("helo"));

        if(addUser("svend", SHA256("hello"))){
            System.out.println("added user");
        }

        if(getUser("svend", SHA256("hello"))){
            System.out.println("got user");
        }
    }
}
