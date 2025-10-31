package SQL;

import java.io.BufferedReader;
import java.io.FileReader;

public class Archivos {

    public String leerTxt(String direccion) {
        String texto = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            while ((bfRead = bf.readLine()) != null) {
                temp = temp + bfRead;
            }
            texto = temp;
        } catch (Exception e) {
            System.err.print("No existe archivo");
        }
        return texto;
    }
    
}
