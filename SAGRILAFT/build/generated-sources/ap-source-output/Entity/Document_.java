package Entity;

import Entity.Template;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-26T09:06:46")
@StaticMetamodel(Document.class)
public class Document_ { 

    public static volatile SingularAttribute<Document, String> template;
    public static volatile SingularAttribute<Document, String> mail;
    public static volatile SingularAttribute<Document, Integer> idDocument;
    public static volatile SingularAttribute<Document, String> businessName;
    public static volatile SingularAttribute<Document, Date> dateRegister;
    public static volatile SingularAttribute<Document, String> files;
    public static volatile SingularAttribute<Document, Date> dateModifyClient;
    public static volatile SingularAttribute<Document, Date> userModify;
    public static volatile SingularAttribute<Document, Template> template1;
    public static volatile SingularAttribute<Document, Integer> state;
    public static volatile SingularAttribute<Document, Date> dateModifyUser;

}