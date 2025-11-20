package Entity;

import Entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-20T11:14:01")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile SingularAttribute<Role, Integer> idRole;
    public static volatile CollectionAttribute<Role, User> userCollection;
    public static volatile SingularAttribute<Role, String> name;
    public static volatile SingularAttribute<Role, String> userRegister;
    public static volatile SingularAttribute<Role, Date> dateRegister;
    public static volatile SingularAttribute<Role, String> permission;
    public static volatile SingularAttribute<Role, Integer> state;

}