package Controladoras;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("REDEACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List login(String usa, String pas) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_sesion`('" + usa + "', '" + pas + "')");
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
    public List consultaAreas() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_ara_c_areas`()");
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
    public List consultaUsuarioDoc(String documento, String codigo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL sirh.sp_signature_documento_codigo('" + documento + "','" + codigo + "')");
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

    public boolean registroUsuario(String nombre, String apellido, String documento, String codigo, String usuario, String firma, int id_rol, String correo, String usuario_registro) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_r_usuario`('" + nombre + "','" + apellido + "','" + documento + "','" + codigo + "','" + usuario + "','" + firma + "','" + id_rol + "','" + correo + "','" + usuario_registro + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarUsuario(int id_usuario, String nombre, String apellido, String documento, String codigo, String usuario, String firma, int id_rol, String correo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_m_usuario`('" + id_usuario + "','" + nombre + "','" + apellido + "','" + documento + "','" + codigo + "','" + usuario + "','" + firma + "','" + id_rol + "','" + correo + "')");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarEstadoUsuario(int id_usuario, int estado) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update usuario set estado=" + estado + " where id_usuario=" + id_usuario + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarPassword(int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("UPDATE usuario u SET u.PASSWORD = YEAR(CURDATE()) WHERE u.id_usuario =" + id_usuario + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarPass(int id_usuario, String contrasena) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("update usuario set password= '" + contrasena + "' where id_usuario=" + id_usuario + "");
            int resultado = q.executeUpdate();
            etm.getTransaction().commit();
            etm.clear();
            etm.close();
            if (resultado == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List iniciarFirma(String fma) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_firma`('" + fma + "')");
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

    public boolean establecerTecnicoTurno(int id_usuario, int turno) {
        EntityManager emt = getEntityManager();
        emt.getTransaction().begin();
        try {
            Query q = emt.createNativeQuery("CALL `sp_usa_m_usuario_turno`('" + id_usuario + "', '" + turno + "')");
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

    public List consultarRoles() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_roles`()");
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

    public List consultaFirmaUsuario(int documento, int codigo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("SELECT f.id_firma, f.documento, f.codigo, f.firma, f.estado, f.fch_registro from signature.firma f WHERE f.documento = " + documento + " and f.codigo = " + codigo + "");
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
    public List traerRol(int id_cargo) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_rol_c_id`('" + id_cargo + "')");
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

    public List traerUsuarioTurno() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_t_usuario_turno`()");
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

    public List consultaUsuarioId(int id_usuario) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_c_usuario_id`('" + id_usuario + "')");
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

    public List consultarUsuarios() {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            Query q = etm.createNativeQuery("CALL `sp_usa_c_usuarios`()");
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

    public List consultaPendientesFiltro(String filtro, String fecha_inicio, String fecha_fin, String id_usuarios, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "SELECT p.id_pendiente,p.descripcion,p.solucion,p.revisado,DATE_FORMAT(p.fecha_registro, '%Y/%m/%d | %H:%i'),p.id_usuario_envia,\n"
                    + "p.id_usuario_recibe,p.id_usuario_solucion,DATE_FORMAT(p.fecha_solucion, '%Y/%m/%d | %H:%i'),Concat(u.nombres,' ',u.apellidos),\n"
                    + "u2.nombre,Concat(u3.nombres,' ',u3.apellidos), p.asunto\n"
                    + "FROM pendiente p inner join usuario u on p.id_usuario_envia = u.id_usuario\n"
                    + "	inner join rol u2 on p.id_usuario_recibe = u2.id_rol\n"
                    + "	left join usuario u3 on p.id_usuario_solucion = u3.id_usuario\n"
                    + "WHERE (p.fecha_solucion between '" + fecha_inicio + "' and '" + fecha_fin + "') \n";
            String atributos = "";
            if (filtro.length() > 0) {
                if (filtro.contains("+")) {
                    String[] arg_filtro = filtro.replace("+", "---").split("---");
                    for (int i = 0; i < arg_filtro.length; i++) {
                        if (i == 0) {
                            atributos = "and (p.descripcion LIKE '%" + arg_filtro[i] + "%' OR p.solucion LIKE '%" + arg_filtro[i] + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + arg_filtro[i] + "%' OR u2.nombre LIKE '%" + arg_filtro[i] + "%' OR Concat(u3.nombres,' ',u3.apellidos) LIKE '%" + arg_filtro[i] + "%'";
                        } else {
                            atributos = atributos + "OR p.descripcion LIKE '%" + arg_filtro[i] + "%' OR p.solucion LIKE '%" + arg_filtro[i] + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + arg_filtro[i] + "%' OR u2.nombre LIKE '%" + arg_filtro[i] + "%' OR Concat(u3.nombres,' ',u3.apellidos) LIKE '%" + arg_filtro[i] + "%'";
                        }
                    }
                    atributos = atributos + ")";
                } else {
                    atributos = "and (p.descripcion LIKE '%" + filtro + "%' OR p.solucion LIKE '%" + filtro + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + filtro + "%' OR u2.nombre LIKE '%" + filtro + "%' OR Concat(u3.nombres,' ',u3.apellidos) LIKE '%" + filtro + "%')";
                }
            }
            if (!id_usuarios.equals("")) {
                String[] usuarios = id_usuarios.replace("][", "---").replace("[", "").replace("]", "").split("---");
                for (int i = 0; i < usuarios.length; i++) {
                    if (usuarios.length > 1) {
                        if (i == 0) {
                            atributos = atributos + " AND (";
                        } else {
                            atributos = atributos + " or ";
                        }
                        atributos = atributos + " p.id_usuario_envia=" + usuarios[i] + "    \n ";
                        if ((i + 1) == usuarios.length) {
                            atributos = atributos + " ) ";
                        }
                    } else {
                        atributos = atributos + " AND (p.id_usuario_envia=" + usuarios[i] + " ) \n";
                    }
                }
            }
            if (!rol.equals("Todos")) {
                atributos = atributos + " AND (u2.nombre='" + rol + "') \n";
            }
            query = query + atributos + " ORDER BY p.fecha_registro desc";
            Query q = etm.createNativeQuery(query);
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

    public List consultaCasosFiltro(String filtro, String fecha_inicio, String fecha_fin, String id_usuarios, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select c.id_caso,DATE_FORMAT(c.fecha_envio, '%Y/%m/%d | %H:%i'),c.id_area,c.id_tecnico_asignado,\n"
                    + "	r.nombre_reportante,c.solicitud,c.prioridad,c.id_tecnico_solucion,\n"
                    + "	DATE_FORMAT(c.fecha_solucion, '%Y/%m/%d | %H:%i'),c.solucion,\n"
                    + " concat(u.nombres,' ',u.apellidos)\n"
                    + " FROM caso c\n"
                    + "	inner join usuario u on u.id_usuario = c.id_tecnico_solucion\n"
                    + "	inner join area a on c.id_area = a.id_area\n"
                    + " inner join reportante r on c.id_reportante = r.id_reportante\n"
                    + " WHERE (c.fecha_envio between '" + fecha_inicio + "' and '" + fecha_fin + "')\n";
            String atributos = "";
            if (filtro.length() > 0) {
                if (filtro.contains("+")) {
                    String[] arg_filtro = filtro.replace("+", "---").split("---");
                    for (int i = 0; i < arg_filtro.length; i++) {
                        if (i == 0) {
                            atributos = "AND (a.nombre LIKE '%" + arg_filtro[i] + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + arg_filtro[i] + "%' OR c.solicitud LIKE '%" + arg_filtro[i] + "%' OR c.prioridad LIKE '%" + arg_filtro[i] + "%' OR c.solucion LIKE '%" + arg_filtro[i] + "%' ";
                        } else {
                            atributos = atributos + "OR a.nombre LIKE '%" + arg_filtro[i] + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + arg_filtro[i] + "%' OR c.solicitud LIKE '%" + arg_filtro[i] + "%' OR c.prioridad LIKE '%" + arg_filtro[i] + "%' OR c.solucion LIKE '%" + arg_filtro[i] + "%' ";
                        }
                    }
                    atributos = atributos + ")";
                } else {
                    atributos = "AND (a.nombre LIKE '%" + filtro + "%' OR Concat(u.nombres,' ',u.apellidos) LIKE '%" + filtro + "%' OR c.solicitud LIKE '%" + filtro + "%' OR c.prioridad LIKE '%" + filtro + "%' OR c.solucion LIKE '%" + filtro + "%') ";
                }
            }
            if (!id_usuarios.equals("")) {
                String[] usuarios = id_usuarios.replace("][", "---").replace("[", "").replace("]", "").split("---");
                for (int i = 0; i < usuarios.length; i++) {
                    if (usuarios.length > 1) {
                        if (i == 0) {
                            atributos = atributos + " AND (";
                        } else {
                            atributos = atributos + " or ";
                        }
                        atributos = atributos + " c.id_tecnico_solucion=" + usuarios[i] + "    \n ";
                        if ((i + 1) == usuarios.length) {
                            atributos = atributos + " ) ";
                        }
                    } else {
                        atributos = atributos + " AND (c.id_tecnico_solucion=" + usuarios[i] + " ) \n";
                    }
                }
            }
            if (!rol.equals("Todos")) {
                atributos = atributos + "\n";
            }
            query = query + atributos + " ORDER BY c.fecha_envio desc";
            Query q = etm.createNativeQuery(query);
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

    public List consultaActividadesFiltro(String filtro, String fecha_inicio, String fecha_fin, String id_usuarios, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select g.id_actividad,DATE_FORMAT(g.fecha_inicio, '%Y/%m/%d | %H:%i'),\n"
                    + "	DATE_FORMAT(g.fecha_fin, '%Y/%m/%d | %H:%i'),g.asunto,g.actividad,\n"
                    + "	g.usuario_registro,DATE_FORMAT(g.fecha_registro, '%Y/%m/%d | %H:%i'),\n"
                    + "	concat(r.nombre,' / ', u.nombres,' ',u.apellidos),concat(u.nombres,' ',u.apellidos)\n"
                    + "from actividad_general g inner join usuario u on g.usuario_registro = u.id_usuario\n"
                    + "	inner join rol r on u.id_rol = r.id_rol\n"
                    + "where (g.fecha_registro between '" + fecha_inicio + "' and '" + fecha_fin + "') \n";
            String atributos = "";
            if (filtro.length() > 0) {
                if (filtro.contains("+")) {
                    String[] arg_filtro = filtro.replace("+", "---").split("---");
                    for (int i = 0; i < arg_filtro.length; i++) {
                        if (i == 0) {
                            atributos = "and (g.asunto  LIKE '%" + arg_filtro[i] + "%'  OR g.actividad  LIKE '%" + arg_filtro[i] + "%' ";
                        } else {
                            atributos = atributos + " or g.asunto  LIKE '%" + arg_filtro[i] + "%'  OR g.actividad  LIKE '%" + arg_filtro[i] + "%' ";
                        }
                    }
                    atributos = atributos + ")";
                } else {
                    atributos = "and (g.asunto  LIKE '%" + filtro + "%' OR g.fecha_inicio  LIKE '%" + filtro + "%' OR g.fecha_fin  LIKE '%" + filtro + "%' OR g.actividad  LIKE '%" + filtro + "%' ) ";
                }
            }
            if (!id_usuarios.equals("")) {
                String[] usuarios = id_usuarios.replace("][", "---").replace("[", "").replace("]", "").split("---");
                for (int i = 0; i < usuarios.length; i++) {
                    if (usuarios.length > 1) {
                        if (i == 0) {
                            atributos = atributos + " AND (";
                        } else {
                            atributos = atributos + " or ";
                        }
                        atributos = atributos + " g.usuario_registro=" + usuarios[i] + "    \n ";
                        if ((i + 1) == usuarios.length) {
                            atributos = atributos + " ) ";
                        }
                    } else {
                        atributos = atributos + " AND (g.usuario_registro=" + usuarios[i] + " ) \n";
                    }
                }
            }
            if (!rol.equals("Todos")) {
                atributos = atributos + " AND (r.nombre='" + rol + "') \n";
            }
            query = query + atributos + " order by g.fecha_registro desc";
            Query q = etm.createNativeQuery(query);
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

    public List consultaActividadesReportanteFiltro(String filtro, String fecha_inicio, String fecha_fin, String id_usuarios, String rol) {
        EntityManager etm = getEntityManager();
        etm.getTransaction().begin();
        try {
            String query = "select r.id_actividad_reportada,r.reportante,r.id_equipo,e.nombre,e.responsable,r.id_tipo_soporte,t.nombre,\n"
                    + "	r.id_aplicativo,a.nombre,DATE_FORMAT(r.fecha_reportante, '%Y/%m/%d | %H:%i'),DATE_FORMAT(r.fecha_ejecucion, '%Y/%m/%d | %H:%i'),\n"
                    + "	DATE_FORMAT(r.fecha_terminacion, '%Y/%m/%d | %H:%i'),r.actividad,r.solucion,concat(rl.nombre,' / ', u.nombres,' ',u.apellidos),DATE_FORMAT(r.fecha_registro, '%Y/%m/%d | %H:%i'),r.Tiempo_Parada, r.Parada_Produccion\n"
                    + " from actividad_reportada r inner join equipo e on r.id_equipo = e.id_equipo\n"
                    + "	left join tipo_soporte t on r.id_tipo_soporte = t.id_tipo_soporte\n"
                    + "	left join aplicativo a on r.id_aplicativo = a.id_aplicativo\n"
                    + "	inner join usuario u on r.usuario_registro = u.id_usuario\n"
                    + "	inner join rol rl on u.id_rol = rl.id_rol\n"
                    + "where (r.fecha_terminacion between '" + fecha_inicio + "' and '" + fecha_fin + "')\n";
            String atributos = "";
            if (filtro.length() > 0) {
                if (filtro.contains("+")) {
                    String[] arg_filtro = filtro.replace("+", "---").split("---");
                    for (int i = 0; i < arg_filtro.length; i++) {
                        if (i == 0) {
                            atributos = "and (r.reportante LIKE '%" + arg_filtro[i] + "%' OR e.nombre LIKE '%" + arg_filtro[i] + "%' OR t.nombre LIKE '%" + arg_filtro[i] + "%' OR a.nombre LIKE '%" + arg_filtro[i] + "%' OR r.actividad LIKE '%" + arg_filtro[i] + "%' OR r.solucion LIKE '%" + arg_filtro[i] + "%'";
                        } else {
                            atributos = atributos + "OR r.reportante LIKE '%" + arg_filtro[i] + "%' OR e.nombre LIKE '%" + arg_filtro[i] + "%' OR t.nombre LIKE '%" + arg_filtro[i] + "%' OR a.nombre LIKE '%" + arg_filtro[i] + "%' OR r.actividad LIKE '%" + arg_filtro[i] + "%' OR r.solucion LIKE '%" + arg_filtro[i] + "%'";
                        }
                    }
                    atributos = atributos + ")";
                } else {
                    atributos = "and (r.reportante LIKE '%" + filtro + "%' OR e.nombre LIKE '%" + filtro + "%' OR t.nombre LIKE '%" + filtro + "%' OR a.nombre LIKE '%" + filtro + "%' OR r.actividad LIKE '%" + filtro + "%' OR r.solucion LIKE '%" + filtro + "%')";
                }
            }
            if (!id_usuarios.equals("")) {
                String[] usuarios = id_usuarios.replace("][", "---").replace("[", "").replace("]", "").split("---");
                for (int i = 0; i < usuarios.length; i++) {
                    if (usuarios.length > 1) {
                        if (i == 0) {
                            atributos = atributos + " AND (";
                        } else {
                            atributos = atributos + " or ";
                        }
                        atributos = atributos + " r.usuario_registro=" + usuarios[i] + "    \n ";
                        if ((i + 1) == usuarios.length) {
                            atributos = atributos + " ) ";
                        }
                    } else {
                        atributos = atributos + " AND (r.usuario_registro=" + usuarios[i] + " ) \n";
                    }
                }
            }
            if (!rol.equals("Todos")) {
                atributos = atributos + " AND (rl.nombre='" + rol + "') \n";
            }
            query = query + atributos + " order by r.fecha_registro desc";
            Query q = etm.createNativeQuery(query);
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
