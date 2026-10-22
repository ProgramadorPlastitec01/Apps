package Metodos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Duenio de la unica EntityManagerFactory del aplicativo.
 *
 * POR QUE EXISTE ESTA CLASE
 * Cada *JpaController creaba su propia fabrica en el constructor y no la
 * cerraba nunca. Como los Tags instancian varios controladores por request,
 * cada pagina dejaba varias fabricas vivas, y cada fabrica se queda con su
 * conexion fisica a MySQL: medido, +1 conexion por controlador, que nunca
 * vuelve. Con wait_timeout en 8 horas esas conexiones no se reciclan y el
 * contador sube hasta max_connections (151), momento en el que MySQL rechaza
 * conexiones nuevas y la aplicacion se queda esperando.
 *
 * Ademas, construir una fabrica obliga a EclipseLink a leer el mapeo de
 * entidades: medido, ~1,8 s la primera vez. Compartiendo una sola fabrica ese
 * costo se paga una vez por despliegue y no una vez por controlador.
 *
 * La fabrica es cara de crear y segura para usar desde varios hilos; los
 * EntityManager no lo son, por eso cada metodo sigue pidiendo el suyo y
 * cerrandolo.
 */
@WebListener
public class JPA implements ServletContextListener {

    private static final String UNIDAD = "SolicitudesProyectosPU";
    private static volatile EntityManagerFactory emf;

    /**
     * Fabrica compartida por todos los controladores.
     * Se construye la primera vez que se pide (doble comprobacion para que dos
     * requests simultaneos no construyan dos fabricas).
     *
     * @return la unica EntityManagerFactory del aplicativo
     */
    public static EntityManagerFactory getEmf() {
        EntityManagerFactory local = emf;
        if (local == null) {
            synchronized (JPA.class) {
                local = emf;
                if (local == null) {
                    local = Persistence.createEntityManagerFactory(UNIDAD);
                    emf = local;
                }
            }
        }
        return local;
    }

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        // Se construye al arrancar y no en el primer login: asi el usuario no
        // paga los ~1,8 s de lectura del mapeo en su primera pantalla.
        getEmf();
    }

    /**
     * Cierra la fabrica al parar o redesplegar la aplicacion. Sin esto las
     * conexiones quedan abiertas en MySQL despues de cada redespliegue.
     */
    @Override
    public void contextDestroyed(ServletContextEvent evento) {
        EntityManagerFactory local = emf;
        emf = null;
        if (local != null && local.isOpen()) {
            local.close();
        }
    }
}
