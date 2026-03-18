/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "verificar_etd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VerificarEtd.findAll", query = "SELECT v FROM VerificarEtd v")
    , @NamedQuery(name = "VerificarEtd.findByIdVerificaretd", query = "SELECT v FROM VerificarEtd v WHERE v.idVerificaretd = :idVerificaretd")})
public class VerificarEtd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVerificar_etd")
    private Integer idVerificaretd;
    @Basic(optional = false)
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Lob
    @Column(name = "Medida_Standard")
    private String medidaStandard;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVerificar")
    private Collection<Calificar> calificarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVerificaretd")
    private Collection<CabeceraEtdHasVerificarEtd> cabeceraEtdHasVerificarEtdCollection;

    public VerificarEtd() {
    }

    public VerificarEtd(Integer idVerificaretd) {
        this.idVerificaretd = idVerificaretd;
    }

    public VerificarEtd(Integer idVerificaretd, String descripcion, String medidaStandard) {
        this.idVerificaretd = idVerificaretd;
        this.descripcion = descripcion;
        this.medidaStandard = medidaStandard;
    }

    public Integer getIdVerificaretd() {
        return idVerificaretd;
    }

    public void setIdVerificaretd(Integer idVerificaretd) {
        this.idVerificaretd = idVerificaretd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedidaStandard() {
        return medidaStandard;
    }

    public void setMedidaStandard(String medidaStandard) {
        this.medidaStandard = medidaStandard;
    }

    @XmlTransient
    public Collection<Calificar> getCalificarCollection() {
        return calificarCollection;
    }

    public void setCalificarCollection(Collection<Calificar> calificarCollection) {
        this.calificarCollection = calificarCollection;
    }

    @XmlTransient
    public Collection<CabeceraEtdHasVerificarEtd> getCabeceraEtdHasVerificarEtdCollection() {
        return cabeceraEtdHasVerificarEtdCollection;
    }

    public void setCabeceraEtdHasVerificarEtdCollection(Collection<CabeceraEtdHasVerificarEtd> cabeceraEtdHasVerificarEtdCollection) {
        this.cabeceraEtdHasVerificarEtdCollection = cabeceraEtdHasVerificarEtdCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVerificaretd != null ? idVerificaretd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificarEtd)) {
            return false;
        }
        VerificarEtd other = (VerificarEtd) object;
        if ((this.idVerificaretd == null && other.idVerificaretd != null) || (this.idVerificaretd != null && !this.idVerificaretd.equals(other.idVerificaretd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VerificarEtd[ idVerificaretd=" + idVerificaretd + " ]";
    }
    
}
