package dtu.crypto;

public class SimulatedCrypto {
    

    public String Sign(String message, String author) {


        return  "{" + message + "}" + "K_private_"+ author;
    }


}
