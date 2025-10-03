package Entity;

import Entity.Attach;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-10-02T16:29:26")
@StaticMetamodel(Certificates.class)
public class Certificates_ { 

    public static volatile SingularAttribute<Certificates, String> product;
    public static volatile SingularAttribute<Certificates, String> code;
    public static volatile SingularAttribute<Certificates, String> qualitySignature;
    public static volatile SingularAttribute<Certificates, String> authorizationSignature;
    public static volatile SingularAttribute<Certificates, String> userRegistration;
    public static volatile SingularAttribute<Certificates, String> format;
    public static volatile SingularAttribute<Certificates, String> batch;
    public static volatile SingularAttribute<Certificates, String> consecutiveQuality;
    public static volatile SingularAttribute<Certificates, String> sample;
    public static volatile SingularAttribute<Certificates, String> result;
    public static volatile SingularAttribute<Certificates, Integer> idCertificate;
    public static volatile SingularAttribute<Certificates, Date> registrationDate;
    public static volatile CollectionAttribute<Certificates, Attach> attachCollection;
    public static volatile SingularAttribute<Certificates, Integer> state;
    public static volatile SingularAttribute<Certificates, String> order;

}