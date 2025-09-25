package Servlet;

import Mail.MailToCase;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.annotation.WebListener;

@WebListener
public class MailListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 MailListener iniciado: se ejecutará cada 10 segundos.");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                MailToCase.procesarCorreos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.DAYS); // cada 10 segundos
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 MailListener detenido.");
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
