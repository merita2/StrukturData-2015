import java.util.Scanner;
import java.io.IOException;
import java.net.UnknownHostException;

public class Utama {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner (System.in);
            Chattugas tgs = new Chattugas();
            
            System.out.println ("Pesan:");
            String masuk = in.next();
            tgs.whois(masuk);
        }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

