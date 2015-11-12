import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException; 

public class KopiBerkasBuffer {
   
    public void kopi(String sumber, String sasaran) throws IOException {
                
        FileInputStream masukanfile = null;
        FileOutputStream keluaranfile= null;
        
        BufferedInputStream masukan = null;
        BufferedOutputStream keluaran= null;
        
        
        try {
           	masukanfile = new FileInputStream(sumber);
            keluaranfile = new FileOutputStream (sasaran);
            
            masukan = new BufferedInputStream(masukanfile);
            keluaran = new BufferedOutputStream (keluaranfile);

            
            int karakter = masukan.read();
            while (karakter != -1) {
    			keluaran.write (karakter);
                 karakter = masukan.read();
                }
            keluaran.flush();
        }

        catch (IOException kesalahan){
            System.out.printf ("terjadi kesalahan: %s", kesalahan);
        }
        
        finally { 
           
            if (masukan != null)
                masukan.close();

            if (keluaran != null)
                keluaran.close();

        }
    }
}

    

