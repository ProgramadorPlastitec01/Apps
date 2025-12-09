package Entity;

import Entity.Segmentation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-05T11:25:53")
@StaticMetamodel(Controls.class)
public class Controls_ { 

    public static volatile SingularAttribute<Controls, String> case1;
    public static volatile SingularAttribute<Controls, Integer> idControl;
    public static volatile SingularAttribute<Controls, String> userRegister;
    public static volatile SingularAttribute<Controls, Date> dateRegister;
    public static volatile SingularAttribute<Controls, String> description;
    public static volatile SingularAttribute<Controls, Segmentation> segmentation;
    public static volatile SingularAttribute<Controls, String> event;

}