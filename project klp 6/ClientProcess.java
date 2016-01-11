import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner; 
import java.lang.String;
import java.util.Calendar;

public class ClientProcess implements Runnable {
    ArrayList<DATA> IDENTITY = new ArrayList<DATA>();
    
    public ClientProcess(Socket koneksi, ArrayList<DATA>IDENTITY ){
        this.koneksi = koneksi;
        this.IDENTITY=IDENTITY;
    }

    public void run() {        
        if (koneksi != null) {
            ipStr = koneksi.getInetAddress().getHostAddress();
            try {
                // Ambil InputStream
                masukan = koneksi.getInputStream();
                masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
                // Ambil OutputStream
                keluaran = koneksi.getOutputStream();
                keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 
                collect();
            }
            catch(IOException salah) { 
                System.out.println(salah);
            }
            finally {
                try { 
                    koneksi.close();
                }
                catch(IOException salah) {
                    System.out.println(salah);
                }                
            }
        }
    }
    
    
   private void collect() throws IOException {
        String perintah = masukanReader.readLine();
        // Jika tidak ada perintah keluar saja
        if (perintah == null)
            return;            
        perintah = perintah.trim().toUpperCase();
       
        String[] parameter = perintah.split(" ");
        
        if (parameter[0].compareTo("ID") == 0 && parameter[2].equals("MELANGKAH")&& parameter.length==5) {
            DATA spn = null;
            String Group=null;
              
            for(DATA kpl : IDENTITY) {
                if(parameter[1].equals (kpl.getIdentity()))
                spn = kpl;
            }
            
           
            
            if(spn != null) {
               Group=parameter [3]+ " " +parameter [4];
               spn.setGroup(Group);
            } 
            else {
               spn = new DATA();
               spn.setUserId(parameter[1]);
               Group=parameter [3]+ " " +parameter [4];
               spn.setGroup(Group);
               IDENTITY.add(spn);
            }
            //hanya bertanya siapa yang mengrim
            keluaranWriter.write("Success process");
            System.out.println("ID "+ parameter[1]);
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }
        
        else if((parameter[0].compareTo("ID") == 0 && parameter[2].equals("JUMLAH_LANGKAH") && parameter.length==6)){
            //ambil koor tampung
            DATA spn = null;
            String keluaran = null;
            
            for(DATA kpl: IDENTITY){
                if(parameter[1].equals(kpl.getIdentity()))
                spn = kpl;
            }
            
            if(spn != null) {
                keluaran = spn.getJAM(parameter[4],parameter[5]);
            }
            
            if (keluaran == null) {
                keluaran = "ERROR SENDING MESSAGES...";
            }
            //ambil jlh tampung
             //keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
           keluaranWriter.write(keluaran);
           keluaranWriter.newLine();
           keluaranWriter.flush();
        }
        
         else {
            //Jika Perintah tidak dikenal
            keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }
      //tampil perintah
        System.out.println("Dari: " + ipStr);
        System.out.println("perintah: " + perintah);
        System.out.println();
  
   }

     
    // Koneksi ke Client
    private Socket koneksi; 
    // IP address dari client
    private String ipStr; 
    
    //InputStream untuk baca perintah
    private InputStream masukan = null;
    // Reader untuk InputStream, pakai yang buffer
    private BufferedReader masukanReader = null;
    // OutputStream untuk tulis balasan
    private OutputStream keluaran = null;
    // Writer untuk OutputStream, pakai yang buffer
    private BufferedWriter keluaranWriter = null;
    
    private final static String PERINTAH_TIDAK_DIKENAL = "SENDING ERROR!";
}