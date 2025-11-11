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
@Table(name = "ciiu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciiu.findAll", query = "SELECT c FROM Ciiu c")
    , @NamedQuery(name = "Ciiu.findByIdCiiu", query = "SELECT c FROM Ciiu c WHERE c.idCiiu = :idCiiu")
    , @NamedQuery(name = "Ciiu.findByCode", query = "SELECT c FROM Ciiu c WHERE c.code = :code")
    , @NamedQuery(name = "Ciiu.findByActivity", query = "SELECT c FROM Ciiu c WHERE c.activity = :activity")
    , @NamedQuery(name = "Ciiu.findByRiskLevel", query = "SELECT c FROM Ciiu c WHERE c.riskLevel = :riskLevel")
    , @NamedQuery(name = "Ciiu.findByState", query = "SELECT c FROM Ciiu c WHERE c.state = :state")
    , @NamedQuery(name = "Ciiu.findByDateRegister", query = "SELECT c FROM Ciiu c WHERE c.dateRegister = :dateRegister")})
public class Ciiu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCiiu")
    private Integer idCiiu;
    @Column(name = "Code")
    private Integer code;
    @Column(name = "Activity")
    private String activity;
    @Column(name = "RiskLevel")
    private Integer riskLevel;
    @Column(name = "State")
    private Integer state;
    @Column(name = "DateRegister")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
    @OneToMany(mappedBy = "ciiu")
    private Collection<Segmentation> segmentationCollection;
    @OneToMany(mappedBy = "ciiu1")
    private Collection<Segmentation> segmentationCollection1;

    public Ciiu() {
    }

    public Ciiu(Integer idCiiu) {
        this.idCiiu = idCiiu;
    }

    public Integer getIdCiiu() {
        return idCiiu;
    }

    public void setIdCiiu(Integer idCiiu) {
        this.idCiiu = idCiiu;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    @XmlTransient
    public Collection<Segmentation> getSegmentationCollection() {
        return segmentationCollection;
    }

    public void setSegmentationCollection(Collection<Segmentation> segmentationCollection) {
        this.segmentationCollection = segmentationCollection;
    }

    @XmlTransient
    public Collection<Segmentation> getSegmentationCollection1() {
        return segmentationCollection1;
    }

    public void setSegmentationCollection1(Collection<Segmentation> segmentationCollection1) {
        this.segmentationCollection1 = segmentationCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiiu != null ? idCiiu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciiu)) {
            return false;
        }
        Ciiu other = (Ciiu) object;
        if ((this.idCiiu == null && other.idCiiu != null) || (this.idCiiu != null && !this.idCiiu.equals(other.idCiiu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mail.Ciiu[ idCiiu=" + idCiiu + " ]";
    }
    
}
