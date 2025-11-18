package Entity;

import Entity.Segmentation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-12T16:54:32")
@StaticMetamodel(Ciiu.class)
public class Ciiu_ { 

    public static volatile SingularAttribute<Ciiu, Integer> idCiiu;
    public static volatile SingularAttribute<Ciiu, Integer> code;
    public static volatile SingularAttribute<Ciiu, Integer> riskLevel;
    public static volatile SingularAttribute<Ciiu, String> activity;
    public static volatile CollectionAttribute<Ciiu, Segmentation> segmentationCollection1;
    public static volatile SingularAttribute<Ciiu, Date> dateRegister;
    public static volatile SingularAttribute<Ciiu, Integer> state;
    public static volatile CollectionAttribute<Ciiu, Segmentation> segmentationCollection;

}