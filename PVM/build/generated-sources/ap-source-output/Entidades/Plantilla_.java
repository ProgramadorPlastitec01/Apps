package Entidades;

import Entidades.TipoPlantilla;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(Plantilla.class)
public class Plantilla_ { 

    public static volatile SingularAttribute<Plantilla, String> codigo;
    public static volatile SingularAttribute<Plantilla, Integer> estado;
    public static volatile SingularAttribute<Plantilla, Date> fchVigencia;
    public static volatile SingularAttribute<Plantilla, String> plantilla;
    public static volatile SingularAttribute<Plantilla, TipoPlantilla> tipoPlantilla;
    public static volatile SingularAttribute<Plantilla, Date> fchRegistro;
    public static volatile SingularAttribute<Plantilla, Integer> idPlantilla;
    public static volatile SingularAttribute<Plantilla, Integer> version;

}