package dtu.crypto;

public class SimulatedCrypto {
    

    public String Sign(String message, String author) {


        return  "{" + message + "}" + "K_private_"+ author;
    }

    public String unSign(String signedMessage) {

        String substring = signedMessage.substring(1,signedMessage.length());
        return signedMessage.substring(1,substring.indexOf("}"));
    }

    public String Encrypt(String message, String receiver) {


        return  "{" + message + "}" + "K_public_"+ receiver;
    }

    public String Decrypt(String signedMessage) {

        String substring = signedMessage.substring(1,signedMessage.length());
        return signedMessage.substring(1,substring.indexOf("}"));
    }


}
