package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(EntradaOtro.class)
public class EntradaOtro_ { 

    public static volatile SingularAttribute<EntradaOtro, Integer> idProyecto;
    public static volatile SingularAttribute<EntradaOtro, Integer> idEntradaOtro;
    public static volatile SingularAttribute<EntradaOtro, String> usuarioRegistro;
    public static volatile SingularAttribute<EntradaOtro, Date> fechaRegistro;
    public static volatile SingularAttribute<EntradaOtro, String> asunto;
    public static volatile SingularAttribute<EntradaOtro, String> observacion;

}