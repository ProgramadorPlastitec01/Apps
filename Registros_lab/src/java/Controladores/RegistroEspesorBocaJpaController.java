package Controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RegistroEspesorBocaJpaController {

    public RegistroEspesorBocaJpaController() {
        emf = Persistence.createEntityManagerFactory("RegistrosLaboratorioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List Consultar_registro_espesores_bocas(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reb_c_soldadura_registro`('" + irg + "')");
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

    public List Traer_registro_espesores_bocas(int irg, int fce, int sfc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reb_t_soldadura_registro`('" + irg + "','" + fce + "','" + sfc + "')");
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

    public boolean Registro_espesores_bocas(int irg, int fce, int sfc, double tm1, double tm2, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reb_r_soldadura_registro`('" + irg + "','" + fce + "','" + sfc + "','" + tm1 + "','" + tm2 + "','" + urg + "');");
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

    public boolean Modificar_espesores_bocas(int irg, int fce, int sfc, double tm1, double tm2, String urg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reb_m_soldadura_registro`('" + irg + "','" + fce + "','" + sfc + "','" + tm1 + "','" + tm2 + "','" + urg + "');");
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

    public List Promedio_soldadura_espesores_bocas(int irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_reb_c_promedio_soldadura`('" + irg + "')");
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

    public List Traer_registro_espesores_bocas(List lst_registros) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query_registro = "";
            String ajustador_query_registro = "";
            for (int i = 0; i < lst_registros.size(); i++) {
                Object[] obj_registros = (Object[]) lst_registros.get(i);
                if (i == 0) {
                    ajustador_query_registro = "WHERE b.id_registro = " + obj_registros[0];
                    query_registro = ajustador_query_registro;
                } else {
                    ajustador_query_registro = ajustador_query_registro + " OR b.id_registro = " + obj_registros[0];
                    query_registro = ajustador_query_registro;
                }
            }
            Query q = etm.createNativeQuery("SELECT b.id_registro_espesor_boca,b.id_registro,b.frecuencia,b.sub_frecuencia,b.toma1,b.toma2,b.usuario_registro FROM registro_espesor_boca b " + query_registro + " ORDER BY b.id_registro,b.frecuencia,b.sub_frecuencia");
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

    public String Calcular_CP_CPK_espesores_id_registro(int ipd, List lst_espesores_boca, int rpa) {
        try {
            //TRAER FICHA TECNICA
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            List lst_ficha_tecnica = jpacftn.Traer_ficha_producto(ipd);
            Object[] obj_ficha_tecnica = (Object[]) lst_ficha_tecnica.get(0);
            double max = 0;
            double min = 0;
            if (rpa == 1) {
                max = Double.parseDouble(obj_ficha_tecnica[44].toString()) + Double.parseDouble(obj_ficha_tecnica[45].toString());
                min = Double.parseDouble(obj_ficha_tecnica[44].toString()) - Double.parseDouble(obj_ficha_tecnica[46].toString());
            } else {
                max = Double.parseDouble(obj_ficha_tecnica[9].toString()) + Double.parseDouble(obj_ficha_tecnica[10].toString());
                min = Double.parseDouble(obj_ficha_tecnica[9].toString()) - Double.parseDouble(obj_ficha_tecnica[11].toString());
            }
            //FIN TRAER FICHA TECNICA 9 10 11
            String[] espesores_boca = new String[(lst_espesores_boca.size() * 2)];
            int cont = 0;
            for (int i = 0; i < lst_espesores_boca.size(); i++) {
                Object[] obj_espesores_bocas = (Object[]) lst_espesores_boca.get(i);
                espesores_boca[i] = obj_espesores_bocas[4] + "";
                cont++;
            }
            for (int j = 0; j < lst_espesores_boca.size(); j++) {
                Object[] obj_espesores_bocas = (Object[]) lst_espesores_boca.get(j);
                espesores_boca[cont++] = obj_espesores_bocas[5] + "";
            }
            double media = 0;
            double varianza = 0;
            double desviacion = 0;
            double cp = 0;
            double cpk_menor = 0;
            double cpk_mayor = 0;
            double cpk = 0;
            String resultados = "";
//            //VALOR MINIMO
//            for (int i = 0; i < espesores_boca.length; i++) {
//                if (i == 0) {
//                    min = Double.parseDouble(espesores_boca[i]);
//                }
//                if (Double.parseDouble(espesores_boca[i]) < min) {
//                    min = Double.parseDouble(espesores_boca[i]);
//                }
//            }
//            //VALOR MAXIMO
//            for (int i = 0; i < espesores_boca.length; i++) {
//                if (Double.parseDouble(espesores_boca[i]) > max) {
//                    max = Double.parseDouble(espesores_boca[i]);
//                }
//            }
            //PROMEDIO / MEDIA
            for (int i = 0; i < espesores_boca.length; i++) {
                media = media + Double.parseDouble(espesores_boca[i]);
            }
            media = media / espesores_boca.length;
            //DESVIACIÓN ESTANDAR Y VARIANZA
            for (int i = 0; i < espesores_boca.length; i++) {
                double rango;
                rango = Math.pow(Double.parseDouble(espesores_boca[i]) - media, 2);
                varianza = varianza + rango;
            }
            varianza = varianza / espesores_boca.length;
            desviacion = Math.sqrt(varianza);
            //REDONDEOS
            long mult = (long) Math.pow(10, 3);
//            min = (Math.round(min * mult)) / (double) mult;
//            max = (Math.round(max * mult)) / (double) mult;
            media = (Math.round(media * mult)) / (double) mult;
            desviacion = (Math.round(desviacion * mult)) / (double) mult;
            //CALCULO DE CP Y CPK
            cp = (max - min) / (6 * desviacion);
            cpk_menor = (media - min) / (3 * desviacion);
            cpk_mayor = (max - media) / (3 * desviacion);
            if (cpk_menor < cpk_mayor) {
                cpk = cpk_menor;
            } else {
                cpk = cpk_mayor;
            }
            cp = (Math.round(cp * mult)) / (double) mult;
            cpk = (Math.round(cpk * mult)) / (double) mult;
            //resultados = "CP : " + cp + " CPK : " + cpk;
            //resultados = "CP : " + cp + "/CPK : " + cpk + "/MIN : " + min + "/MAX : " + max + "/MEDIA : " + media + "/DESV : " + desviacion;
            resultados = cp + "-" + cpk + "-" + min + "-" + max + "-" + media + "-" + desviacion;
            return resultados;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean Eliminar_soldadura_boca_registro(int irg, int fce, int sfc) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("DELETE FROM registro_espesor_boca WHERE id_registro = " + irg + " AND frecuencia = " + fce + " AND sub_frecuencia = " + sfc + "");
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

    public List Datos_estadisticos_bocas(String irg) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT rb.id_registro_espesor_boca,rb.id_registro,rb.toma1,rb.toma2 FROM registro_espesor_boca rb INNER JOIN registro r ON rb.id_registro = r.id_registro WHERE (" + irg + ")");
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
