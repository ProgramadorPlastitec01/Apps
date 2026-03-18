/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "calificar_etd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalificarEtd.findAll", query = "SELECT c FROM CalificarEtd c")
    , @NamedQuery(name = "CalificarEtd.findByIdcalificarEtd", query = "SELECT c FROM CalificarEtd c WHERE c.idcalificarEtd = :idcalificarEtd")
    , @NamedQuery(name = "CalificarEtd.findByCumple", query = "SELECT c FROM CalificarEtd c WHERE c.cumple = :cumple")
    , @NamedQuery(name = "CalificarEtd.findByAplica", query = "SELECT c FROM CalificarEtd c WHERE c.aplica = :aplica")
    , @NamedQuery(name = "CalificarEtd.findByObservaciones", query = "SELECT c FROM CalificarEtd c WHERE c.observaciones = :observaciones")})
public class CalificarEtd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcalificar_etd")
    private Integer idcalificarEtd;
    @Basic(optional = false)
    @Column(name = "Cumple")
    private String cumple;
    @Column(name = "Aplica")
    private String aplica;
    @Column(name = "Observaciones")
    private String observaciones;
    @JoinColumn(name = "idcabecera_etd_has_Verificar_etd", referencedColumnName = "idcabecera_etd_has_Verificar_etd")
    @ManyToOne(optional = false)
    private CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretd;

    public CalificarEtd() {
    }

    public CalificarEtd(Integer idcalificarEtd) {
        this.idcalificarEtd = idcalificarEtd;
    }

    public CalificarEtd(Integer idcalificarEtd, String cumple) {
        this.idcalificarEtd = idcalificarEtd;
        this.cumple = cumple;
    }

    public Integer getIdcalificarEtd() {
        return idcalificarEtd;
    }

    public void setIdcalificarEtd(Integer idcalificarEtd) {
        this.idcalificarEtd = idcalificarEtd;
    }

    public String getCumple() {
        return cumple;
    }

    public void setCumple(String cumple) {
        this.cumple = cumple;
    }

    public String getAplica() {
        return aplica;
    }

    public void setAplica(String aplica) {
        this.aplica = aplica;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public CabeceraEtdHasVerificarEtd getIdcabeceraetdhasVerificaretd() {
        return idcabeceraetdhasVerificaretd;
    }

    public void setIdcabeceraetdhasVerificaretd(CabeceraEtdHasVerificarEtd idcabeceraetdhasVerificaretd) {
        this.idcabeceraetdhasVerificaretd = idcabeceraetdhasVerificaretd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcalificarEtd != null ? idcalificarEtd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalificarEtd)) {
            return false;
        }
        CalificarEtd other = (CalificarEtd) object;
        if ((this.idcalificarEtd == null && other.idcalificarEtd != null) || (this.idcalificarEtd != null && !this.idcalificarEtd.equals(other.idcalificarEtd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CalificarEtd[ idcalificarEtd=" + idcalificarEtd + " ]";
    }
    
}
