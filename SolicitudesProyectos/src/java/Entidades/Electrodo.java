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
@Table(name = "electrodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Electrodo.findAll", query = "SELECT e FROM Electrodo e")
    , @NamedQuery(name = "Electrodo.findByIdElectrodo", query = "SELECT e FROM Electrodo e WHERE e.idElectrodo = :idElectrodo")
    , @NamedQuery(name = "Electrodo.findByNumeroElectrodo", query = "SELECT e FROM Electrodo e WHERE e.numeroElectrodo = :numeroElectrodo")
    , @NamedQuery(name = "Electrodo.findByLinea", query = "SELECT e FROM Electrodo e WHERE e.linea = :linea")
    , @NamedQuery(name = "Electrodo.findByEstado", query = "SELECT e FROM Electrodo e WHERE e.estado = :estado")})
public class Electrodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idElectrodo")
    private Integer idElectrodo;
    @Basic(optional = false)
    @Column(name = "Numero_Electrodo")
    private String numeroElectrodo;
    @Basic(optional = false)
    @Column(name = "Linea")
    private String linea;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idElectrodo")
    private Collection<CabeceraEtd> cabeceraEtdCollection;
    @JoinColumn(name = "id_plano", referencedColumnName = "id_plano")
    @ManyToOne(optional = false)
    private Plano idPlano;

    public Electrodo() {
    }

    public Electrodo(Integer idElectrodo) {
        this.idElectrodo = idElectrodo;
    }

    public Electrodo(Integer idElectrodo, String numeroElectrodo, String linea, String estado) {
        this.idElectrodo = idElectrodo;
        this.numeroElectrodo = numeroElectrodo;
        this.linea = linea;
        this.estado = estado;
    }

    public Integer getIdElectrodo() {
        return idElectrodo;
    }

    public void setIdElectrodo(Integer idElectrodo) {
        this.idElectrodo = idElectrodo;
    }

    public String getNumeroElectrodo() {
        return numeroElectrodo;
    }

    public void setNumeroElectrodo(String numeroElectrodo) {
        this.numeroElectrodo = numeroElectrodo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<CabeceraEtd> getCabeceraEtdCollection() {
        return cabeceraEtdCollection;
    }

    public void setCabeceraEtdCollection(Collection<CabeceraEtd> cabeceraEtdCollection) {
        this.cabeceraEtdCollection = cabeceraEtdCollection;
    }

    public Plano getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Plano idPlano) {
        this.idPlano = idPlano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idElectrodo != null ? idElectrodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Electrodo)) {
            return false;
        }
        Electrodo other = (Electrodo) object;
        if ((this.idElectrodo == null && other.idElectrodo != null) || (this.idElectrodo != null && !this.idElectrodo.equals(other.idElectrodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Electrodo[ idElectrodo=" + idElectrodo + " ]";
    }
    
}
