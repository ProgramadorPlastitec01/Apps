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
 * @author Programador.TI2
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d")
    , @NamedQuery(name = "Document.findByIdDocument", query = "SELECT d FROM Document d WHERE d.idDocument = :idDocument")
    , @NamedQuery(name = "Document.findByMail", query = "SELECT d FROM Document d WHERE d.mail = :mail")
    , @NamedQuery(name = "Document.findByBusinessName", query = "SELECT d FROM Document d WHERE d.businessName = :businessName")
    , @NamedQuery(name = "Document.findByState", query = "SELECT d FROM Document d WHERE d.state = :state")
    , @NamedQuery(name = "Document.findByDateModifyClient", query = "SELECT d FROM Document d WHERE d.dateModifyClient = :dateModifyClient")
    , @NamedQuery(name = "Document.findByDateModifyUser", query = "SELECT d FROM Document d WHERE d.dateModifyUser = :dateModifyUser")
    , @NamedQuery(name = "Document.findByUserModify", query = "SELECT d FROM Document d WHERE d.userModify = :userModify")
    , @NamedQuery(name = "Document.findByDateRegister", query = "SELECT d FROM Document d WHERE d.dateRegister = :dateRegister")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdDocument")
    private Integer idDocument;
    @Column(name = "Mail")
    private String mail;
    @Column(name = "BusinessName")
    private String businessName;
    @Lob
    @Column(name = "Template")
    private String template;
    @Lob
    @Column(name = "Files")
    private String files;
    @Column(name = "State")
    private Integer state;
    @Column(name = "DateModifyClient")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModifyClient;
    @Column(name = "DateModifyUser")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModifyUser;
    @Column(name = "UserModify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userModify;
    @Column(name = "DateRegister")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
    @JoinColumn(name = "IdTemplate", referencedColumnName = "IdTemplate")
    @ManyToOne
    private Template template1;

    public Document() {
    }

    public Document(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDateModifyClient() {
        return dateModifyClient;
    }

    public void setDateModifyClient(Date dateModifyClient) {
        this.dateModifyClient = dateModifyClient;
    }

    public Date getDateModifyUser() {
        return dateModifyUser;
    }

    public void setDateModifyUser(Date dateModifyUser) {
        this.dateModifyUser = dateModifyUser;
    }

    public Date getUserModify() {
        return userModify;
    }

    public void setUserModify(Date userModify) {
        this.userModify = userModify;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Template getTemplate1() {
        return template1;
    }

    public void setTemplate1(Template template1) {
        this.template1 = template1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocument != null ? idDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.idDocument == null && other.idDocument != null) || (this.idDocument != null && !this.idDocument.equals(other.idDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mail.Document[ idDocument=" + idDocument + " ]";
    }
    
}
