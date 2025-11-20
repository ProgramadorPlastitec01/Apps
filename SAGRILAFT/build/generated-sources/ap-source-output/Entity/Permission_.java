package Entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-20T17:24:43")
@StaticMetamodel(Permission.class)
public class Permission_ { 

    public static volatile SingularAttribute<Permission, Integer> idPermission;
    public static volatile SingularAttribute<Permission, String> module;
    public static volatile SingularAttribute<Permission, String> userRegister;
    public static volatile SingularAttribute<Permission, Date> dateRegister;
    public static volatile SingularAttribute<Permission, String> description;
    public static volatile SingularAttribute<Permission, Integer> state;
    public static volatile SingularAttribute<Permission, String> option;

}