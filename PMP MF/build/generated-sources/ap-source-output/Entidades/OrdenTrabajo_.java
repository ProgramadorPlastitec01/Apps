package Entidades;

import Entidades.OrdenTrabajoDetalle;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-12T10:33:46")
@StaticMetamodel(OrdenTrabajo.class)
public class OrdenTrabajo_ { 

    public static volatile SingularAttribute<OrdenTrabajo, Integer> tipoMtto;
    public static volatile SingularAttribute<OrdenTrabajo, Integer> idOrdenTrabajo;
    public static volatile SingularAttribute<OrdenTrabajo, Integer> idEquipo;
    public static volatile SingularAttribute<OrdenTrabajo, Integer> estado;
    public static volatile SingularAttribute<OrdenTrabajo, Integer> numero;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaReciboGc;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaRegistro;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaProgramacion;
    public static volatile CollectionAttribute<OrdenTrabajo, OrdenTrabajoDetalle> ordenTrabajoDetalleCollection;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaEjecucion;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioReciboGc;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioVerificacion;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaReciboPrf;
    public static volatile SingularAttribute<OrdenTrabajo, Date> fechaVerificacion;
    public static volatile SingularAttribute<OrdenTrabajo, String> tiempoEstimado;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioRegistro;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioEjecucion;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioProgramacion;
    public static volatile SingularAttribute<OrdenTrabajo, String> usuarioReciboPrf;

}