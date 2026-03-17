package Entidades;

import Entidades.Area;
import Entidades.InstrumentoMedicion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(TipoInstrumento.class)
public class TipoInstrumento_ { 

    public static volatile SingularAttribute<TipoInstrumento, Area> area;
    public static volatile SingularAttribute<TipoInstrumento, String> tipo;
    public static volatile SingularAttribute<TipoInstrumento, Integer> estado;
    public static volatile SingularAttribute<TipoInstrumento, Integer> idTipoInstrumento;
    public static volatile SingularAttribute<TipoInstrumento, Integer> frecuenciaInterna;
    public static volatile SingularAttribute<TipoInstrumento, Integer> grafica;
    public static volatile SingularAttribute<TipoInstrumento, String> usuRegistro;
    public static volatile SingularAttribute<TipoInstrumento, Integer> idPlantilla;
    public static volatile SingularAttribute<TipoInstrumento, Integer> tipoFrecuencia;
    public static volatile SingularAttribute<TipoInstrumento, Integer> toleranciaInterna;
    public static volatile SingularAttribute<TipoInstrumento, Integer> toleranciaExterna;
    public static volatile SingularAttribute<TipoInstrumento, Date> fchRegistro;
    public static volatile SingularAttribute<TipoInstrumento, Integer> frecuenciaExterna;
    public static volatile CollectionAttribute<TipoInstrumento, InstrumentoMedicion> instrumentoMedicionCollection;

}