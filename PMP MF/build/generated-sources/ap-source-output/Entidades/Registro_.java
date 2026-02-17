package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-01-13T10:33:19")
@StaticMetamodel(Registro.class)
public class Registro_ { 

    public static volatile SingularAttribute<Registro, String> codigo;
    public static volatile SingularAttribute<Registro, String> observacionesGenerales;
    public static volatile SingularAttribute<Registro, Date> fechaVigencia;
    public static volatile SingularAttribute<Registro, Integer> estado;
    public static volatile SingularAttribute<Registro, String> usuarioRegistro;
    public static volatile SingularAttribute<Registro, String> notaImportante;
    public static volatile SingularAttribute<Registro, Date> fechaRegistro;
    public static volatile SingularAttribute<Registro, Integer> idTipoMtto;
    public static volatile SingularAttribute<Registro, String> nombre;
    public static volatile SingularAttribute<Registro, Integer> version;
    public static volatile SingularAttribute<Registro, Integer> idRegistro;

}