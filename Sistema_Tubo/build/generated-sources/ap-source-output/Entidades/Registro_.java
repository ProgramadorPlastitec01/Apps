package Entidades;

import Entidades.ControlInternoBobina;
import Entidades.FichaTecnica;
import Entidades.RegistroDespeje;
import Entidades.Rollo;
import Entidades.VerificacionMetraje;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(Registro.class)
public class Registro_ { 

    public static volatile SingularAttribute<Registro, Integer> estado;
    public static volatile SingularAttribute<Registro, Date> fechaRegistro;
    public static volatile SingularAttribute<Registro, Integer> loteC;
    public static volatile SingularAttribute<Registro, Integer> turno;
    public static volatile CollectionAttribute<Registro, RegistroDespeje> registroDespejeCollection;
    public static volatile SingularAttribute<Registro, Integer> fecha;
    public static volatile SingularAttribute<Registro, String> serial;
    public static volatile SingularAttribute<Registro, String> usuarioRegistro;
    public static volatile SingularAttribute<Registro, FichaTecnica> idFichaTecnica;
    public static volatile CollectionAttribute<Registro, Rollo> rolloCollection;
    public static volatile SingularAttribute<Registro, Integer> loteP;
    public static volatile SingularAttribute<Registro, Integer> loteProducto;
    public static volatile SingularAttribute<Registro, Integer> consecutivoCalidad;
    public static volatile CollectionAttribute<Registro, ControlInternoBobina> controlInternoBobinaCollection;
    public static volatile CollectionAttribute<Registro, VerificacionMetraje> verificacionMetrajeCollection;
    public static volatile SingularAttribute<Registro, Integer> idRegistro;

}