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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Programador.TI2
 */
@Entity
@Table(name = "template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Template.findAll", query = "SELECT t FROM Template t")
    , @NamedQuery(name = "Template.findByIdTemplate", query = "SELECT t FROM Template t WHERE t.idTemplate = :idTemplate")
    , @NamedQuery(name = "Template.findByType", query = "SELECT t FROM Template t WHERE t.type = :type")
    , @NamedQuery(name = "Template.findByVersion", query = "SELECT t FROM Template t WHERE t.version = :version")
    , @NamedQuery(name = "Template.findByState", query = "SELECT t FROM Template t WHERE t.state = :state")
    , @NamedQuery(name = "Template.findByDateModify", query = "SELECT t FROM Template t WHERE t.dateModify = :dateModify")
    , @NamedQuery(name = "Template.findByDateRegister", query = "SELECT t FROM Template t WHERE t.dateRegister = :dateRegister")})
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdTemplate")
    private Integer idTemplate;
    @Column(name = "Type")
    private Integer type;
    @Lob
    @Column(name = "Format")
    private String format;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "State")
    private Integer state;
    @Column(name = "DateModify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModify;
    @Column(name = "DateRegister")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
    @OneToMany(mappedBy = "template1")
    private Collection<Document> documentCollection;

    public Template() {
    }

    public Template(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Integer getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public void setDateModify(Date dateModify) {
        this.dateModify = dateModify;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection() {
        return documentCollection;
    }

    public void setDocumentCollection(Collection<Document> documentCollection) {
        this.documentCollection = documentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemplate != null ? idTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Template)) {
            return false;
        }
        Template other = (Template) object;
        if ((this.idTemplate == null && other.idTemplate != null) || (this.idTemplate != null && !this.idTemplate.equals(other.idTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mail.Template[ idTemplate=" + idTemplate + " ]";
    }
    
}
