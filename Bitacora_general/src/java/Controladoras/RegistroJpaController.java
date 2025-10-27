package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroJpaController implements Serializable {

    public RegistroJpaController() {
        emf = Persistence.createEntityManagerFactory("BitacoraPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultarRegistros() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consulta_registro`()");

            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.clear();

            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean registrarCabecera(String fecha, String turno, String idZona, String nombreResp) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_registrarCabecera_registro`('" + fecha + "', '" + turno + "', '" + idZona + "', '" + nombreResp + "')");
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

    public List consultarRegistrosPorID(int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rgt_c_consultaid_registro`('" + idRegistro + "')");

            List resultados = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.clear();

            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean guardarDescFallaLinea(String descFalla, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_guardarDescipFallaLinea_registro`('" + descFalla + "', '" + idRegistro + "')");
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

    public boolean eliminarDescFallaLinea(String desc, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_u_eliminarDescripcion_registro`('" + desc + "', '" + idRegistro + "')");
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

    public boolean editarDescFallaLinea(String desc, String descNueva, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_u_editarDescripcion_registro`('" + desc + "', '" + descNueva + "', '" + idRegistro + "')");
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

    public boolean guardarInspeccionLineaSellado(String fallaLineaSellado, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_guardarInspeccionLineaSellado_registro`('" + fallaLineaSellado + "', '" + idRegistro + "')");
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

    public List consultarInspeccionSelladoPorID(int idRegistro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_r_consultarInspeccionSellado_registro`('" + idRegistro + "')");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados == null || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean eliminarDescFallaLineaSellado(String desc, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_u_eliminarDescripcionLinSellado_registro`('" + desc + "', '" + idRegistro + "')");
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

    public boolean editarDescEquipoSellado(String desc, String descNueva, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_u_actualizarLineaSellado_registro`('" + desc + "', '" + descNueva + "', '" + idRegistro + "')");
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

    public boolean guardarDuctoBocas(String fallaDuctoBocas, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_guardarDuctoBocas_registro`('" + fallaDuctoBocas + "', '" + idRegistro + "')");
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

    public boolean guardarSelladoraPp(String frase, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_c_guadarSelladoraPp`('" + frase + "', '" + idRegistro + "')");
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

    public boolean guardarSelladoraColpitt(String frase, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_c_guadarSelladoraColpitt`('" + frase + "', '" + idRegistro + "')");
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

    public boolean editarDuctoBocas(String desc, String descNueva, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_u_editarDuctoBocas_registro`('" + desc + "', '" + descNueva + "', '" + idRegistro + "')");
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

    public boolean editarSelladoraPp(String desc, String descNueva, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_editarSelladoraPp`('" + desc + "', '" + descNueva + "', '" + idRegistro + "')");
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

    public boolean editarSelladoraColpit(String desc, String descNueva, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_editarSelladoraColpit`('" + desc + "', '" + descNueva + "', '" + idRegistro + "')");
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

    public boolean eliminarDescInpeccionBocas(String desc, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_d_eliminarDescInpeccionBocas_registro`('" + desc + "', '" + idRegistro + "')");
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

    public boolean eliminarItemSelladoraPp(String desc, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_d_eliminarItemSelladoraPp`('" + desc + "', '" + idRegistro + "')");
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

    public boolean eliminarItemSelladoraColpitt(String desc, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_d_eliminarItemSelladoraColpitt`('" + desc + "', '" + idRegistro + "')");
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

    public boolean guardarInspeccionEnsamble(String insEnsam, String insEnsamNue, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_c_guardar_inspeccion_ensamble`('" + insEnsam + "', '" + insEnsamNue + "','" + idRegistro + "')");
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

    public boolean guardarObservacion(String observacion, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_c_guardarObservacion`('" + observacion + "', '" + idRegistro + "')");
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

    public boolean editarFirma(String formato, String formatoNuevo, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_agregarFirma`('" + formato + "', '" + formatoNuevo + "', '" + idRegistro + "')");
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

    public boolean editarFirmaSelladoraPP(String formato, String fomatoNuevo, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_agregarFirmaSelladoraPP`('" + formato + "', '" + fomatoNuevo + "', '" + idRegistro + "')");
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

    public boolean editarFirmaSelladoraColpitt(String formato, String fomatoNuevo, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_agregarFirmaSelladoraColpit`('" + formato + "', '" + fomatoNuevo + "', '" + idRegistro + "')");
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

    public boolean editarFirmaDuctoBocas(String formato, String fomatoNuevo, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_agregarFirmaDuctoBocas`('" + formato + "', '" + fomatoNuevo + "', '" + idRegistro + "')");
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

    public boolean editarFirmaSelladoraPp(String formato, String fomatoNuevo, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_agregarFirmaPp`('" + formato + "', '" + fomatoNuevo + "', '" + idRegistro + "')");
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

    public boolean editarEstadoRegistro(int estado, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_actualizarEstadoOrden`('" + estado + "', '" + idRegistro + "')");
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

    public boolean establecerFormatoInspeccionEnsamble(int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_u_establecerFormatoInspeccionEnsamble`('" + idRegistro + "')");
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

    public boolean firmaVerifica(int idVerifica, String fec_hora, String observacion, int idRegistro) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_reg_registrarFirmaVerifica`('" + idVerifica + "', '" + fec_hora + "', '" + observacion + "', '" + idRegistro + "')");
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

    public List responsableRegistro(int idRegistro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_r_rsponsable_registro`('" + idRegistro + "')");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public List responsableRegistro2() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_l_responsableRegistro2`()");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public List cargoRegistroBitacora() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_l_cargoRegistroBitacora`()");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public List responsableVerifica(int idRegistro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reg_r_reponsable_verifica`('" + idRegistro + "')");
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public List filtro(String fechaInicio, String fechaFin, int idUsuario, String turno, int idCargo, String datoGlobal) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String sql = "SELECT r.id_registro, r.fecha, r.turno, r.zona, r.linea_descripcion, r.inspeccion_sellado, r.inspeccion_ensamble, r.inspeccion_bocas, r.inspeccion_selladora_pp, r.inspeccion_selladora_colpitt, r.responsable_verifica, r.usuario_registro, r.estado, r.fecha_hora_registro "
                    + "FROM registro r "
                    + "INNER JOIN usuario u "
                    + "ON r.usuario_registro = u.id_usuario "
                    + "INNER JOIN cargo c  "
                    + "ON u.id_cargo = c.id_cargo "
                    + "WHERE r.fecha  "
                    + "BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' "
                    + "AND (r.linea_descripcion LIKE '%" + datoGlobal + "%'  "
                    + "OR r.inspeccion_ensamble LIKE '%" + datoGlobal + "%' "
                    + "OR r.inspeccion_bocas LIKE '%" + datoGlobal + "%' "
                    + "OR r.inspeccion_selladora_pp LIKE '%" + datoGlobal + "%' "
                    + "OR r.inspeccion_selladora_colpitt LIKE '%" + datoGlobal + "%' "
                    + "OR r.observacion LIKE '%" + datoGlobal + "%'"
                    + "OR r.inspeccion_sellado LIKE '%" + datoGlobal + "%') ";

            if (idUsuario > 0) {
                String sqlUsuario = "AND r.usuario_registro = " + idUsuario + " ";
                sql += sqlUsuario;
            }
            if (turno != null && turno.length() > 0) {
                String sqlTurno = "AND r.turno = " + turno + " ";
                sql += sqlTurno;
            }
            if (idCargo > 0) {
                String sqlCargo = "AND c.id_cargo = " + idCargo + " ";
                sql += sqlCargo;
            }

            Query q = etm.createNativeQuery(sql);
            List resultados = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultados.isEmpty() || resultados.equals("null")) {
                return null;
            } else {
                return resultados;
            }

        } catch (Exception ex) {
            return null;
        }
    }

    public List filtrar(String fechainicio, String fechafin, int id_usuario, String turno, int id_cargo, String palabra) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            int count = 0;
            String ConsultaAnexa = "";
            if (!fechainicio.equals("") && !fechafin.equals("")) {
                String fechas = " r.fecha BETWEEN '" + fechainicio + "' AND '" + fechafin + "' ";
                ConsultaAnexa += fechas;
                count++;
            }

            if (id_usuario != 0) {
                if (count == 0) {
                    String responsable = " r.usuario_registro = " + id_usuario + " ";
                    ConsultaAnexa += responsable;
                    count++;
                } else {
                    String responsable = " AND r.usuario_registro = " + id_usuario + " ";
                    ConsultaAnexa += responsable;
                }
            }

            if (!turno.equals("")) {
                if (count == 0) {
                    String idturno = " r.turno = '" + turno + "' ";
                    ConsultaAnexa += idturno;
                    count++;
                } else {
                    String idturno = " AND r.turno = '" + turno + "' ";
                    ConsultaAnexa += idturno;
                }
            }

            if (id_cargo != 0) {
                if (count == 0) {
                    String cargo = " c.id_cargo = " + id_cargo + " ";
                    ConsultaAnexa += cargo;
                    count++;
                } else {
                    String cargo = " AND c.id_cargo = " + id_cargo + " ";
                    ConsultaAnexa += cargo;
                }
            }

            if (!palabra.equals("")) {
                if (count == 0) {
                    String busqueda = " r.linea_descripcion LIKE CONCAT ('%','" + palabra + "','%') OR r.inspeccion_sellado LIKE CONCAT ('%','" + palabra + "','%') OR r.observacion LIKE CONCAT ('%','" + palabra + "','%') ";
                    ConsultaAnexa += busqueda;
                    count++;
                } else {
                    String busqueda = " AND (r.linea_descripcion LIKE CONCAT ('%','" + palabra + "','%') OR r.inspeccion_sellado LIKE CONCAT ('%','" + palabra + "','%') OR r.observacion LIKE CONCAT ('%','" + palabra + "','%')) ";
                    ConsultaAnexa += busqueda;
                }
            }
            String consulta = "SELECT r.id_registro, r.fecha, r.turno, r.zona, r.linea_descripcion, r.inspeccion_sellado, r.inspeccion_ensamble, r.inspeccion_bocas, r.inspeccion_selladora_pp, r.inspeccion_selladora_colpitt, r.responsable_verifica,r.usuario_registro, r.estado, r.fecha_hora_registro, u.id_cargo "
                    + " FROM registro r "
                    + " INNER JOIN usuario u ON r.usuario_registro = u.id_usuario "
                    + " INNER JOIN cargo c ON u.id_cargo = c.id_cargo "
                    + " WHERE " + ConsultaAnexa + " ;";

            Query q = em.createNativeQuery(consulta);
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List traerregistroid(int Id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_c_consulta_usuario_registro_idregistro`('" + Id + "')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean editarcabecera(String turno, String zona, int Id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_m_modificar_cabeceraderegistro`('" + turno + "', '" + zona + "', '" + Id + "')");
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

    public List consultarturno() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_prm_c_ConsultarParametrosCategoria`('Turno')");
            List resultado = q.getResultList();
            em.getTransaction().commit();
            em.clear();
            em.close();
            if (resultado != null) {
                return resultado;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
