package Entidades;

import Entidades.NoConformidad;
import Entidades.Tipo;
import Entidades.TipoInstrumento;
import Entidades.Traslado;
import Entidades.Verificacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(InstrumentoMedicion.class)
public class InstrumentoMedicion_ { 

    public static volatile SingularAttribute<InstrumentoMedicion, String> codigo;
    public static volatile SingularAttribute<InstrumentoMedicion, String> rangoMedida;
    public static volatile SingularAttribute<InstrumentoMedicion, Integer> estado;
    public static volatile SingularAttribute<InstrumentoMedicion, Tipo> tipo;
    public static volatile SingularAttribute<InstrumentoMedicion, Date> fchUltimaVerificacionExt;
    public static volatile SingularAttribute<InstrumentoMedicion, String> usuRegistro;
    public static volatile SingularAttribute<InstrumentoMedicion, String> modelo;
    public static volatile CollectionAttribute<InstrumentoMedicion, Traslado> trasladoCollection;
    public static volatile SingularAttribute<InstrumentoMedicion, String> numeroSerial;
    public static volatile CollectionAttribute<InstrumentoMedicion, NoConformidad> noConformidadCollection;
    public static volatile SingularAttribute<InstrumentoMedicion, TipoInstrumento> tipoInstrumento;
    public static volatile SingularAttribute<InstrumentoMedicion, String> exactitud;
    public static volatile SingularAttribute<InstrumentoMedicion, Date> fchUltimaVerificacionInt;
    public static volatile CollectionAttribute<InstrumentoMedicion, Verificacion> verificacionCollection;
    public static volatile SingularAttribute<InstrumentoMedicion, Integer> idPlantillaVerificacion;
    public static volatile SingularAttribute<InstrumentoMedicion, String> instrumento;
    public static volatile SingularAttribute<InstrumentoMedicion, Date> fchRegistro;
    public static volatile SingularAttribute<InstrumentoMedicion, String> observaciones;
    public static volatile SingularAttribute<InstrumentoMedicion, Integer> idInstrumentoMedicion;
    public static volatile SingularAttribute<InstrumentoMedicion, String> fabricante;
    public static volatile SingularAttribute<InstrumentoMedicion, String> clasificacion;
    public static volatile SingularAttribute<InstrumentoMedicion, String> divisionEscala;

}