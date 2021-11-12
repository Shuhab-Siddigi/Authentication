package dtu.crypto;


public class SimulatedCrypto {

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


    public static String getFunctions(String ticket) {
        return ticket.substring(ticket.indexOf("[")+1, ticket.lastIndexOf("]"));
    }

}