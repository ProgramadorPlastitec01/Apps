package Metodos;

public class Filtro_dinamico {

    public static String Filtro_dinamico(String ffi, String ffn, String fto, String tfc, String cmp) {
        String[] filtro = cmp.replace("][", "///").replace("[", "").replace("]", "").split("///");
        String[] filtro2 = fto.replace("][", "///").replace("[", "").replace("]", "").split("///");
        String condicion = "a." + tfc + " BETWEEN '" + ffi + "' AND '" + ffn + "' AND (";
        for (int i = 0; i < filtro.length; i++) {
            for (int n = 0; n < filtro2.length; n++) {
                if (i != (filtro.length - 1) || n != (filtro2.length - 1)) {
                    condicion = condicion + "a." + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%') OR ";
                } else {
                    condicion = condicion + "a." + filtro[i] + " LIKE CONCAT ('%','" + filtro2[n] + "','%')";
                }
            }
            if (i == (filtro.length - 1)) {
                condicion = condicion + ")";
            }

        }
        String query = ("select a.id_activo, a.codigo, a.planta,a.bodega, a.piso, a.proceso, a.area,  ar.nombre as Area, a.nombre_equipo, a.marca, \n"
                + "a.modelo, a.serie, a.ano_fabricacion,a.fabricante,a.orden_compra, a.fecha_compra, a.costo, a.num_factura, a.descripcion, a.fecha_ingreso, a.estado, a.tipo_activo, a.area_registro,\n"
                + "a.fecha_dada_baja,a.jusificacion_dada_baja \n"
                + "FROM activo a INNER JOIN area ar on a.area=ar.id_area \n"
                + "WHERE " + condicion);
        return query;
    }
}
