package Method;



import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SignatureToImg {

    // Clase interna para mapear las coordenadas del JSON
    public static class Coordenada {
        private int lx, ly, mx, my;

        public int getLx() { return lx; }
        public int getLy() { return ly; }
        public int getMx() { return mx; }
        public int getMy() { return my; }
    }

    /**
     * Genera una imagen PNG a partir de coordenadas JSON y retorna su codificación Base64.
     */
    public static String generarImagenBase64(String json) throws Exception {
        // Convertir el JSON a una lista de coordenadas
        Gson gson = new Gson();
        List<Coordenada> coordenadas = gson.fromJson(json, new TypeToken<List<Coordenada>>() {}.getType());

        // Crear imagen (dimensiones personalizables)
        int width = 100; // Ancho del canvas
        int height = 40; // Alto del canvas
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Dibujar fondo blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Dibujar las líneas a partir de las coordenadas
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2)); // Grosor de la línea
        for (Coordenada coord : coordenadas) {
            g.drawLine(coord.getLx(), coord.getLy(), coord.getMx(), coord.getMy());
        }

        // Convertir la imagen a un arreglo de bytes en formato PNG
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        // Convertir los bytes a una cadena Base64
        String base64Image = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
        g.dispose(); // Liberar recursos
        return base64Image;
    }
}
