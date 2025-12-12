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
@Table(name = "setting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s")
    , @NamedQuery(name = "Setting.findByIdSetting", query = "SELECT s FROM Setting s WHERE s.idSetting = :idSetting")
    , @NamedQuery(name = "Setting.findByCategorie", query = "SELECT s FROM Setting s WHERE s.categorie = :categorie")
    , @NamedQuery(name = "Setting.findByState", query = "SELECT s FROM Setting s WHERE s.state = :state")
    , @NamedQuery(name = "Setting.findByDateRegister", query = "SELECT s FROM Setting s WHERE s.dateRegister = :dateRegister")})
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_setting")
    private Integer idSetting;
    @Column(name = "categorie")
    private String categorie;
    @Lob
    @Column(name = "value")
    private String value;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "state")
    private Integer state;
    @Column(name = "date_register")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;

    public Setting() {
    }

    public Setting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    public Integer getIdSetting() {
        return idSetting;
    }

    public void setIdSetting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSetting != null ? idSetting.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setting)) {
            return false;
        }
        Setting other = (Setting) object;
        if ((this.idSetting == null && other.idSetting != null) || (this.idSetting != null && !this.idSetting.equals(other.idSetting))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Setting[ idSetting=" + idSetting + " ]";
    }
    
}
