package Controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class FilterJpaController implements Serializable {

    public FilterJpaController() {
        emf = Persistence.createEntityManagerFactory("AppTIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //<editor-fold defaultstate="collapsed" desc="LIST">
    public List ConsultFilterBinnacle(String Date, String[] Data, String[] PersonActive, String[] PersonInactive) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Concat = "";
            String Person = "";
            String Query = "SELECT b.id_binnacle,b.date_initial,b.date_final,b.binnacle,b.shift,b.activity,b.state,b.id_user,CONCAT(u.`name`,' ',u.lastname),b.date_register "
                    + "FROM binnacle b "
                    + "INNER JOIN `user` u ON b.id_user = u.id_user ";
            String[] ArgDate = Date.split(" - ");
            Concat = "WHERE b.date_initial >= '" + ArgDate[0] + "' AND b.date_final <= '" + ArgDate[1] + "'";
            if (PersonActive.length > 0) {
                for (int i = 0; i < PersonActive.length; i++) {
                    if (i == 0) {
                        Person += PersonActive[i] + "";
                    } else {
                        Person += "," + PersonActive[i];
                    }
                }
            }
            if (PersonInactive.length > 0) {
                for (int i = 0; i < PersonInactive.length; i++) {
                    if (i == 0) {
                        if (PersonActive.length > 0) {
                            Person += "," + PersonInactive[i];
                        } else {
                            Person += PersonInactive[i] + "";
                        }
                    } else {
                        Person += "," + PersonInactive[i];
                    }
                }
            }
            if (PersonActive.length > 0 || PersonInactive.length > 0) {
                Concat += Concat = " AND b.id_user IN (" + Person + ")";
            }
            if (Data.length > 0) {
                for (int i = 0; i < Data.length; i++) {
                    if (i == 0) {
                        Concat += Concat = " AND (b.binnacle LIKE '%" + Data[0] + "%' " + ((Data.length > 1) ? " " : ")") + " ";
                    } else if (i == (Data.length - 1)) {
                        Concat += Concat = " OR b.binnacle LIKE '%" + Data[i] + "%')";
                    } else {
                        Concat += Concat = " OR b.binnacle LIKE '%" + Data[i] + "%'";
                    }

                }
            }

            Query += Concat;
            Query q = etm.createNativeQuery(Query);
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

    public List ConsultFilterPending(String Date, String[] Data, String[] PersonActive, String[] PersonInactive) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Concat = "";
            String Person = "";
            String Query = "SELECT p.id_pending,p.affair,p.managed,p.`description`,p.progress,p.solution,p.solution_date,p.solver,p.user_register,p.date_register\n"
                    + "FROM pending p ";
            String[] ArgDate = Date.split(" - ");
            Concat = "WHERE p.date_register >= '" + ArgDate[0] + "' AND p.solution_date <= '" + ArgDate[1] + "'";
            if (PersonActive.length > 0) {
                for (int i = 0; i < PersonActive.length; i++) {
                    if (i == 0) {
                        Person += PersonActive[i] + "";
                    } else {
                        Person += "," + PersonActive[i];
                    }
                }
            }
            if (PersonInactive.length > 0) {
                for (int i = 0; i < PersonInactive.length; i++) {
                    if (i == 0) {
                        if (PersonActive.length > 0) {
                            Person += "," + PersonInactive[i];
                        } else {
                            Person += PersonInactive[i] + "";
                        }
                    } else {
                        Person += "," + PersonInactive[i];
                    }
                }
            }
            if (PersonActive.length > 0 || PersonInactive.length > 0) {
                Concat += Concat = " AND p.user_register IN (" + Person + ")";
            }
            if (Data.length > 0) {
                for (int i = 0; i < Data.length; i++) {
                    if (i == 0) {
                        Concat += Concat = " AND (p.`description` LIKE '%" + Data[0] + "%' OR p.solution LIKE '%" + Data[0] + "%' " + ((Data.length > 1) ? " " : ")") + " ";
                    } else if (i == (Data.length - 1)) {
                        Concat += Concat = " OR p.`description` LIKE '%" + Data[0] + "%' OR p.solution LIKE '%" + Data[0] + "%')";
                    } else {
                        Concat += Concat = " OR p.`description` LIKE '%" + Data[0] + "%' OR p.solution LIKE '%" + Data[0] + "%'";
                    }

                }
            }

            Query += Concat;
            Query q = etm.createNativeQuery(Query);
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

    public List ConsultFilterTicket(String Date, String[] Data, String[] PersonActive, String[] PersonInactive) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Concat = "";
            String Person = "";
            String Query = "SELECT s.id_support,s.date,s.prority,s.id_area,a.initial,s.userReport, r.`name` ,s.request,s.supportType,t.`type`,s.supportDetail,s.supportPc,s.userSolution, CONCAT(u.`name`,' ',u.lastname),s.solution,s.dateSolution,s.pcStop,s.ProdStop "
                    + "FROM support s "
                    + "INNER JOIN area a ON s.id_area = a.id_area "
                    + "INNER JOIN reporter r ON s.userReport = r.id_reporter "
                    + "INNER JOIN type_support t ON s.supportType = t.id_type_support "
                    + "INNER JOIN user u ON s.userSolution = u.id_user ";
            String[] ArgDate = Date.split(" - ");
            Concat = " WHERE s.date >= '" + ArgDate[0] + "' AND s.dateSolution <= '" + ArgDate[1] + "' ";
            if (PersonActive.length > 0) {
                for (int i = 0; i < PersonActive.length; i++) {
                    if (i == 0) {
                        Person += PersonActive[i] + "";
                    } else {
                        Person += "," + PersonActive[i];
                    }
                }
            }
            if (PersonInactive.length > 0) {
                for (int i = 0; i < PersonInactive.length; i++) {
                    if (i == 0) {
                        if (PersonActive.length > 0) {
                            Person += "," + PersonInactive[i];
                        }else{
                            Person += PersonInactive[i] + "";
                        }
                    } else {
                        Person += "," + PersonInactive[i];
                    }
                }
            }
            if (PersonActive.length > 0 || PersonInactive.length > 0) {
                Concat += Concat = " AND userSolution IN (" + Person + ")";
            }
            if (Data.length > 0) {
                for (int i = 0; i < Data.length; i++) {
                    if (i == 0) {
                        Concat += Concat = " AND (s.request LIKE '%" + Data[0] + "%' OR s.solution LIKE '%" + Data[0] + "%' " + ((Data.length > 1) ? " " : ")") + " ";
                    } else if (i == (Data.length - 1)) {
                        Concat += Concat = " OR s.request LIKE '%" + Data[0] + "%' OR s.solution LIKE '%" + Data[0] + "%')";
                    } else {
                        Concat += Concat = " OR s.request LIKE '%" + Data[0] + "%' OR s.solution LIKE '%" + Data[0] + "%'";
                    }

                }
            }

            Query += Concat;
            Query q = etm.createNativeQuery(Query);
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
