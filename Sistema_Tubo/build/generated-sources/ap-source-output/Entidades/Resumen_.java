package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(Resumen.class)
public class Resumen_ { 

    public static volatile SingularAttribute<Resumen, Integer> idLinea;
    public static volatile SingularAttribute<Resumen, Integer> estado;
    public static volatile SingularAttribute<Resumen, String> lote;
    public static volatile SingularAttribute<Resumen, Date> fechaRegistro;
    public static volatile SingularAttribute<Resumen, String> numeroCertificado;
    public static volatile SingularAttribute<Resumen, String> producto;
    public static volatile SingularAttribute<Resumen, Integer> idResumen;
    public static volatile SingularAttribute<Resumen, String> fechaFin;
    public static volatile SingularAttribute<Resumen, String> fechaInicio;
    public static volatile SingularAttribute<Resumen, String> usuarioRegistro;
    public static volatile SingularAttribute<Resumen, String> orden;
    public static volatile SingularAttribute<Resumen, String> cantidadRollos;
    public static volatile SingularAttribute<Resumen, String> fechaDespacho;

}