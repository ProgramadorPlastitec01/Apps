package GLPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties = new Properties();

    public ConfigLoader() {
        try {
//            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            InputStream input = new FileInputStream("C:/Apps/SIRH/config.properties");
//            InputStream input = new FileInputStream("config.properties");
            if (input == null) {
                System.err.println("❌ Error: No se encontró config.properties en el classpath.");
                throw new FileNotFoundException("Archivo config.properties no encontrado en resources");
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

}
