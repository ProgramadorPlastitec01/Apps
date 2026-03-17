package Entidades;

import Entidades.Defecto;
import Entidades.Solicitud;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Movimientos.class)
public class Movimientos_ { 

    public static volatile SingularAttribute<Movimientos, String> descripcionSalida;
    public static volatile SingularAttribute<Movimientos, Integer> idMovimientos;
    public static volatile SingularAttribute<Movimientos, String> usuarioRegistroEntrada;
    public static volatile SingularAttribute<Movimientos, String> encargadoEntrega;
    public static volatile SingularAttribute<Movimientos, String> tipoSalida;
    public static volatile SingularAttribute<Movimientos, String> pieza;
    public static volatile SingularAttribute<Movimientos, Date> fechaRegistroSalida;
    public static volatile SingularAttribute<Movimientos, Solicitud> idSolicitud;
    public static volatile SingularAttribute<Movimientos, String> usuarioRegistroSalida;
    public static volatile SingularAttribute<Movimientos, String> tipoEntrada;
    public static volatile SingularAttribute<Movimientos, Defecto> idDefecto;
    public static volatile SingularAttribute<Movimientos, Date> fechaSalida;
    public static volatile SingularAttribute<Movimientos, Date> fechaEntrada;
    public static volatile SingularAttribute<Movimientos, Date> fechaRegistroEntrada;
    public static volatile SingularAttribute<Movimientos, String> descripcionEntrada;

}