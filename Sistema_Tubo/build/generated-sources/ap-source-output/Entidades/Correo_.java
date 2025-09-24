package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(Correo.class)
public class Correo_ { 

    public static volatile SingularAttribute<Correo, Integer> idCorreo;
    public static volatile SingularAttribute<Correo, String> password;
    public static volatile SingularAttribute<Correo, Integer> estado;
    public static volatile SingularAttribute<Correo, String> receptor;
    public static volatile SingularAttribute<Correo, Integer> port;
    public static volatile SingularAttribute<Correo, String> usuarioRegistro;
    public static volatile SingularAttribute<Correo, Date> fechaRegistro;
    public static volatile SingularAttribute<Correo, String> host;
    public static volatile SingularAttribute<Correo, String> funcion;
    public static volatile SingularAttribute<Correo, String> emisor;

}