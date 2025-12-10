package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UsuarioJpaController {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("LocativosPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public List Usuario_sesión(String usa, String pwd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_sesion`('" + usa + "','" + pwd + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Usuarios() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_c_usuarios`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Usuarios_filtro(String fto) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_usuarios_filtro`('" + fto + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Traer_usuario(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_usuario`('" + ius + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public boolean Registrar_usuario(String nbe, String apl, int dcm, int cdg, String cor, String usr, int irl, int iare, String uss) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_r_usuario`('" + nbe + "','" + apl + "','" + dcm + "','" + cdg + "','" + cor + "','" + usr + "','" + irl + "','" + iare + "','" + uss + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean Modificar_usuario(int ius, String nbe, String apl, int dcm, int cdg, String cor, String usr, int irl, int iare, String uss) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_m_usuario`('" + ius + "','" + nbe + "','" + apl + "','" + dcm + "','" + cdg + "','" + cor + "','" + usr + "','" + irl + "','" + iare + "','" + uss + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean Activar_usuario(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_m_activar`('" + ius + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean Desactivar_usuario(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_m_desactivar`('" + ius + "')");
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public List Traer_ejecutor() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usu_t_ejecutor`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public List Correo_de_solicitud_y_copia(int irol, int iuser) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("select CONCAT(u.id_usuario),GROUP_CONCAT(u.correo) from usuario u where u.id_rol = " + irol + " or u.id_usuario = " + iuser + "");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            }
            return consulta;
        } catch (Exception ex) {
        }
        return null;
    }

    public boolean Restablecer_password(int ius) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE usuario SET password = YEAR(CURDATE()) WHERE id_usuario = " + ius);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Cambiar_password(int ius, String pwd) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE usuario SET password = '" + pwd + "'WHERE id_usuario = " + ius);
            int exitoso = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (exitoso == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

}
