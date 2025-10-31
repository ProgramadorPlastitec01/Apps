package Servlets;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.file.*;

@ServerEndpoint("/filewatcher/{bascula}")
public class FileWatcherWebSocket {

    @OnOpen
    public void onOpen(Session session, @PathParam("bascula") String bascula) {
        System.out.println("Conexión WebSocket abierta para la báscula: " + bascula);
        new Thread(() -> watchFile(session, bascula)).start();
        sendInitialContent(session, bascula);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Conexión WebSocket cerrada");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private void watchFile(Session session, String bascula) {
//        String filePath = "C:/Users/programador.ti2/Documents/test/" + bascula + ".txt";
        String filePath = "//172.16.2.117/c/files/Reg/test/" + bascula + ".txt";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.err.println("El archivo no existe: " + filePath);
            return;
        }

        Path parentDir = path.getParent();
        if (parentDir == null) {
            System.err.println("El directorio padre es nulo para el archivo: " + filePath);
            return;
        }

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            System.out.println("Monitoreando el archivo: " + filePath);

            while (session.isOpen()) {
                WatchKey key;
                try {
                    key = watchService.take(); 
                } catch (InterruptedException e) {
                    System.err.println("La espera del watch service fue interrumpida");
                    Thread.currentThread().interrupt(); 
                    break;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path changed = (Path) event.context();
                        if (changed.endsWith(path.getFileName())) {
                            String content = new String(Files.readAllBytes(path));
                            session.getBasicRemote().sendText(content);
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    System.err.println("El WatchKey no es válido, terminando la monitorización del archivo.");
                    break;
                }
            }
        }catch (IOException e) {
            System.err.println("Ocurrió un error de IO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendInitialContent(Session session, String bascula) {
        String filePath = "C:/Users/programador.ti2/Documents/test/" + bascula + ".txt";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.err.println("El archivo no existe: " + filePath);
            return;
        }

        try {
            String content = new String(Files.readAllBytes(path));
            session.getBasicRemote().sendText(content);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
