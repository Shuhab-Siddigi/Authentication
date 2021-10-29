package dtu.crypto;


public class SimulatedCrypto {

    // public static String signCredentials(User credentials, String receiver, String identity) {
        
    //     String signedMessage = SimulatedCrypto.Sign(credentials.getUsername() + "," + credentials.getPassword()+"," + identity+",exp(ge,X)",receiver);

    //     System.out.println("Signed Credentials: " + signedMessage);


    //     return signedMessage;
    // }



    // public static User decryptCredentials(String message){
        
    //     System.out.println("Got Credentials: " + message);

    //     String decrypt = SimulatedCrypto.Decrypt(message);

    //     System.out.println("Decrypted Credentials: " + decrypt);

    //     message = SimulatedCrypto.unSign(decrypt);

    //     String[] identity = message.split(",");

    //     return  new User(identity[0], identity[1]);
    // }

    // public static String encryptMessage(String message,User credentials,String identity,String receiver){
        
    //     String signedMessage = SimulatedCrypto.Sign(message+","+credentials.getUsername() + "," + credentials.getPassword(),identity);

    //     System.out.println("Signed Message: " + signedMessage);

    //     String encryptedMessage = SimulatedCrypto.Encrypt(signedMessage, receiver);

    //     System.out.println("Encrypted Message: " + encryptedMessage);

    //     return encryptedMessage;
    // }
 
  

    // public static String decryptMessage(String message){
        
    //     System.out.println("Got message: " + message);

    //     String decrypt = SimulatedCrypto.Decrypt(message);

    //     System.out.println("Decrypted Message: " + decrypt);

    //     message = SimulatedCrypto.unSign(decrypt);

    //     String[] msg = message.split(",");

    //     return  msg[2];
    // }
       
    public static String Sign(String message, String author) {
        return "{" + message + "}" + "PrivateKey(" + author+")";
    }

    public static String Encrypt(String message, String receiver) {
        return "{" + message + "}" + "PublicKey(" + receiver+")";
    }
    public static String unSign(String signedMessage) {
        String substring = signedMessage.substring(1, signedMessage.length());
        return signedMessage.substring(1, substring.lastIndexOf("}")+1);
    }
    public static String Decrypt(String signedMessage) {
        String substring = signedMessage.substring(1, signedMessage.length());
        return signedMessage.substring(1, substring.lastIndexOf("}")+1);
    }
    public static String expEncrypt(String message, String inner,String outer) {
        //{|{C,ath,p}inv(pk(C))|}exp(exp(ge,X),Y)
        return "{|" + message + "|}" + "exp(exp(ge," +inner+"),"+outer+")";
    }
    public static String expDencrypt(String signedMessage) {
        String substring = signedMessage.substring(1, signedMessage.length());
        return signedMessage.substring(2, substring.lastIndexOf("|")+1);
    }
   
    public static String EncryptSharedKey(String message,String message2, String inner, String outer) {
        String enc =  "{|" + message + ",{|"+message2+"|}skag|}";
        return expEncrypt(enc, inner, outer);
    }

    public static String DecryptSharedKey(String message) {
        message = message.substring(2, message.lastIndexOf("|"));
        return message.substring(0, message.lastIndexOf("|"));
    }

    public static String KCG(String message) {
        // {|Print_command|}KCG
        return message =  "{|" + message + "|}KCG";
    }

    public static String DeKCG(String payload) {
        return payload.substring(2, payload.lastIndexOf("|"));
    }

}