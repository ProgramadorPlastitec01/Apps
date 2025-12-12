/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "attach")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attach.findAll", query = "SELECT a FROM Attach a")
    , @NamedQuery(name = "Attach.findByIdAttached", query = "SELECT a FROM Attach a WHERE a.idAttached = :idAttached")
    , @NamedQuery(name = "Attach.findByAffair", query = "SELECT a FROM Attach a WHERE a.affair = :affair")
    , @NamedQuery(name = "Attach.findByUserRegistration", query = "SELECT a FROM Attach a WHERE a.userRegistration = :userRegistration")
    , @NamedQuery(name = "Attach.findByRegistrationDate", query = "SELECT a FROM Attach a WHERE a.registrationDate = :registrationDate")
    , @NamedQuery(name = "Attach.findByUserModify", query = "SELECT a FROM Attach a WHERE a.userModify = :userModify")
    , @NamedQuery(name = "Attach.findByModifiedDate", query = "SELECT a FROM Attach a WHERE a.modifiedDate = :modifiedDate")})
public class Attach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_attached")
    private Integer idAttached;
    @Column(name = "affair")
    private String affair;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "user_registration")
    private String userRegistration;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "user_modify")
    private String userModify;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "id_certificate", referencedColumnName = "id_certificate")
    @ManyToOne
    private Certificates idCertificate;

    public Attach() {
    }

    public Attach(Integer idAttached) {
        this.idAttached = idAttached;
    }

    public Integer getIdAttached() {
        return idAttached;
    }

    public void setIdAttached(Integer idAttached) {
        this.idAttached = idAttached;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUserModify() {
        return userModify;
    }

    public void setUserModify(String userModify) {
        this.userModify = userModify;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Certificates getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(Certificates idCertificate) {
        this.idCertificate = idCertificate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttached != null ? idAttached.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attach)) {
            return false;
        }
        Attach other = (Attach) object;
        if ((this.idAttached == null && other.idAttached != null) || (this.idAttached != null && !this.idAttached.equals(other.idAttached))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Attach[ idAttached=" + idAttached + " ]";
    }
    
}
