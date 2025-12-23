package Entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-23T08:31:37")
@StaticMetamodel(Setting.class)
public class Setting_ { 

    public static volatile SingularAttribute<Setting, Date> dateRegister;
    public static volatile SingularAttribute<Setting, String> description;
    public static volatile SingularAttribute<Setting, Integer> idSetting;
    public static volatile SingularAttribute<Setting, Integer> state;
    public static volatile SingularAttribute<Setting, String> category;
    public static volatile SingularAttribute<Setting, String> value;

}