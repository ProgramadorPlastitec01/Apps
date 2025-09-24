package Entidades;

import Entidades.Registro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(VerificacionMetraje.class)
public class VerificacionMetraje_ { 

    public static volatile SingularAttribute<VerificacionMetraje, String> usuarioRegistro;
    public static volatile SingularAttribute<VerificacionMetraje, Integer> idVerificacion;
    public static volatile SingularAttribute<VerificacionMetraje, Date> fechaRegistros;
    public static volatile SingularAttribute<VerificacionMetraje, String> turnos;
    public static volatile SingularAttribute<VerificacionMetraje, String> items;
    public static volatile SingularAttribute<VerificacionMetraje, Registro> idRegistro;

}