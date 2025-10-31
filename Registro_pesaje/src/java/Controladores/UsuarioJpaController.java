package Controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List UsuarioSesion(String usa, String pas) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_sesion`('" + usa + "', '" + pas + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List login(String usa, String pas) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usr_t_login_usuario`('" + usa + "', '" + pas + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaUsuario() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usr_c_consultar_usuarios`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List ConsultaUsuarioId(int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usr_c_consulta_usuario_id`('" + id_usuario + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean RegistrarUsuario(String nmb, String apl, int dcm, int cdg, String user, String pass, int id_rol,String urg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usr_r_registar_usuario`('" + nmb + "','" + apl + "','" + dcm + "','" + cdg + "','" + user + "','" + pass + "','" + id_rol + "','" + urg + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean cambiarPass(int isa, String ctn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update usuario set password= '" + ctn + "' where id_usuario=" + isa + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarUsuario(String nmb, String apl, int dcm, int cdg, String user, int id_rol, int ius) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usr_m_modificar_usuario`('" + nmb + "','" + apl + "','" + dcm + "','" + cdg + "','" + user + "','" + id_rol + "','" + ius + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean CambiarEstadoUsuario(int etd, int ius) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usr_m_cambiar_estado`('" + etd + "','" + ius + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public boolean RestablecerPassword(int ius) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_usr_m_restablecer_password`('" + ius + "')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="PERFIL">
    
    public boolean cambiarFotoPerfil(int id_user, String img_ic) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prl_m_modificarLogoUsuario`('" + id_user + "','"+ img_ic +"')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean CAmbiarNombreApellido(int id_user, String txt_nombres, String txt_apellido) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prf_m_modificarNombreApellido`('" + id_user + "','"+ txt_nombres +"', '"+ txt_apellido +"')");
            int resultado = q.executeUpdate();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public List ConsultarLogoUsuario(int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_prl_c_consultarLogoUsuario`('" + id_usuario + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (!consulta.isEmpty()) {
                return consulta;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    
    //</editor-fold>

}
