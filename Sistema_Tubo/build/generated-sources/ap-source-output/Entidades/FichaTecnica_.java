package Entidades;

import Entidades.OrdenProduccion;
import Entidades.Registro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(FichaTecnica.class)
public class FichaTecnica_ { 

    public static volatile SingularAttribute<FichaTecnica, Integer> estado;
    public static volatile CollectionAttribute<FichaTecnica, Registro> registroCollection;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroInteriorBobina;
    public static volatile SingularAttribute<FichaTecnica, Double> alturaBobina;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroInteriorMin;
    public static volatile SingularAttribute<FichaTecnica, String> producto;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExterior;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExteriorMax;
    public static volatile SingularAttribute<FichaTecnica, Double> rugosidad;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroInterior;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExteriorBobinaMin;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroInteriorMax;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExteriorBobina;
    public static volatile SingularAttribute<FichaTecnica, Integer> inspeccionVisual;
    public static volatile SingularAttribute<FichaTecnica, String> observacion;
    public static volatile SingularAttribute<FichaTecnica, Double> espesorPared;
    public static volatile SingularAttribute<FichaTecnica, String> codigo;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExteriorBobinaMax;
    public static volatile SingularAttribute<FichaTecnica, Date> fechaRegistro;
    public static volatile SingularAttribute<FichaTecnica, Double> diametroExteriorMin;
    public static volatile SingularAttribute<FichaTecnica, Integer> version;
    public static volatile SingularAttribute<FichaTecnica, Double> espesorParedMin;
    public static volatile SingularAttribute<FichaTecnica, Integer> idFichaTecnica;
    public static volatile SingularAttribute<FichaTecnica, String> usuarioRegistro;
    public static volatile CollectionAttribute<FichaTecnica, OrdenProduccion> ordenProduccionCollection;
    public static volatile SingularAttribute<FichaTecnica, Double> espesorParedMax;

}