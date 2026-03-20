package Entidad;

import Entidad.LogManual;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(LogManual.class)
public class LogManual_ { 

    public static volatile SingularAttribute<LogManual, String> versionNew;
    public static volatile SingularAttribute<LogManual, String> justificacion;
    public static volatile CollectionAttribute<LogManual, LogManual> logManualCollection;
    public static volatile SingularAttribute<LogManual, String> versionOld;
    public static volatile SingularAttribute<LogManual, LogManual> logManual;
    public static volatile SingularAttribute<LogManual, String> usuario;
    public static volatile SingularAttribute<LogManual, Date> fechaInsercion;
    public static volatile SingularAttribute<LogManual, Integer> idLogManual;

}