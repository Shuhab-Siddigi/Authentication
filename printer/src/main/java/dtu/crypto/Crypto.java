package dtu.crypto;

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
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Crypto {
    

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



}
