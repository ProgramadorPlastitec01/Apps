package Entidades;

import Entidades.Proyecto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(FormulaC.class)
public class FormulaC_ { 

    public static volatile SingularAttribute<FormulaC, String> capa;
    public static volatile SingularAttribute<FormulaC, String> codigo;
    public static volatile SingularAttribute<FormulaC, Integer> estado;
    public static volatile SingularAttribute<FormulaC, String> extructura;
    public static volatile SingularAttribute<FormulaC, String> usuRegistro;
    public static volatile SingularAttribute<FormulaC, Date> fchSolicitud;
    public static volatile SingularAttribute<FormulaC, String> producto;
    public static volatile SingularAttribute<FormulaC, String> tMaterial;
    public static volatile SingularAttribute<FormulaC, String> tExtrusion;
    public static volatile SingularAttribute<FormulaC, Proyecto> fkProyecto;
    public static volatile SingularAttribute<FormulaC, Date> fchRegistro;
    public static volatile SingularAttribute<FormulaC, Integer> idFormulaC;
    public static volatile SingularAttribute<FormulaC, Integer> ctdCapa;
    public static volatile SingularAttribute<FormulaC, String> observacion;

}