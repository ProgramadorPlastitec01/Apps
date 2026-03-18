/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "cabecera_etd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraEtd.findAll", query = "SELECT c FROM CabeceraEtd c")
    , @NamedQuery(name = "CabeceraEtd.findByIdCabeceraetd", query = "SELECT c FROM CabeceraEtd c WHERE c.idCabeceraetd = :idCabeceraetd")
    , @NamedQuery(name = "CabeceraEtd.findByFecha", query = "SELECT c FROM CabeceraEtd c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "CabeceraEtd.findBySolicitudes", query = "SELECT c FROM CabeceraEtd c WHERE c.solicitudes = :solicitudes")
    , @NamedQuery(name = "CabeceraEtd.findByVerificadoPor", query = "SELECT c FROM CabeceraEtd c WHERE c.verificadoPor = :verificadoPor")})
public class CabeceraEtd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCabecera_etd")
    private Integer idCabeceraetd;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Solicitudes")
    private String solicitudes;
    @Basic(optional = false)
    @Column(name = "Verificado_Por")
    private String verificadoPor;
    @JoinColumn(name = "idElectrodo", referencedColumnName = "idElectrodo")
    @ManyToOne(optional = false)
    private Electrodo idElectrodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCabeceraetd")
    private Collection<CabeceraEtdHasVerificarEtd> cabeceraEtdHasVerificarEtdCollection;

    public CabeceraEtd() {
    }

    public CabeceraEtd(Integer idCabeceraetd) {
        this.idCabeceraetd = idCabeceraetd;
    }

    public CabeceraEtd(Integer idCabeceraetd, Date fecha, String solicitudes, String verificadoPor) {
        this.idCabeceraetd = idCabeceraetd;
        this.fecha = fecha;
        this.solicitudes = solicitudes;
        this.verificadoPor = verificadoPor;
    }

    public Integer getIdCabeceraetd() {
        return idCabeceraetd;
    }

    public void setIdCabeceraetd(Integer idCabeceraetd) {
        this.idCabeceraetd = idCabeceraetd;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(String solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String getVerificadoPor() {
        return verificadoPor;
    }

    public void setVerificadoPor(String verificadoPor) {
        this.verificadoPor = verificadoPor;
    }

    public Electrodo getIdElectrodo() {
        return idElectrodo;
    }

    public void setIdElectrodo(Electrodo idElectrodo) {
        this.idElectrodo = idElectrodo;
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
        hash += (idCabeceraetd != null ? idCabeceraetd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraEtd)) {
            return false;
        }
        CabeceraEtd other = (CabeceraEtd) object;
        if ((this.idCabeceraetd == null && other.idCabeceraetd != null) || (this.idCabeceraetd != null && !this.idCabeceraetd.equals(other.idCabeceraetd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CabeceraEtd[ idCabeceraetd=" + idCabeceraetd + " ]";
    }
    
}
