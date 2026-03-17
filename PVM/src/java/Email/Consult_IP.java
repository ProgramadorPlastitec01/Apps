package Email;

import java.net.*;

public class Consult_IP {
   public static void main(String[] args) {
      byte[] b;
      try{
         //Si se pasa un argumento obtenemos la direccion IP
         if (args.length > 0){
            String host_remoto = args[0];
            InetAddress address = InetAddress.getByName(host_remoto);
            String IP_remota = address.getHostAddress();
            System.out.println("Direccion IP de la maquina remota : " + IP_remota);
         }
         //Si no obtenemos la direccion IP de la maquina local
         else{
            InetAddress direccion = InetAddress.getLocalHost();
            String nombreDelHost = direccion.getHostName();//nombre host
            String IP_local = direccion.getHostAddress();//ip como String
            System.out.println("La IP de la maquina local es : " + IP_local);
            System.out.println("El nombre del host local es : " + nombreDelHost);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
}