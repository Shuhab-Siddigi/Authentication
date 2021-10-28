package dtu.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.Cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
            stmt2.setString(2, SHA256(password));
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
            stmt1.setString(2, SHA256(password));
            if(stmt1.executeQuery().next())
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void writeToFile(String path, byte[] key){
        try {
            File f = new File(path);
            f.getParentFile().mkdirs();
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(key);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readFromFile(String path){
        try {
            FileInputStream fin = new FileInputStream(path);
            byte[] input = fin.readAllBytes();
            fin.close();
            return input;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair RSAKeyGenerator(){
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            keygen.initialize(1024);
            return keygen.generateKeyPair();
    
            //writeToFile(path + "/private", keypair.getPrivate().getEncoded());
            //writeToFile(path + "/public", keypair.getPublic().getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey getPublicKey(String key){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKey(String key) {
        PrivateKey privateKey = null;
        try{
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveKeyPair(String path, KeyPair keyPair){
        try{
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            X509EncodedKeySpec keyPub;
            PKCS8EncodedKeySpec keyPri;
            FileOutputStream fout;

            keyPub = new X509EncodedKeySpec(publicKey.getEncoded());
            fout = new FileOutputStream(path + "/public.key");
            fout.write(keyPub.getEncoded());
            fout.close();

            keyPri = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fout = new FileOutputStream(path + "/private.key");
            fout.write(keyPri.getEncoded());
            fout.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
	}

    public static KeyPair LoadKeyPair(String path, String algorithm){
        try{
            FileInputStream fs;
            byte[] input;
            KeyFactory key;
            PublicKey pubKey;
            PrivateKey priKey;

            key = KeyFactory.getInstance(algorithm);

            fs = new FileInputStream(path + "/public.key");
            input = fs.readAllBytes();
            fs.close();

            pubKey = key.generatePublic(new X509EncodedKeySpec(input));

            fs = new FileInputStream(path + "/private.key");
            input = fs.readAllBytes();
            fs.close();

            priKey = key.generatePrivate(new PKCS8EncodedKeySpec(input));

            return new KeyPair(pubKey, priKey);

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(String data, PublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data.getBytes());
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(byte[] data, PrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data));
        } catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        SaveKeyPair("KEY", RSAKeyGenerator());
        KeyPair keyPair = LoadKeyPair("KEY", "RSA");
        
        //String k = keyPair.getPublic().toString();
        byte[] enc = encrypt("hello", keyPair.getPublic());
        String dec = decrypt(enc, keyPair.getPrivate());

        //getPublicKey(readFromFile("KEY/private.key"));
        //RSAKeyGenerator("KEY");
        System.out.println("hello");

        System.out.println(SHA256("helo"));

        if(addUser("svend", "hello")){
            System.out.println("added user");
        }

        if(getUser("svend", "hello")){
            System.out.println("got user");
        }
    }
}
