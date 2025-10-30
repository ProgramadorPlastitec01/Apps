package Servlet;

import java.io.*;
import java.util.Base64;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;

@WebServlet("/UploadPasteImageServlet")
public class UploadPasteImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        JSONObject jsonResponse = new JSONObject();

        try {
            // 🔹 Leer el cuerpo JSON enviado por JavaScript
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject json = new JSONObject(sb.toString());
            String base64Data = json.optString("imageData", "");
            String rol = json.optString("rol", "General");
            String idUsuario = json.optString("idusuario", "0");

            if (base64Data.isEmpty()) {
                throw new Exception("No se recibió imagen en formato Base64");
            }

            // 🔹 Decodificar imagen base64
            base64Data = base64Data.replaceFirst("^data:image/[^;]+;base64,", "");
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // 🔹 Construir ruta física para guardar la imagen (por usuario)
            // 🔹 Guardar directamente en la carpeta de elFinder de XAMPP
            // 🔹 Nueva ruta: dentro de /Usuarios/<id>/ImagenesCapturadas
            String uploadDir = "C:\\xampp\\htdocs\\elFinder\\files\\Usuarios\\" + idUsuario + "\\ImagenesCapturadas\\";

            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + uploadDir);
            }

            // 🔹 Crear nombre único de archivo
            String fileName = "img_" + System.currentTimeMillis() + ".png";
            File file = new File(dir, fileName);

            // 🔹 Guardar el archivo físicamente
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(imageBytes);
            }

            // 🔹 Construir URL pública accesible desde el editor
            String fileUrl = "http://172.16.1.164/elFinder/files/Usuarios/"
                    + idUsuario + "/ImagenesCapturadas/" + fileName;

            // 🔹 Armar respuesta JSON
            jsonResponse.put("status", "success");
            jsonResponse.put("url", fileUrl);
            jsonResponse.put("rol", rol);
            jsonResponse.put("usuario", idUsuario);
            jsonResponse.put("path", uploadDir);

            // 🔹 Log para depuración
            System.out.println("Imagen guardada correctamente:");
            System.out.println("Ruta local: " + file.getAbsolutePath());
            System.out.println("URL pública: " + fileUrl);

        } catch (Exception ex) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", ex.getMessage());
            ex.printStackTrace();
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
