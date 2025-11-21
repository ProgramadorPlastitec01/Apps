package Controladores;

import Metodos.Estadisticos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroFrecuenciaHoraJpaController {

    public RegistroFrecuenciaHoraJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Parametros_registro_frecuencia_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_parametros_registro`('" + irg + "')");
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

    public List Registros_lote(String lte, int ipd, int nmr, int iln, String cet, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_registros_lote`('" + lte + "','" + ipd + "','" + nmr + "','" + iln + "','" + cet + "','" + fin + "','" + ffn + "')");
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

    public List Registros_lote_resumido(String lte, int ipd, int nmr, int iln, String cet, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_registros_lote_resumido`('" + lte + "','" + ipd + "','" + nmr + "','" + iln + "','" + cet + "','" + fin + "','" + ffn + "')");
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

    public List Registros_lote(String lte, int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_parametros_tomas_registro_lote`('" + lte + "','" + irg + "')");
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

    public List Parametros_tomas_registro_frecuencia_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_c_parametro_tomas_registro`('" + irg + "')");
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

    public List Responsables_tomas_registro_frecuencia_hora(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_responsables_registro`('" + irg + "')");
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

    public boolean Registrar_frecuencia_hora(int irg, int ipr, String vlr, int fce, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro_frecuencia_hora SET toma" + fce + "='" + vlr + "',"
                    + "usuario_toma" + fce + " = CONCAT((DATE_FORMAT(NOW(), '%H:%i:%p')),'/" + urg + "') "
                    + "WHERE id_registro ='" + irg + "' AND id_parametro ='" + ipr + "'");
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

    public boolean Registrar_frecuencia_hora(int irg, int ipr) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_r_frecuencia_hora_registro`('" + irg + "','" + ipr + "')");
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

    public List Registros_lote(int nmr, int ipd, int irg, String prm, String lte, String fin, String ffn) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rfh_t_registros_lote_datos`('" + nmr + "','" + ipd + "','" + irg + "','" + prm + "','" + lte + "','" + fin + "','" + ffn + "')");
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

    public boolean Eliminar_frecuencia_hora_registro(int irg, int fce) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE registro_frecuencia_hora SET toma" + fce + " = NULL , usuario_toma" + fce + " = NULL WHERE id_registro = " + irg + " ");
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

    //DATOS ESTADISTICOS R-GC-017
    public List Datos_estadisticos_frecuencia_hora(String prm, String irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro,p.comparador,rf.toma1,rf.toma2,rf.toma3,rf.toma4,rf.toma5,rf.toma6,rf.toma7,rf.toma8,rf.toma9,rf.toma10,p.nombre,t.tipo_registro FROM registro_frecuencia_hora rf INNER JOIN registro r ON rf.id_registro = r.id_registro INNER JOIN parametro p ON rf.id_parametro = p.id_parametro INNER JOIN tipo_linea t ON p.id_tipo_linea = t.id_tipo_linea WHERE (p.comparador like '" + prm + "') and (" + irg + ")");
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

    public List Datos_estadisticos_frecuencia_hora_avt(String prm, String irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT r.id_registro,p.nombre,rf.toma1,rf.toma2,rf.toma3,rf.toma4,rf.toma5,rf.toma6,rf.toma7,rf.toma8,rf.toma9,rf.toma10 FROM registro_frecuencia_hora rf INNER JOIN registro r ON rf.id_registro = r.id_registro INNER JOIN parametro p ON rf.id_parametro = p.id_parametro WHERE (p.nombre like '%" + prm + "%') and (r.id_registro = " + irg + ")");
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

    public String Calcular_CP_CPK_estadisticos(int ipd, List lst_controles, String prm) {
        try {
            //<editor-fold defaultstate="collapsed" desc="PARAMETROS Y VARIABLES">
            String[] arg_parametros = {"Pared doble_3_4_5_0",
                "Pared sencilla_6_7_8_0",
                "Soldadura boca_9_10_11_0",
                "Soldadura cola_12_13_14_0",
                "Longitud total_15_16_17_0",
                "Ducto derecho_18_19_20_0",
                "Ducto central_50_51_52_0",
                "Ducto izquierdo_21_22_23_0",
                "Dia. Int. ducto derecho_24_25_26_1",
                "Dia. Int. ducto central_53_54_55_1",
                "Dia. Int. ducto izquierdo_30_31_32_1",
                "Dia. Ext. ducto derecho_27_28_29_1",
                "Dia. Ext. ducto central_56_57_58_1",
                "Dia. Ext. ducto izquierdo_33_34_35_1",
                "Ancho de manga_36_37_38_0",
                "Ancho de ventana_59_60_61_0",
                "Espesor ducto bicapa Ext_62_63_64_0",
                "Espesor ducto bicapa Int_65_66_67_0",
                "Pared sencilla estriada_69_70_71_0",
                "Distancia X4_72_73_74_0",
                "Distancia X5_75_76_77_0"};
            double media = 0;
            double varianza = 0;
            double desviacion = 0;
            double cp = 0;
            double cpk_menor = 0;
            double cpk_mayor = 0;
            double cpk = 0;
            double max_ft = 0;
            double min_ft = 0;
            double max = 0;
            double min = 0;
            String resultados = "";
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            Metodos.Estadisticos mtdetd = new Estadisticos();
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ARRAY Y FICHA TECNICA">
            List lst_ficha_tecnica = jpacftn.Traer_ficha_producto(ipd);
            Object[] obj_ficha_tecnica = (Object[]) lst_ficha_tecnica.get(0);
            for (int i = 0; i < arg_parametros.length; i++) {
                if (arg_parametros[i].contains(prm + "_")) {
                    String[] arg_datos = arg_parametros[i].split("_");
                    max_ft = Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[1])].toString()) + Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[2])].toString());
                    min_ft = Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[1])].toString()) - Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[3])].toString());
                    break;
                }
            }
            //FIN TRAER FICHA TECNICA 9 10 11
            String[] controles_registro = mtdetd.Extrar_datos(lst_controles);
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MINIMO">
            for (int i = 0; i < controles_registro.length; i++) {
                if (i == 0) {
                    min = Double.parseDouble(controles_registro[i]);
                }
                if (Double.parseDouble(controles_registro[i]) < min) {
                    min = Double.parseDouble(controles_registro[i]);
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAXIMO">
            for (int i = 0; i < controles_registro.length; i++) {
                if (Double.parseDouble(controles_registro[i]) > max) {
                    max = Double.parseDouble(controles_registro[i]);
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PROMEDIO / MEDIA">
            for (int i = 0; i < controles_registro.length; i++) {
                media = media + Double.parseDouble(controles_registro[i]);
            }
            media = media / controles_registro.length;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="DESVIACION ESTANDAR">
            for (int i = 0; i < controles_registro.length; i++) {
                double rango;
                rango = Math.pow(Double.parseDouble(controles_registro[i]) - media, 2);
                varianza = varianza + rango;
            }
            varianza = varianza / controles_registro.length;
            desviacion = Math.sqrt(varianza);
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CALCULOS CP, CPK">
            if (desviacion > 0) {
                cp = (max_ft - min_ft) / (6 * desviacion);
                cpk_menor = (media - min_ft) / (3 * desviacion);
                cpk_mayor = (max_ft - media) / (3 * desviacion);
                if (cpk_menor < cpk_mayor) {
                    cpk = cpk_menor;
                } else {
                    cpk = cpk_mayor;
                }
                //<editor-fold defaultstate="collapsed" desc="REDONDEO">
                long mult = (long) Math.pow(10, 3);
                min = (Math.round(min * mult)) / (double) mult;
                max = (Math.round(max * mult)) / (double) mult;
                min_ft = (Math.round(min_ft * mult)) / (double) mult;
                max_ft = (Math.round(max_ft * mult)) / (double) mult;
                media = (Math.round(media * mult)) / (double) mult;
                desviacion = (Math.round(desviacion * mult)) / (double) mult;
                cp = (Math.round(cp * mult)) / (double) mult;
                cpk_menor = (Math.round(cpk_menor * mult)) / (double) mult;
                cpk_mayor = (Math.round(cpk_mayor * mult)) / (double) mult;
                cpk = (Math.round(cpk * mult)) / (double) mult;
//</editor-fold>
                resultados = prm + "-" + min_ft + "-" + max_ft + "-" + media + "-" + desviacion + "-" + cp + "-" + cpk_menor + "-" + cpk_mayor + "-" + cpk;
            } else {
                resultados = prm + "-" + min_ft + "-" + max_ft + "-" + media + "-" + 0 + "-" + 0 + "-" + 0 + "-" + 0 + "-" + 0;
            }
//</editor-fold>
            return resultados;
        } catch (Exception ex) {
            return "";
        }
    }
    public String Calcular_CP_CPK_estadisticos_ft(int ift, List lst_controles, String prm) {
        try {
            //<editor-fold defaultstate="collapsed" desc="PARAMETROS Y VARIABLES">
            String[] arg_parametros = {"Pared doble_3_4_5_0",
                "Pared sencilla_6_7_8_0",
                "Soldadura boca_9_10_11_0",
                "Soldadura cola_12_13_14_0",
                "Longitud total_15_16_17_0",
                "Ducto derecho_18_19_20_0",
                "Ducto central_50_51_52_0",
                "Ducto izquierdo_21_22_23_0",
                "Dia. Int. ducto derecho_24_25_26_1",
                "Dia. Int. ducto central_53_54_55_1",
                "Dia. Int. ducto izquierdo_30_31_32_1",
                "Dia. Ext. ducto derecho_27_28_29_1",
                "Dia. Ext. ducto central_56_57_58_1",
                "Dia. Ext. ducto izquierdo_33_34_35_1",
                "Ancho de manga_36_37_38_0",
                "Ancho de ventana_59_60_61_0",
                "Espesor ducto bicapa Ext_62_63_64_0",
                "Espesor ducto bicapa Int_65_66_67_0",
                "Pared sencilla estriada_69_70_71_0",
                "Distancia X4_72_73_74_0",
                "Distancia X5_75_76_77_0"};
            double media = 0;
            double varianza = 0;
            double desviacion = 0;
            double cp = 0;
            double cpk_menor = 0;
            double cpk_mayor = 0;
            double cpk = 0;
            double max_ft = 0;
            double min_ft = 0;
            double max = 0;
            double min = 0;
            String resultados = "";
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            Metodos.Estadisticos mtdetd = new Estadisticos();
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ARRAY Y FICHA TECNICA">
            List lst_ficha_tecnica = jpacftn.Traer_ficha_id(ift);
            Object[] obj_ficha_tecnica = (Object[]) lst_ficha_tecnica.get(0);
            for (int i = 0; i < arg_parametros.length; i++) {
                if (arg_parametros[i].contains(prm + "_")) {
                    String[] arg_datos = arg_parametros[i].split("_");
                    max_ft = Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[1])].toString()) + Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[2])].toString());
                    min_ft = Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[1])].toString()) - Double.parseDouble(obj_ficha_tecnica[Integer.parseInt(arg_datos[3])].toString());
                    break;
                }
            }
            //FIN TRAER FICHA TECNICA 9 10 11
            String[] controles_registro = mtdetd.Extrar_datos(lst_controles);
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MINIMO">
            for (int i = 0; i < controles_registro.length; i++) {
                if (i == 0) {
                    min = Double.parseDouble(controles_registro[i]);
                }
                if (Double.parseDouble(controles_registro[i]) < min) {
                    min = Double.parseDouble(controles_registro[i]);
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAXIMO">
            for (int i = 0; i < controles_registro.length; i++) {
                if (Double.parseDouble(controles_registro[i]) > max) {
                    max = Double.parseDouble(controles_registro[i]);
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PROMEDIO / MEDIA">
            for (int i = 0; i < controles_registro.length; i++) {
                media = media + Double.parseDouble(controles_registro[i]);
            }
            media = media / controles_registro.length;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="DESVIACION ESTANDAR">
            for (int i = 0; i < controles_registro.length; i++) {
                double rango;
                rango = Math.pow(Double.parseDouble(controles_registro[i]) - media, 2);
                varianza = varianza + rango;
            }
            varianza = varianza / controles_registro.length;
            desviacion = Math.sqrt(varianza);
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CALCULOS CP, CPK">
            if (desviacion > 0) {
                cp = (max_ft - min_ft) / (6 * desviacion);
                cpk_menor = (media - min_ft) / (3 * desviacion);
                cpk_mayor = (max_ft - media) / (3 * desviacion);
                if (cpk_menor < cpk_mayor) {
                    cpk = cpk_menor;
                } else {
                    cpk = cpk_mayor;
                }
                //<editor-fold defaultstate="collapsed" desc="REDONDEO">
                long mult = (long) Math.pow(10, 3);
                min = (Math.round(min * mult)) / (double) mult;
                max = (Math.round(max * mult)) / (double) mult;
                min_ft = (Math.round(min_ft * mult)) / (double) mult;
                max_ft = (Math.round(max_ft * mult)) / (double) mult;
                media = (Math.round(media * mult)) / (double) mult;
                desviacion = (Math.round(desviacion * mult)) / (double) mult;
                cp = (Math.round(cp * mult)) / (double) mult;
                cpk_menor = (Math.round(cpk_menor * mult)) / (double) mult;
                cpk_mayor = (Math.round(cpk_mayor * mult)) / (double) mult;
                cpk = (Math.round(cpk * mult)) / (double) mult;
//</editor-fold>
                resultados = prm + "-" + min_ft + "-" + max_ft + "-" + media + "-" + desviacion + "-" + cp + "-" + cpk_menor + "-" + cpk_mayor + "-" + cpk;
            } else {
                resultados = prm + "-" + min_ft + "-" + max_ft + "-" + media + "-" + 0 + "-" + 0 + "-" + 0 + "-" + 0 + "-" + 0;
            }
//</editor-fold>
            return resultados;
        } catch (Exception ex) {
            return "";
        }
    }

}
