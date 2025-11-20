package Entity;

import Entity.Document;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-20T11:14:01")
@StaticMetamodel(Template.class)
public class Template_ { 

    public static volatile SingularAttribute<Template, Date> dateModify;
    public static volatile SingularAttribute<Template, String> format;
    public static volatile SingularAttribute<Template, Date> dateRegister;
    public static volatile CollectionAttribute<Template, Document> documentCollection;
    public static volatile SingularAttribute<Template, Integer> idTemplate;
    public static volatile SingularAttribute<Template, Integer> state;
    public static volatile SingularAttribute<Template, Integer> type;
    public static volatile SingularAttribute<Template, Integer> version;

}