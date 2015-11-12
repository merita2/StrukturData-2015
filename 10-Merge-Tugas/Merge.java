import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
public class Merge{
  
    public void merge(String file1, String file2, String file3, String sasaran) throws IOException {
        FileInputStream File1 = null;
        FileInputStream File2 = null;
        FileInputStream File3 = null;
        FileOutputStream keluaran= null;
        
        
        try {          
            File1 = new FileInputStream(file1);
            File2 = new FileInputStream(file2);
            File3 = new FileInputStream(file3);
            keluaran = new FileOutputStream(sasaran); 

          
            int karakter = File1.read();
            
            while (karakter != -1) {
                
                keluaran.write(karakter);
                karakter = File1.read();
            }
            karakter = File2.read();
            
             while (karakter != -1) {// kalau minus 1 dia selesai
               keluaran.write(karakter);
                karakter = File2.read();
            }
            karakter = File3.read();
            
             while (karakter != -1) {
                keluaran.write(karakter);
                karakter = File3.read();
            }
            keluaran.flush();
        } 
        finally {
            
            if (file1 != null)           
            File1.close();
            if (file2 != null)           
            File2.close();
            if (file3 != null)           
            File3.close();
            if (keluaran != null)
                keluaran.close();

        }
    }
}
