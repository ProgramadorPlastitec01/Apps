package Controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ParadaMaquinaJpaController {

    public ParadaMaquinaJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Parada_maquinas_categoria(int ict) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pmq_t_paradas_categoria`('" + ict + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Paradas_maquinas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pmq_c_paradas_maquina`()");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_parada_maquina(String nbe, int ict, int itl, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pmq_r_parada_maquina`('" + nbe + "','" + ict + "','" + itl + "','" + urg + "')");
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

    public List Parada_maquinas_categoria_registradas(int ict, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_c_paradas_categoria`('" + ict + "','" + irg + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Parada_maquinas_categoria_registradas_id(int idCat, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_c_paradas_categoria_id`('" + idCat + "','" + irg + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Parada_maquinas_sumas(int irg, int cat) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_c_paradas_sumParadas`('" + irg + "','" + cat + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Parada_maquinas_categoria_registradas_idParada(int cate, int irg, int idpm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_c_paradas_categoria_idparada`('" + cate + "','" + irg + "','" + idpm + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Consultar_idpard_pmtt(int id_reg, int idprcate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_c_consultar_IdPardasxRegistro`('" + id_reg + "', '" + idprcate + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Consultar_idpard_pmtt_idcate(int idprcate) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT p.id_parada_maquina, p.nombre, p.id_categoria, p.id_tipo_linea, p.usuario_registro, p.fecha_registro "
                    + "FROM parada_maquina p "
                    + "WHERE p.id_parada_maquina = " + idprcate + "");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List Traer_parada_maquinas_registradas(int irg, int ipm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_t_paradas_maquina_registro`('" + irg + "','" + ipm + "')");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Registrar_parada_maquina_registro(int irg, int ipm, int ctd, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_r_registro_parada_maquina`('" + irg + "','" + ipm + "','" + ctd + "','" + urg + "')");
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

    public boolean Registrar_parada_maquina_registro_pmtt(int irg, int ipm, int ctd, int hra, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_r_registro_parada_maquina_pmtt`('" + irg + "','" + ipm + "','" + ctd + "','" + hra + "','" + urg + "')");
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

    public boolean Actualizar_parada_maquina_registro(int irg, int ipm, int ctd, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_m_registro_parada_maquina`('" + irg + "','" + ipm + "','" + ctd + "','" + urg + "')");
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

    public boolean Actualizar_parada_maquina_registro_pmtt(int irg, int ipm, int hor, int ctd,  String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_a_actualizarParadaMaquina_hora`('" + irg + "','" + ipm + "','" + hor + "','" + ctd + "','" + urg + "')");
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

    public boolean Eliminar_parada_maquina_registro(int ipm) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpm_e_parada_maquina`('" + ipm + "')");
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

    public boolean Eliminar_parada_maquina_registro_v2(String ids) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("DELETE FROM registro_parada_maquina "
                    + "WHERE id_registro_parada_maquina IN (" + ids + ")");
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

    //Pendiente para realizar consulta por categorias en el modulo de categorías.
    public String Listar_fechas_OEE_paradas_maquina(String fin, String ffn) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_inicial = fin;
        String fecha_final = ffn;
        Date date_inicio = sdf.parse(fecha_inicial);
        Date date_fin = sdf.parse(fecha_final);
        String fechas = "";
        Long diferencia = (date_fin.getTime() - date_inicio.getTime()) / (3600 * 24 * 1000);
        for (int i = 0; i < diferencia + 1; i++) {
            if (i == 0) {
                fechas = fin;
            } else {
                cal.setTime(date_inicio);
                cal.add(Calendar.DAY_OF_YEAR, i);
                fechas = fechas + "_" + sdf.format(cal.getTime());
            }
        }
        return fechas;
    }
    // public List OEE_PNC(int iln, String fin, String ffn) {

    public List Paradas_maquina_agrupado() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT pm.nombre,c.nombre FROM parada_maquina pm INNER JOIN categoria c ON c.id_categoria = pm.id_categoria GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List OEE_paradas_maquina(int iln, String vlm, String frg, String npm, String nct, String cpd, String trn) {
        //public List OEE_PNC(int iln, String vlm, String frg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (iln > 0) {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.lote_producto LIKE CONCAT('%" + cpd + "%') AND r.id_linea = " + iln + " AND r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                    } else {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                    }
                }
            } else {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.lote_producto LIKE CONCAT('%" + cpd + "%') AND r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                    } else {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "' AND pm.nombre = '" + npm + "' AND c.nombre = '" + nct + "' " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre LIMIT 1");
                    }
                }
            }
            //Query q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "') GROUP BY pn.nombre ORDER BY c.nombre,pn.nombre ");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List OEE_paradas_maquina_agrupado(int iln, String vlm, String fin, String ffn, String trn, String cpd) {
        //public List OEE_PNC(int iln, String vlm, String frg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (iln > 0) {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.lote_producto LIKE CONCAT('%" + cpd + "%') AND r.id_linea = " + iln + " AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                } else if (vlm == null ? "0" != null : !vlm.equals("0")) {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                    } else {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                    }
                }
            } else {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.lote_producto LIKE CONCAT('%" + cpd + "%') AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                } else if (vlm == null ? "0" != null : !vlm.equals("0")) {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                    } else {
                        q = etm.createNativeQuery("SELECT pm.nombre,c.nombre,SUM(rpm.cantidad) FROM parada_maquina pm INNER JOIN registro_parada_maquina rpm ON pm.id_parada_maquina = rpm.id_parada_maquina INNER JOIN categoria c ON c.id_categoria = pm.id_categoria INNER JOIN registro r ON r.id_registro = rpm.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE p.volumen = '" + vlm + "' AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pm.nombre,c.nombre ORDER BY c.nombre,pm.nombre");
                    }
                }
            }
            //Query q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "') GROUP BY pn.nombre ORDER BY c.nombre,pn.nombre ");
            List consulta = q.getResultList();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (consulta.isEmpty()) {
                return null;
            } else {
                return consulta;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
