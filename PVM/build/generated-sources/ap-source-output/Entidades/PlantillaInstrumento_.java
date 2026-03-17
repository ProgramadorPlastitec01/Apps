package Entidades;

import Entidades.Verificacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(PlantillaInstrumento.class)
public class PlantillaInstrumento_ { 

    public static volatile SingularAttribute<PlantillaInstrumento, Integer> estado;
    public static volatile CollectionAttribute<PlantillaInstrumento, Verificacion> verificacionCollection;
    public static volatile SingularAttribute<PlantillaInstrumento, Integer> idInstrumento;
    public static volatile SingularAttribute<PlantillaInstrumento, String> plantilla;
    public static volatile SingularAttribute<PlantillaInstrumento, Date> fechaRegistro;
    public static volatile SingularAttribute<PlantillaInstrumento, Integer> idPlantillaInstrumento;
    public static volatile SingularAttribute<PlantillaInstrumento, Integer> idTipoPlantilla;

}