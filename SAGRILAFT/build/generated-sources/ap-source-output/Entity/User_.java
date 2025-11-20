package Entity;

import Entity.Role;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-20T11:14:01")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> idUser;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, String> mail;
    public static volatile SingularAttribute<User, Integer> document;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> userRegister;
    public static volatile SingularAttribute<User, Date> dateRegister;
    public static volatile SingularAttribute<User, Integer> position;
    public static volatile SingularAttribute<User, Integer> state;
    public static volatile SingularAttribute<User, String> user;

}