/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "certificates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Certificates.findAll", query = "SELECT c FROM Certificates c")
    , @NamedQuery(name = "Certificates.findByIdCertificate", query = "SELECT c FROM Certificates c WHERE c.idCertificate = :idCertificate")
    , @NamedQuery(name = "Certificates.findByConsecutiveQuality", query = "SELECT c FROM Certificates c WHERE c.consecutiveQuality = :consecutiveQuality")
    , @NamedQuery(name = "Certificates.findByCode", query = "SELECT c FROM Certificates c WHERE c.code = :code")
    , @NamedQuery(name = "Certificates.findBySample", query = "SELECT c FROM Certificates c WHERE c.sample = :sample")
    , @NamedQuery(name = "Certificates.findByResult", query = "SELECT c FROM Certificates c WHERE c.result = :result")
    , @NamedQuery(name = "Certificates.findByOrder", query = "SELECT c FROM Certificates c WHERE c.order = :order")
    , @NamedQuery(name = "Certificates.findByProduct", query = "SELECT c FROM Certificates c WHERE c.product = :product")
    , @NamedQuery(name = "Certificates.findByBatch", query = "SELECT c FROM Certificates c WHERE c.batch = :batch")
    , @NamedQuery(name = "Certificates.findByState", query = "SELECT c FROM Certificates c WHERE c.state = :state")
    , @NamedQuery(name = "Certificates.findByQualitySignature", query = "SELECT c FROM Certificates c WHERE c.qualitySignature = :qualitySignature")
    , @NamedQuery(name = "Certificates.findByAuthorizationSignature", query = "SELECT c FROM Certificates c WHERE c.authorizationSignature = :authorizationSignature")
    , @NamedQuery(name = "Certificates.findByUserRegistration", query = "SELECT c FROM Certificates c WHERE c.userRegistration = :userRegistration")
    , @NamedQuery(name = "Certificates.findByRegistrationDate", query = "SELECT c FROM Certificates c WHERE c.registrationDate = :registrationDate")})
public class Certificates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_certificate")
    private Integer idCertificate;
    @Column(name = "consecutive_quality")
    private String consecutiveQuality;
    @Column(name = "code")
    private String code;
    @Column(name = "sample")
    private String sample;
    @Column(name = "result")
    private String result;
    @Lob
    @Column(name = "format")
    private String format;
    @Column(name = "order")
    private String order;
    @Column(name = "product")
    private String product;
    @Column(name = "batch")
    private String batch;
    @Column(name = "state")
    private Integer state;
    @Column(name = "quality_signature")
    private String qualitySignature;
    @Column(name = "authorization_signature")
    private String authorizationSignature;
    @Column(name = "user_registration")
    private String userRegistration;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    public Certificates() {
    }

    public Certificates(Integer idCertificate) {
        this.idCertificate = idCertificate;
    }

    public Integer getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(Integer idCertificate) {
        this.idCertificate = idCertificate;
    }

    public String getConsecutiveQuality() {
        return consecutiveQuality;
    }

    public void setConsecutiveQuality(String consecutiveQuality) {
        this.consecutiveQuality = consecutiveQuality;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getQualitySignature() {
        return qualitySignature;
    }

    public void setQualitySignature(String qualitySignature) {
        this.qualitySignature = qualitySignature;
    }

    public String getAuthorizationSignature() {
        return authorizationSignature;
    }

    public void setAuthorizationSignature(String authorizationSignature) {
        this.authorizationSignature = authorizationSignature;
    }

    public String getUserRegistration() {
        return userRegistration;
    }

    public void setUserRegistration(String userRegistration) {
        this.userRegistration = userRegistration;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @XmlTransient

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCertificate != null ? idCertificate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Certificates)) {
            return false;
        }
        Certificates other = (Certificates) object;
        if ((this.idCertificate == null && other.idCertificate != null) || (this.idCertificate != null && !this.idCertificate.equals(other.idCertificate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Certificates[ idCertificate=" + idCertificate + " ]";
    }
    
}
