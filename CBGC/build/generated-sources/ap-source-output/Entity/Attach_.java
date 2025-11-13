package Entity;

import Entity.Certificates;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-13T15:08:24")
@StaticMetamodel(Attach.class)
public class Attach_ { 

    public static volatile SingularAttribute<Attach, String> userRegistration;
    public static volatile SingularAttribute<Attach, Certificates> idCertificate;
    public static volatile SingularAttribute<Attach, Date> registrationDate;
    public static volatile SingularAttribute<Attach, Date> modifiedDate;
    public static volatile SingularAttribute<Attach, String> description;
    public static volatile SingularAttribute<Attach, String> userModify;
    public static volatile SingularAttribute<Attach, String> affair;
    public static volatile SingularAttribute<Attach, Integer> idAttached;

}