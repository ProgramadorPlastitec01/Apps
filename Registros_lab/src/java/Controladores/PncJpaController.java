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

public class PncJpaController {

    public PncJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List PNC() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pnc_c_pnc`()");
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
//Pendiente para realizar consulta por categorias en el modulo de categorías.
//    public List PNC(int ict) {
//        EntityManager etm = getEntityManager();
//        etm.getTransaction().begin();
//        try {
//            Query q = etm.createNativeQuery("CALL `sp_pnc_c_pnc_categoria`('" + ict + "')");
//            List consulta = q.getResultList();
//            etm.getTransaction().commit();
//            etm.clear();
//            etm.close();
//            if (consulta.isEmpty()) {
//                return null;
//            } else {
//                return consulta;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }

    public boolean Registrar_pnc(String nbe, int ict, int itl, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pnc_r_pnc`('" + nbe + "','" + ict + "','" + itl + "','" + urg + "')");
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

    public boolean Registrar_pnc_registro(int irg, int ipn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpn_r_pnc`('" + irg + "','" + ipn + "')");
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

    public boolean Eliminar_pnc_registro(int irp) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpn_e_pnc`('" + irp + "')");
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

    public List Traer_pnc_registro(int irg, int ipn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpn_t_pnc_registro`('" + irg + "','" + ipn + "')");
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

    public List Pnc_registro(int irg, int ict) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rpn_c_pnc_registro`('" + irg + "','" + ict + "')");
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

    public List PNC_categoria_registro(int ict, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_pnc_c_pnc_categoria_registro`('" + ict + "','" + irg + "')");
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

    public boolean Registrar_toma_descripcion_pnc(int irg, int irp, String vlr, int fce, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE `registro_pnc` SET `toma" + fce + "`='" + vlr + "',`usuario_toma" + fce + "`='" + urg + "' WHERE `id_registro`='" + irg + "' AND `id_registro_pnc`='" + irp + "'");
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
    public String Listar_fechas_OEE_PNC(String fin, String ffn) throws ParseException {
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

    public List PNC_agrupado() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT pn.nombre,c.nombre FROM pnc pn INNER JOIN categoria c ON c.id_categoria = pn.id_categoria GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
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

    public List OEE_PNC(int iln, String vlm, String frg, String npn, String nct, String cpd, String trn) {
        //public List OEE_PNC(int iln, String vlm, String frg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (iln > 0) {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE " + ((cpd.equals("") ? "" : "r.lote_producto LIKE CONCAT('%" + cpd + "%') AND")) + " r.id_linea = " + iln + " AND r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (r.id_linea = " + iln + " AND r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
                    } else {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
                    }
                }
            } else {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE " + ((cpd.equals("") ? "" : "r.lote_producto LIKE CONCAT('%" + cpd + "%') AND")) + " r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
                    } else {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE (p.volumen = '" + vlm + "' AND r.fecha_turno = '" + frg + "') AND (pn.nombre = '" + npn + "' AND c.nombre = '" + nct + "') " + (trn.equals("0") ? "" : "AND r.turno = '" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre LIMIT 1");
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

    public List OEE_PNC_agrupado(int iln, String vlm, String fin, String ffn, String trn, String cpd) {
        //public List OEE_PNC(int iln, String vlm, String frg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = null;
            if (iln > 0) {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE " + ((cpd.equals("") ? "" : "r.lote_producto LIKE CONCAT('%" + cpd + "%') AND")) + " r.id_linea = " + iln + " AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
                    } else {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.id_linea = " + iln + " AND p.volumen = '" + vlm + "' AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
                    }
                }
            } else {
                if (cpd == null ? "0" != null : !cpd.equals("0")) {
                    q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE " + ((cpd.equals("") ? "" : "r.lote_producto LIKE CONCAT('%" + cpd + "%') AND")) + " r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
                } else {
                    if (vlm.equals("TODOS")) {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
                    } else {
                        q = etm.createNativeQuery("SELECT pn.nombre,c.nombre,(SUM(rpn.toma1)+SUM(rpn.toma2)+SUM(rpn.toma3)+SUM(rpn.toma4)+SUM(rpn.toma5)+SUM(rpn.toma6)+SUM(rpn.toma7)+SUM(rpn.toma8)) FROM pnc pn INNER JOIN registro_pnc rpn ON pn.id_pnc = rpn.id_pnc INNER JOIN categoria c ON c.id_categoria = pn.id_categoria INNER JOIN registro r ON r.id_registro = rpn.id_registro INNER JOIN producto p ON r.id_producto = p.id_producto WHERE p.volumen = '" + vlm + "' AND r.fecha_turno BETWEEN '" + fin + "' AND '" + ffn + "' " + (trn.equals("0") ? "" : "AND r.turno='" + trn + "'") + " GROUP BY pn.nombre,c.nombre ORDER BY c.nombre,pn.nombre");
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
