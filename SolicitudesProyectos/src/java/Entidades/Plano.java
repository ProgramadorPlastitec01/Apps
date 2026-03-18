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
@Table(name = "plano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plano.findAll", query = "SELECT p FROM Plano p")
    , @NamedQuery(name = "Plano.findByIdPlano", query = "SELECT p FROM Plano p WHERE p.idPlano = :idPlano")
    , @NamedQuery(name = "Plano.findByNombrePlano", query = "SELECT p FROM Plano p WHERE p.nombrePlano = :nombrePlano")
    , @NamedQuery(name = "Plano.findByTipo", query = "SELECT p FROM Plano p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Plano.findByFechaIngreso", query = "SELECT p FROM Plano p WHERE p.fechaIngreso = :fechaIngreso")})
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plano")
    private Integer idPlano;
    @Basic(optional = false)
    @Column(name = "nombre_plano")
    private String nombrePlano;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlano")
    private Collection<Electrodo> electrodoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlano")
    private Collection<Calificar> calificarCollection;

    public Plano() {
    }

    public Plano(Integer idPlano) {
        this.idPlano = idPlano;
    }

    public Plano(Integer idPlano, String nombrePlano, String tipo, Date fechaIngreso) {
        this.idPlano = idPlano;
        this.nombrePlano = nombrePlano;
        this.tipo = tipo;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Integer idPlano) {
        this.idPlano = idPlano;
    }

    public String getNombrePlano() {
        return nombrePlano;
    }

    public void setNombrePlano(String nombrePlano) {
        this.nombrePlano = nombrePlano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @XmlTransient
    public Collection<Electrodo> getElectrodoCollection() {
        return electrodoCollection;
    }

    public void setElectrodoCollection(Collection<Electrodo> electrodoCollection) {
        this.electrodoCollection = electrodoCollection;
    }

    @XmlTransient
    public Collection<Calificar> getCalificarCollection() {
        return calificarCollection;
    }

    public void setCalificarCollection(Collection<Calificar> calificarCollection) {
        this.calificarCollection = calificarCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlano != null ? idPlano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plano)) {
            return false;
        }
        Plano other = (Plano) object;
        if ((this.idPlano == null && other.idPlano != null) || (this.idPlano != null && !this.idPlano.equals(other.idPlano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Plano[ idPlano=" + idPlano + " ]";
    }
    
}
