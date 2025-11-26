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
import javax.persistence.Lob;
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
@Table(name = "format")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Format.findAll", query = "SELECT f FROM Format f")
    , @NamedQuery(name = "Format.findByIdFormat", query = "SELECT f FROM Format f WHERE f.idFormat = :idFormat")
    , @NamedQuery(name = "Format.findByApplication", query = "SELECT f FROM Format f WHERE f.application = :application")
    , @NamedQuery(name = "Format.findByRecord", query = "SELECT f FROM Format f WHERE f.record = :record")
    , @NamedQuery(name = "Format.findByVersion", query = "SELECT f FROM Format f WHERE f.version = :version")
    , @NamedQuery(name = "Format.findByState", query = "SELECT f FROM Format f WHERE f.state = :state")
    , @NamedQuery(name = "Format.findByUserRegistration", query = "SELECT f FROM Format f WHERE f.userRegistration = :userRegistration")
    , @NamedQuery(name = "Format.findByRegistrationDate", query = "SELECT f FROM Format f WHERE f.registrationDate = :registrationDate")})
public class Format implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_format")
    private Integer idFormat;
    @Column(name = "application")
    private String application;
    @Column(name = "record")
    private String record;
    @Column(name = "version")
    private String version;
    @Lob
    @Column(name = "template")
    private String template;
    @Column(name = "state")
    private Integer state;
    @Column(name = "user_registration")
    private String userRegistration;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    public Format() {
    }

    public Format(Integer idFormat) {
        this.idFormat = idFormat;
    }

    public Integer getIdFormat() {
        return idFormat;
    }

    public void setIdFormat(Integer idFormat) {
        this.idFormat = idFormat;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormat != null ? idFormat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Format)) {
            return false;
        }
        Format other = (Format) object;
        if ((this.idFormat == null && other.idFormat != null) || (this.idFormat != null && !this.idFormat.equals(other.idFormat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Format[ idFormat=" + idFormat + " ]";
    }
    
}
