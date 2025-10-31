package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistroDetalleJpaController implements Serializable {

    public RegistroDetalleJpaController() {
        emf = Persistence.createEntityManagerFactory("Registro_pesajePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LISTAS">
    public List ConsultarRegistroDetalle() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rdell_c_consultarRegistroDetalle`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarRegistroDetalle_id(int id_reg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rdt_c_ConsultarRegistroDell_id`('" + id_reg + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarDetalle_id(int id_dell) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rdt_c_ConsultarDetalle_id`('" + id_dell + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarDetalle_ultimo() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rdll_c_consultarRegistroDetalle_ultimo`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_defectos_activos() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_consultar_dectos_activos`()");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_defectosId(int idOrden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dft_c_consultar_dectos_id`('" + idOrden + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_ObservacionesId(int id_red) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_rdt_c_consultar_obsrvacionesId`('" + id_red + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List Consultar_estadosxOrden(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_pesaje_c_consulta_EstadosxOrden`('" + id_orden + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarControlesCuarentena(int id_reg) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ctr_c_ConsultarControlesCuarentenas`(" + id_reg + ")");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarControlesCuarentenaId(int id_control) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_ctr_c_ConsultarControlesCuarentenasId`('" + id_control + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarCuarentenasXcontrol(int id_control) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cua_c_ConsultacuarentenasxControl`('" + id_control + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarControlxCuarentena(int id_control, int id_cuarentena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dll_c_ConsultarDatosControlxCuarentena`('" + id_control + "', '" + id_cuarentena + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarFirmasxRevision(int id_cuarentena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dll_c_ConsultarFirmasRevision`('" + id_cuarentena + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarFirmasxInspectora(int id_cuarentena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dll_c_ConsultarFirmasInspectora`('" + id_cuarentena + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarFirmasxCoordinadora(int id_cuarentena) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_dll_c_ConsultarFirmasCoordinadora`('" + id_cuarentena + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarCuarentenasXOrderTurno(int id_orden, int id_registro, int turno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cua_c_ConsultaCuarentenasxOrdenTurno`('" + id_orden + "','" + id_registro + "','" + turno + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List ConsultarCuarentenasXOrder(int id_orden) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Query q = em.createNativeQuery("CALL `sp_cua_c_ConsultaCuarentenasxOrden`('" + id_orden + "')");
            List resultados = q.getResultList();
            em.getTransaction().commit();;
            em.clear();
            em.close();
            if (resultados != null) {
                return resultados;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESO">

    public boolean registar_regDetalle(int id_reg, String txt_turno, String txt_personal, String usu_reg, int bascula) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_r_registrarDetalle`('" + id_reg + "', '" + txt_turno + "', '" + txt_personal + "', '" + usu_reg + "','" + bascula + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_defectoDetalle(int IdCuarent, String defectos) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_r_registros_dectos_registroDetalle`('" + IdCuarent + "', '" + defectos + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarRegistroDetalle(int id_reg, String txt_turno, String txt_personal, int bascula) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdt_m_modificarRegistroDetalle`('" + id_reg + "', '" + txt_turno + "', '" + txt_personal + "','" + bascula + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ActualizarPeso(int ird, int hora, double peso) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "UPDATE registro_detalle "
                    + "SET peso_" + hora + " = " + peso + ""
                    + "WHERE id_registroDetalle = " + ird + "";
            Query q = etm.createNativeQuery(query);
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean FirmarCuarentena(int idcontrol, int idcuarentena, int event, int idUSer) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        String signature = "";
        try {
            if (event == 1) {
                signature = "c.resp_revision";
            } else if (event == 2) {
                signature = "c.insp_calidad";
            } else if (event == 3) {
                signature = "c.coord_produccion";
            }
            String query = "UPDATE cuarentena c "
                    + "SET " + signature + " = " + idUSer + " "
                    + "WHERE c.id_control = " + idcontrol + " AND c.id_cuarentena = " + idcuarentena + " ";
            Query q = etm.createNativeQuery(query);
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_Horas_Iniciales(int ird, int hora) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "UPDATE registro_detalle "
                    + "SET fecha_inicio_" + hora + " =  STR_TO_DATE(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), '%Y-%m-%d %H:%i:%s') "
                    + "WHERE id_registroDetalle = " + ird + "";
            Query q = etm.createNativeQuery(query);
            q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_Horas_Finales(int ird, int hora) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "UPDATE registro_detalle "
                    + "SET fecha_final_" + hora + " = DATE_ADD(fecha_inicio_" + hora + ", INTERVAL 1 HOUR) "
                    + "WHERE id_registroDetalle = " + ird + "";
            Query q = etm.createNativeQuery(query);
            q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ActualizarEstado(int ird) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            Query q = etm.createNativeQuery("CALL `sp_rdl_m_modificar_estado`('" + ird + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Actualziar_control(int ird, String control) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            Query q = etm.createNativeQuery("CALL `sp_rdt_a_actualizar_campoControl`('" + ird + "','" + control + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_tiempo(int ird, String tmp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            Query q = etm.createNativeQuery("CALL `sp_rdt_r_registrar_tiempo`('" + ird + "','" + tmp + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_obsrvaciones(int ird, String obs) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            Query q = etm.createNativeQuery("CALL `sp_rdt_m_modificar_obsrvaciones`('" + ird + "','" + obs + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Cambiar_estado_detalle(int ird, int est) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {

            Query q = etm.createNativeQuery("CALL `sp_rdl_m_modificarEstadoDetalle`('" + ird + "','" + est + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Cambiar_Estados_Masivo(int id_ord) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pesaje_ActualizarEstadosAutomatico`('" + id_ord + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Liberar_basculas(int id_registroDll) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdl_a_Liberar_basculas`('" + id_registroDll + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Seleccion_Basculas(int id_registroDll, int id_basula) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rdl_a_seleccionBasculas`('" + id_registroDll + "','" + id_basula + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean RegistrarControlesCuarentenas(int id_reg, String Turno, String txtBase, String txtPiston) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ctr_r_RegistrarControlesCuarentenasId`('" + id_reg + "','" + Turno + "','" + txtBase + "', '" + txtPiston + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarControlesCuarentenas(int idControl, int id_reg, String txtBase, String txtPiston) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ctr_r_UpdateControlesCuarentenasId`('" + idControl + "','" + id_reg + "','" + txtBase + "', '" + txtPiston + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Registrar_CabeceraCuarentenas(int id_control, String Nrocuarent, int und, String defectos, String userRegister) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_cta_r_registrarCuarentena`(" + id_control + ",'" + Nrocuarent + "'," + und + ",'" + defectos + "','" + userRegister + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean CerrarCuarentenas(int Nrocuarent, String defCuare, int UnidAprob) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_dll_a_cerraCuarentena`(" + Nrocuarent + ",'" + defCuare + "', " + UnidAprob + ")");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean LimpiarPesoHora(int idDetalle, int valor) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Query = "UPDATE registro_detalle r "
                    + "SET peso_" + valor + " = 0, r.fecha_inicio_" + valor + " = NULL, r.fecha_final_" + valor + " = NULL "
                    + "WHERE r.id_registroDetalle = " + idDetalle + "";
            Query q = etm.createNativeQuery(Query);
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean LimpiarPesoHoraGeneral(int idDetalle, int valor) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Query = "UPDATE registro_detalle r ";
            for (int i = valor; i <= 8; i++) {
                if (i == 8) {
                    Query = Query + " peso_" + i + " = 0, r.fecha_inicio_" + i + " = NULL, r.fecha_final_" + i + " = NULL";
                } else {
                    if (i == 1) {
                        Query = Query + "SET peso_" + i + " = 0, r.fecha_inicio_" + i + " = NULL, r.fecha_final_" + i + " = NULL,";
                    } else {
                        Query = Query + " peso_" + i + " = 0, r.fecha_inicio_" + i + " = NULL, r.fecha_final_" + i + " = NULL,";
                    }
                }
            }
            Query = Query + " WHERE r.id_registroDetalle = " + idDetalle + "";
            Query q = etm.createNativeQuery(Query);
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
//</editor-fold>
}
