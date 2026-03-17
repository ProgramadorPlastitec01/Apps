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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cabecera_etd_has_verificar_etd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraEtdHasVerificarEtd.findAll", query = "SELECT c FROM CabeceraEtdHasVerificarEtd c")
    , @NamedQuery(name = "CabeceraEtdHasVerificarEtd.findByIdcabeceraetdhasVerificaretd", query = "SELECT c FROM CabeceraEtdHasVerificarEtd c WHERE c.idcabeceraetdhasVerificaretd = :idcabeceraetdhasVerificaretd")})
public class CabeceraEtdHasVerificarEtd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcabecera_etd_has_Verificar_etd")
    private Integer idcabeceraetdhasVerificaretd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcabeceraetdhasVerificaretd")
    private Collection<CalificarEtd> calificarEtdCollection;
    @JoinColumn(name = "idCabecera_etd", referencedColumnName = "idCabecera_etd")
    @ManyToOne(optional = false)
    private CabeceraEtd idCabeceraetd;
    @JoinColumn(name = "idVerificar_etd", referencedColumnName = "idVerificar_etd")
    @ManyToOne(optional = false)
    private VerificarEtd idVerificaretd;

    public CabeceraEtdHasVerificarEtd() {
    }

    public CabeceraEtdHasVerificarEtd(Integer idcabeceraetdhasVerificaretd) {
        this.idcabeceraetdhasVerificaretd = idcabeceraetdhasVerificaretd;
    }

    public Integer getIdcabeceraetdhasVerificaretd() {
        return idcabeceraetdhasVerificaretd;
    }

    public void setIdcabeceraetdhasVerificaretd(Integer idcabeceraetdhasVerificaretd) {
        this.idcabeceraetdhasVerificaretd = idcabeceraetdhasVerificaretd;
    }

    @XmlTransient
    public Collection<CalificarEtd> getCalificarEtdCollection() {
        return calificarEtdCollection;
    }

    public void setCalificarEtdCollection(Collection<CalificarEtd> calificarEtdCollection) {
        this.calificarEtdCollection = calificarEtdCollection;
    }

    public CabeceraEtd getIdCabeceraetd() {
        return idCabeceraetd;
    }

    public void setIdCabeceraetd(CabeceraEtd idCabeceraetd) {
        this.idCabeceraetd = idCabeceraetd;
    }

    public VerificarEtd getIdVerificaretd() {
        return idVerificaretd;
    }

    public void setIdVerificaretd(VerificarEtd idVerificaretd) {
        this.idVerificaretd = idVerificaretd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcabeceraetdhasVerificaretd != null ? idcabeceraetdhasVerificaretd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraEtdHasVerificarEtd)) {
            return false;
        }
        CabeceraEtdHasVerificarEtd other = (CabeceraEtdHasVerificarEtd) object;
        if ((this.idcabeceraetdhasVerificaretd == null && other.idcabeceraetdhasVerificaretd != null) || (this.idcabeceraetdhasVerificaretd != null && !this.idcabeceraetdhasVerificaretd.equals(other.idcabeceraetdhasVerificaretd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CabeceraEtdHasVerificarEtd[ idcabeceraetdhasVerificaretd=" + idcabeceraetdhasVerificaretd + " ]";
    }
    
}
