package Entidad;

import Entidad.TipoProceso;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(Pregunta.class)
public class Pregunta_ { 

    public static volatile SingularAttribute<Pregunta, String> opciones;
    public static volatile SingularAttribute<Pregunta, Date> fchRegistro;
    public static volatile SingularAttribute<Pregunta, TipoProceso> idTipoProceso;
    public static volatile SingularAttribute<Pregunta, String> usuRegistro;
    public static volatile SingularAttribute<Pregunta, String> respuesta;
    public static volatile SingularAttribute<Pregunta, Integer> idPregunta;
    public static volatile SingularAttribute<Pregunta, String> pregunta;

}