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
            // 🔹 Leer el cuerpo JSON de la petición
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject json = new JSONObject(sb.toString());
            String base64Data = json.optString("imageData", null);
            String rol = json.optString("rol", "Default");
            String idusuario = json.optString("idusuario", "0");

            if (base64Data == null || base64Data.isEmpty()) {
                throw new Exception("No se recibió imagen base64");
            }

            // 🔹 Extraer los bytes de la imagen base64
            base64Data = base64Data.replaceFirst("^data:image/[^;]+;base64,", "");
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // 🔹 Construir ruta física para guardar la imagen
            String uploadDir = getServletContext().getRealPath(
                "/elFinder/files/Usuarios/" + idusuario + "/"
            );

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // crea carpeta si no existe
            }

            // 🔹 Crear nombre único de archivo
            String fileName = "img_" + System.currentTimeMillis() + ".png";
            File file = new File(dir, fileName);

            try (OutputStream os = new FileOutputStream(file)) {
                os.write(imageBytes);
            }

            // 🔹 Construir URL pública (para mostrar en el editor)
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/elFinder/files/Usuarios/" + idusuario + "/" + fileName;

            jsonResponse.put("status", "success");
            jsonResponse.put("url", baseUrl);

            // 🔹 Log de depuración
            System.out.println("✅ Imagen guardada en: " + file.getAbsolutePath());
            System.out.println("🌐 URL pública: " + baseUrl);

        } catch (Exception ex) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", ex.getMessage());
            ex.printStackTrace();
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
        }
    }
}
