package Entidades;

import Entidades.Registro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Maquina.class)
public class Maquina_ { 

    public static volatile SingularAttribute<Maquina, Integer> estado;
    public static volatile SingularAttribute<Maquina, Integer> idMaquina;
    public static volatile SingularAttribute<Maquina, Date> fechaRegistro;
    public static volatile CollectionAttribute<Maquina, Registro> registroCollection;
    public static volatile SingularAttribute<Maquina, String> nombre;

}