package Entidades;

import Entidades.InstrumentoMedicion;
import Entidades.PlantillaInstrumento;
import Entidades.TipoVerificacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-05-20T11:40:02")
@StaticMetamodel(Verificacion.class)
public class Verificacion_ { 

    public static volatile SingularAttribute<Verificacion, String> justificacion;
    public static volatile SingularAttribute<Verificacion, Integer> estado;
    public static volatile SingularAttribute<Verificacion, String> adjunto;
    public static volatile SingularAttribute<Verificacion, Date> fchRegistro;
    public static volatile SingularAttribute<Verificacion, InstrumentoMedicion> instrumentoMedicion;
    public static volatile SingularAttribute<Verificacion, Integer> idVerificacion;
    public static volatile SingularAttribute<Verificacion, TipoVerificacion> tipoVerificacion;
    public static volatile SingularAttribute<Verificacion, String> usuRegistro;
    public static volatile SingularAttribute<Verificacion, PlantillaInstrumento> plantillaInstrumento;

}