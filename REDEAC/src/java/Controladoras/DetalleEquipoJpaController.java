package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DetalleEquipoJpaController implements Serializable {

    public DetalleEquipoJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List consultaDetalleEquipo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_c_detalle_equipos`()");
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

    public List consultaModificarDetalleEquipo(int id_detalle) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_c_modificar_equipo`('" + id_detalle + "')");
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

    public List consultaDetalleEquipoFiltro(String filtro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_c_detalle_equipo_filtro`('" + filtro + "')");
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

    public List ContadorRed() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_contador_red`()");
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

    public List ContadorAntivirus() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_contador_antivirus`()");
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

    public List ContadorGarantia() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_contador_garantia`()");
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

    public List ContadorEstadoDetalle() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_contador_estado_detalle`()");
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

    public List ContadorTipoEquipo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_deq_contador_tipo_equipo`()");
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

    public List ContadorEstadoEquipo() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("Select COUNT(e.id_equipo), e.estado, (SELECT CONCAT('[',GROUP_CONCAT(e.nombre),']') from  equipo e where e.estado = 'B' order by LENGTH(e.nombre), e.nombre) as PC\n"
                    + "	from equipo e \n"
                    + "		where e.estado = 'B' \n"
                    + "union all \n"
                    + "	Select COUNT(e.id_equipo), e.estado, (SELECT CONCAT('[',GROUP_CONCAT(e.nombre),']') from  equipo e where e.estado = 'R' order by LENGTH(e.nombre), e.nombre) as PC \n"
                    + "	from equipo e \n"
                    + "		where e.estado = 'R' \n"
                    + "union all \n"
                    + "	Select COUNT(e.id_equipo), e.estado, (SELECT CONCAT('[',GROUP_CONCAT(e.nombre),']') from  equipo e where e.estado = 'D' order by LENGTH(e.nombre), e.nombre) as PC\n"
                    + "	from equipo e \n"
                    + "		where e.estado = 'D'");
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

    public boolean registroDetalleEquipo(int id_equipo, String nombre_equipo, String login_Plas, String mac, String antivirus, String win_version, String office_version, String tipo_estado, String activos_soporte, String nombre) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_deq_r_detalle_equipo`(" + id_equipo + ",'" + nombre_equipo + "','" + login_Plas + "', '" + mac + "','" + antivirus + "','" + win_version + "','" + office_version + "','" + tipo_estado + "', '" + activos_soporte + "','" + nombre + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ModificarDetalleEquipo(int id_detalle, String neq, String lpt, String ip, String mac, String red, String vlan, String wit, String oit, String atv, String itn, String vpn, String skye, String gml, String cit, String cet, String fct, String fft, String lcc, String ffg, String pvd, String grt, String asp, String tsw, String tet) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_deq_m_detalle_equipo`('" + id_detalle + "','"
                    + neq + "','"
                    + lpt + "','"
                    + ip + "','"
                    + mac + "','"
                    + red + "','"
                    + vlan + "','"
                    + wit + "','"
                    + oit + "','"
                    + atv + "','"
                    + itn + "','"
                    + vpn + "','"
                    + skye + "','"
                    + gml + "','"
                    + cit + "','"
                    + cet + "','" 
                    + fct + "','"
                    + fft + "','"
                    + lcc + "','"
                    + ffg + "','"
                    + pvd + "','"
                    + grt + "','"
                    + asp + "','"
                    + tsw + "','"
                    + tet + "')");
            int resultado = q.executeUpdate();
            emt.getTransaction().commit();
            emt.clear();
            emt.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String Filtro_dinamico(String tipo_estado, String fto, String cmp) {
        String[] filtro = cmp.replace("][", "///").replace("[", "").replace("]", "").split("///");
        String[] filtro2 = fto.replace("][", "///").replace("[", "").replace("]", "").split("///");
        String condicion = "";
        if (!tipo_estado.equals("ALL")) {
            condicion = " d.tipo_estado = '" + tipo_estado + "' AND ";
        }
        for (int i = 0; i < filtro.length; i++) {
            for (int n = 0; n < filtro2.length; n++) {
                if (i != (filtro.length - 1) || n != (filtro2.length - 1)) {
                    condicion = condicion + "" + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%') OR ";
//                    condicion = condicion + "d." + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%') OR ";
                } else {
                    condicion = condicion + "" + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%')";
//                    condicion = condicion + "d." + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%')";
                }
            }
//            if (i == (filtro.length - 1)) {
//                condicion = condicion + ")";
//            }

        }
        String query = ("select d.id_detalleE,d.id_equipo,e.nombre,d.red,e.tipo,d.mac,d.punto_red,e.cargo,d.nombre_equipo,e.responsable,\n"
                + "	d.login_plastitec,d.login_novell,d.login_solin,d.login_plastitecsa,d.dominio,d.ip_anterior,\n"
                + "	d.ip_nueva,d.mascara,d.puerta_enlance,e.correo,d.vlan,d.win_version,d.win_tipo,d.win_factura_version,\n"
                + "	d.win_factura_tipo,d.office_version,d.office_tipo,d.office_factura_version,d.office_factura_tipo,d.factura,d.fecha_factura,d.licencia,d.fecha_garan,d.proveedor,d.garantia,d.estado,d.antivirus,\n"
                + "	d.internet,d.descripcion,d.stiker_win,d.stiker_office,d.serial_windows,d.serial_office,d.software_antivirus,\n"
                + "	d.software_internet,d.software_adobe,d.software_pausas,d.software_flash,d.software_suit,d.tipo_software,e.estado,d.tipo_estado\n"
                + "	from detalle_equipo d\n"
                + "		inner join equipo e on d.id_equipo = e.id_equipo\n"
                + "		where " + condicion + "\n");
        return query;
    }

    public List ConsultaQuery(String query) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("" + query + "");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
