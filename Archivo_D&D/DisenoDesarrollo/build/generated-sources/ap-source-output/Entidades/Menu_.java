package Entidades;

import Entidades.Modulo;
import Entidades.Opcion;
import Entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(Menu.class)
public class Menu_ { 

    public static volatile SingularAttribute<Menu, Opcion> fkOpcion;
    public static volatile SingularAttribute<Menu, Usuario> fkUsuario;
    public static volatile SingularAttribute<Menu, Date> fchRegistro;
    public static volatile SingularAttribute<Menu, Integer> idMenu;
    public static volatile SingularAttribute<Menu, Modulo> fkModulo;
    public static volatile SingularAttribute<Menu, String> usuRegistro;

}