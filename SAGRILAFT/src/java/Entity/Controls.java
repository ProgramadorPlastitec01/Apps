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
@Table(name = "controls")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Controls.findAll", query = "SELECT c FROM Controls c")
    , @NamedQuery(name = "Controls.findByIdControl", query = "SELECT c FROM Controls c WHERE c.idControl = :idControl")
    , @NamedQuery(name = "Controls.findByCase1", query = "SELECT c FROM Controls c WHERE c.case1 = :case1")
    , @NamedQuery(name = "Controls.findByEvent", query = "SELECT c FROM Controls c WHERE c.event = :event")
    , @NamedQuery(name = "Controls.findByUserRegister", query = "SELECT c FROM Controls c WHERE c.userRegister = :userRegister")
    , @NamedQuery(name = "Controls.findByDateRegister", query = "SELECT c FROM Controls c WHERE c.dateRegister = :dateRegister")})
public class Controls implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdControl")
    private Integer idControl;
    @Column(name = "Case")
    private String case1;
    @Lob
    @Column(name = "Description")
    private String description;
    @Column(name = "Event")
    private String event;
    @Column(name = "UserRegister")
    private String userRegister;
    @Column(name = "DateRegister")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
    @JoinColumn(name = "IdSegmentation", referencedColumnName = "IdSegmentation")
    @ManyToOne
    private Segmentation segmentation;

    public Controls() {
    }

    public Controls(Integer idControl) {
        this.idControl = idControl;
    }

    public Integer getIdControl() {
        return idControl;
    }

    public void setIdControl(Integer idControl) {
        this.idControl = idControl;
    }

    public String getCase1() {
        return case1;
    }

    public void setCase1(String case1) {
        this.case1 = case1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(String userRegister) {
        this.userRegister = userRegister;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControl != null ? idControl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controls)) {
            return false;
        }
        Controls other = (Controls) object;
        if ((this.idControl == null && other.idControl != null) || (this.idControl != null && !this.idControl.equals(other.idControl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mail.Controls[ idControl=" + idControl + " ]";
    }
    
}
