package Methods;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Directory {

    public void crearCarpeta(String nombreCarpeta) {
        String basePath = "C:\\xampp\\htdocs\\Archivo_DYD\\flmngr\\files\\"; // Ajusta la ruta según tu sistema
        Path path = Paths.get(basePath, nombreCarpeta);

        try {
            Files.createDirectories(path);
            System.out.println("Carpeta creada exitosamente: " + path.toString());
        } catch (IOException e) {
            System.err.println("Error creando la carpeta: " + e.getMessage());
        }
    }

}
