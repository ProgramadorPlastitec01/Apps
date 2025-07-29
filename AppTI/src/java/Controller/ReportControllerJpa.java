package Controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ReportControllerJpa {

    public ReportControllerJpa() {
        emf = Persistence.createEntityManagerFactory("AppTIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List ConsultReportPending(int IdPending, String Affair, int Priority, String Charge, String Person,
            String DateR, String DateF, int ProgressInitial, int ProgressFinal, String Description, String Solution, int Count) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String Qrt = "";
            Qrt = "SELECT p.id_pending,p.affair,CASE p.priority WHEN 1 THEN 'ALTA'  WHEN 2 THEN 'MEDIA' ELSE 'BAJA' END AS 'priority',"
                    + "				p.managed,p.`description`,SUBSTRING(RemoveHTML(p.`description`),1,200) AS 'Desc QHTML',p.progress, "
                    + "				p.solution,p.solution_date,p.solver, "
                    + "                         p.user_register,p.date_register, LENGTH(p.solution), SUBSTRING(RemoveHTML(p.solution),1,200), LENGTH(p.`description`)  "
                    + "FROM pending p ";
            if (IdPending > 0) {
                if (Count == 0) {
                    Qrt += "WHERE p.id_pending = " + IdPending + "";
                    Count = Count + 1;
                } else {
                    Qrt += " AND p.id_pending = " + IdPending + "";
                }
            }
            if (!Affair.equals("")) {
                if (Count == 0) {
                    Qrt += "WHERE p.affair LIKE '%" + Affair + "%'";
                    Count = Count + 1;
                } else {
                    Qrt += " AND p.affair LIKE '%" + Affair + "%'";
                }
            }
            if (Priority > 0) {
                if (Count == 0) {
                    if (Priority == 3) {
                        Qrt += "WHERE p.priority = 0";
                    } else {
                        Qrt += "WHERE p.priority = " + Priority + "";
                    }
                    Count = Count + 1;
                } else {
                    if (Priority == 3) {
                        Qrt += " AND p.priority = 0";
                    } else {
                        Qrt += " AND p.priority = " + Priority + "";
                    }
                }
            }
            if (!Charge.equals("")) {
                if (Count == 0) {
                    Qrt += "WHERE p.managed = '" + Charge + "'";
                    Count = Count + 1;
                } else {
                    Qrt += " AND p.managed = '" + Charge + "'";
                }
            }
            if (!Person.equals("")) {
                if (Count == 0) {
                    Qrt += "WHERE p.managed = '" + Person + "' AND p.solver = '" + Person + "'";
                    Count = Count + 1;
                } else {
                    Qrt += " AND p.managed = '" + Person + "' AND p.solver = '" + Person + "'";
                }
            }
            if (!DateR.equals("")) {
                DateR = DateR.replace(" - ", "///");
                String[] DateFormatR = DateR.split("///");
                String DateInitialR = DateFormatR[0].trim();
                String DateFinalR = DateFormatR[1].trim();
                if (Count == 0) {
                    Qrt += "WHERE p.date_register between '" + DateInitialR + "' AND '" + DateFinalR + "'";
                    Count = Count + 1;
                } else {
                    Qrt += " AND (p.date_register between '" + DateInitialR + "' AND '" + DateFinalR + "')";
                }
            }
            if (!DateF.equals("")) {
                DateF = DateF.replace(" - ", "///");
                String[] DateFormatF = DateF.split("///");
                String DateInitialF = DateFormatF[0].trim();
                String DateFinalF = DateFormatF[1].trim();
                if (Count == 0) {
                    Qrt += "WHERE p.date_register between '" + DateInitialF + "' AND '" + DateFinalF + "'";
                    Count = Count + 1;
                } else {
                    Qrt += " (AND p.date_register between '" + DateInitialF + "' AND '" + DateFinalF + "')";
                }
            }
            if (ProgressInitial >= 0 && ProgressFinal > 0) {
                if (Count == 0) {
                    Qrt += "WHERE p.progress >= " + ProgressInitial + " AND  p.progress <= " + ProgressFinal;
                    Count = Count + 1;
                } else {
                    Qrt += " AND (p.progress >= " + ProgressInitial + " AND p.progress <= " + ProgressFinal + ")";
                }
            }
            if (!Description.equals("")) {
                if (Count == 0) {
                    Qrt += "WHERE p.`description` LIKE '%" + Description + "%'";
                    Count = Count + 1;
                } else {
                    Qrt += " AND p.`description` LIKE '%" + Description + "%'";
                }
            }
            if (!Solution.equals("")) {
                if (Count == 0) {
                    Qrt += "WHERE p.`solution` LIKE '%" + Solution + "%'";
                } else {
                    Qrt += " AND p.`solution` LIKE '%" + Solution + "%'";
                }
            }
            Qrt += " ORDER BY p.id_pending DESC ";
            Query q = etm.createNativeQuery(Qrt);
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
}
