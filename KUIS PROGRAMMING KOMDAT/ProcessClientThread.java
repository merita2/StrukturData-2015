import java.net.Socket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Calendar;

public class ProcessClientThread implements Runnable {
    private String SIAPA ="SIAPA";
    private String WAKTU = "WAKTU";
    String hasil= null;
    OutputStream keluaran =null;
    BufferedWriter keluaranBuf = null;
    Calendar kalender = Calendar.getInstance();
    String waktuStr = kalender.getTime().toString();

    public ProcessClientThread(Socket koneksiKiriman) {
        koneksi = koneksiKiriman;
    }

    public void run() {
        try{
            if (koneksi != null)
                prosesPermintaanClient(koneksi);
        }   
        catch(IOException err) {
            System.out.println(err);
        }
        catch(InterruptedException err) {
            System.out.println(err);
        }
    }

    private void prosesPermintaanClient(Socket koneksi) 
    throws InterruptedException, IOException {
        String ip = koneksi.getInetAddress().getHostAddress();
        System.out.println("Dari: " + ip);

        // Ambil dan tampilkan masukan

        InputStream masukan = koneksi.getInputStream();
        BufferedReader masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
        String baris = masukanReader.readLine();

        System.out.println(baris);

        OutputStream keluaran = koneksi.getOutputStream();
        BufferedWriter keluaranBuf = new BufferedWriter(new OutputStreamWriter(keluaran));

        if (baris.equals(SIAPA)){
            hasil=""+ip;
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter (new OutputStreamWriter(keluaran));
            keluaranBuf.write(hasil);
            keluaranBuf.newLine();
            keluaranBuf.flush();
            koneksi.close();

        }   
        else if(baris.equals(WAKTU)){
            hasil=""+waktuStr;
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter (new OutputStreamWriter(keluaran));
            keluaranBuf.write(hasil);
            keluaranBuf.newLine();
            keluaranBuf.flush();
            koneksi.close();
        }
        /* else if(baris.equals(WAKTU_N)){
            hasil=""+waktu_N Str;
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter (new OutputStreamWriter(keluaran));
            keluaranBuf.write(hasil);
            keluaranBuf.newLine();
            keluaranBuf.flush();
            koneksi.close();
        }*/

        else {
            hasil="perintah tidak dikenali";
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter (new OutputStreamWriter(keluaran));
            keluaranBuf.write(hasil);
            keluaranBuf.newLine();
            keluaranBuf.flush();
            koneksi.close();
        }

    }
    private Socket koneksi; 
}