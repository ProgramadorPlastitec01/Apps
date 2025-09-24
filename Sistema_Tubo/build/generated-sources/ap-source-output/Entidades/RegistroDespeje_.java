package Entidades;

import Entidades.Registro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(RegistroDespeje.class)
public class RegistroDespeje_ { 

    public static volatile SingularAttribute<RegistroDespeje, Integer> estado;
    public static volatile SingularAttribute<RegistroDespeje, String> usuarioRegistro;
    public static volatile SingularAttribute<RegistroDespeje, Integer> idDespeje;
    public static volatile SingularAttribute<RegistroDespeje, Date> fechaRegistro;
    public static volatile SingularAttribute<RegistroDespeje, String> formato;
    public static volatile SingularAttribute<RegistroDespeje, Registro> idRegistro;

}