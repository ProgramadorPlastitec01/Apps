package Entidades;

import Entidades.OrdenTrabajo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-03-12T10:25:19")
@StaticMetamodel(OrdenTrabajoDetalle.class)
public class OrdenTrabajoDetalle_ { 

    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> descripcion;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, OrdenTrabajo> idOrdenTrabajo;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> noPmp;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> repuestos;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, Integer> idOrdenTrabajoDetalle;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, Integer> contEjecutadas;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> pendientes;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, Date> fechaRealizado;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> actividades;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, Integer> contProgramadas;
    public static volatile SingularAttribute<OrdenTrabajoDetalle, String> logResponsables;

}