package Method;

import Controller.SettingControllerJpa;
import java.util.List;

public class FilterInfoReport {

    public static String FilterInfoReport(int IdPC, String Antivirus, String Internal_Mail, String External_Mail, String Bill, String Bill_Date, String Gmail,
            String Warranty, String Warranty_Date, String Internet, String Login_Plastitec, String Licence, String Licence_date, String MAC,
            String IP, String Supplier, String Network_Point, String RED, String Skype, String Type_State,
            String VLAN, String VPN, String Office_Version, String WIN_Version, String Responsible, String Area, String Type) {
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        List lst_setting = SettingJpa.ConsultSettingCategorie("ConsultComputInfo");
        String Qrt = "";
        int Count = 0;
        if (lst_setting != null) {
            Object[] Obj_setting = (Object[]) lst_setting.get(0);
            Qrt = Obj_setting[2].toString();
        } else {
            Qrt = "SELECT c.id_pc_dtl,c.id_pc,cm.name,CASE cm.state \n"
                    + "		WHEN 1 THEN 'BUENO'\n"
                    + "		WHEN 2 THEN 'EN REVISIÓN'\n"
                    + "		ELSE 'DE BAJA' END AS Val_S,cm.responsible,a.`initial`,c.login_plastitec,c.ip,c.mac,c.red,c.vlan,c.win_installed,\n"
                    + "	c.office_installed,c.antivirus,c.internet,c.vpn,c.skype,c.gmail,c.internal_mail,c.external_mail,\n"
                    + "	c.bill,c.bill_date,c.licence,c.licence_end_date,c.supplier,c.warranty,c.wanrraty_date,cm.type_pc,\n"
                    + "	cm.`process`,c.network_point,c.`description`,c.user_register,c.date_register,c.user_modified,c.date_modified\n"
                    + "FROM computer_dtl c\n"
                    + "LEFT JOIN computer cm ON c.id_pc = cm.id_pc\n"
                    + "INNER JOIN area a ON cm.id_area = a.id_area ";
        }
        if (IdPC > 0) {
            if (Count == 0) {
                Qrt += "WHERE c.id_pc = " + IdPC + "";
                Count = Count + 1;
            } else {
                Qrt += " AND c.id_pc = " + IdPC + "";
            }
        }
        if (!Antivirus.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.antivirus LIKE '%" + Antivirus + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.antivirus LIKE '%" + Antivirus + "%'";
            }
        }
        if (!Internal_Mail.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.internal_mail LIKE '%" + Internal_Mail + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.internal_mail LIKE '%" + Internal_Mail + "%'";
            }
        }
        if (!External_Mail.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.external_mail LIKE '%" + External_Mail + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.external_mail LIKE '%" + External_Mail + "%'";
            }
        }
        if (!Bill.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.bill LIKE '%" + Bill + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.bill LIKE '%" + Bill + "%'";
            }
        }
        if (!Bill_Date.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.bill_date LIKE '%" + Bill_Date + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.bill_date LIKE '%" + Bill_Date + "%'";
            }
        }
        if (!Gmail.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.gmail LIKE '%" + Gmail + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.gmail LIKE '%" + Gmail + "%'";
            }
        }
        if (!Warranty.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.warranty LIKE '%" + Warranty + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.warranty LIKE '%" + Warranty + "%'";
            }
        }
        if (!Warranty_Date.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.warranty_date LIKE '%" + Warranty_Date + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.warranty_date LIKE '%" + Warranty + "%'";
            }
        }
        if (!Internet.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.internet LIKE '%" + Internet + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.internet LIKE '%" + Internet + "%'";
            }
        }
        if (!Login_Plastitec.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.login_plastitec LIKE '%" + Login_Plastitec + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.login_plastitec LIKE '%" + Login_Plastitec + "%'";
            }
        }
        if (!Licence.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.licence LIKE '%" + Licence + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.licence LIKE '%" + Licence + "%'";
            }
        }
        if (!Licence_date.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.licence_end_date LIKE '%" + Licence_date + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.licence_end_date LIKE '%" + Licence_date + "%'";
            }
        }
        if (!MAC.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.mac LIKE '%" + MAC + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.mac LIKE '%" + MAC + "%'";
            }
        }
        if (!IP.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.ip LIKE '%" + IP + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.ip LIKE '%" + IP + "%'";
            }
        }
        if (!Supplier.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.supplier LIKE '%" + Supplier + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.supplier LIKE '%" + Supplier + "%'";
            }
        }
        if (!Network_Point.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.network_point LIKE '%" + Network_Point + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.network_point LIKE '%" + Network_Point + "%'";
            }
        }
        if (!RED.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.red LIKE '%" + RED + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.red LIKE '%" + RED + "%'";
            }
        }
        if (!Skype.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.skype LIKE '%" + Skype + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.skype LIKE '%" + RED + "%'";
            }
        }
        if (!Type_State.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE CASE cm.state \n"
                        + "		WHEN 1 THEN 'BUENO'\n"
                        + "		WHEN 2 THEN 'EN REVISIÓN'\n"
                        + "		ELSE 'DE BAJA' END  LIKE '%" + Type_State + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND CASE cm.state \n"
                        + "		WHEN 1 THEN 'BUENO'\n"
                        + "		WHEN 2 THEN 'EN REVISIÓN'\n"
                        + "		ELSE 'DE BAJA' END  LIKE '%" + Type_State + "%'";
            }
        }
        if (!VLAN.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.vlan LIKE '%" + VLAN + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.vlan LIKE '%" + VLAN + "%'";
            }
        }
        if (!VPN.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.vpn LIKE '%" + VPN + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.vpn LIKE '%" + VPN + "%'";
            }
        }
        if (!Office_Version.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.office_installed LIKE '%" + Office_Version + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.office_installed LIKE '%" + Office_Version + "%'";
            }
        }
        if (!WIN_Version.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE c.win_installed LIKE '%" + WIN_Version + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND c.win_installed LIKE '%" + WIN_Version + "%'";
            }
        }
        if (!Responsible.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE cm.responsible LIKE '%" + Responsible + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND cm.responsible LIKE '%" + Responsible + "%'";
            }
        }
        if (!Area.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE a.`initial` LIKE '" + Area + "'";
                Count = Count + 1;
            } else {
                Qrt += " AND a.`initial` LIKE '" + Area + "'";
            }
        }
        if (!Type.equals("")) {
            if (Count == 0) {
                Qrt += "WHERE cm.type_pc LIKE '%" + Type + "%'";
                Count = Count + 1;
            } else {
                Qrt += " AND cm.type_pc LIKE '%" + Type + "%'";
            }
        }
        return Qrt;
    }

}
