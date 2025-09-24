package Entidades;

import Entidades.Registro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(ControlInternoBobina.class)
public class ControlInternoBobina_ { 

    public static volatile SingularAttribute<ControlInternoBobina, Integer> codigoGalga;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> diametroMedida2;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> idControl;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> diametroMedida1;
    public static volatile SingularAttribute<ControlInternoBobina, String> codigoTambor;
    public static volatile SingularAttribute<ControlInternoBobina, Date> fechaRegistro;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> rollo;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> concepto;
    public static volatile SingularAttribute<ControlInternoBobina, String> usuarioRegistros;
    public static volatile SingularAttribute<ControlInternoBobina, Integer> turnoHora;
    public static volatile SingularAttribute<ControlInternoBobina, Registro> idRegistro;

}