/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "herramienta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Herramienta.findAll", query = "SELECT h FROM Herramienta h")
    , @NamedQuery(name = "Herramienta.findByIdHerramienta", query = "SELECT h FROM Herramienta h WHERE h.idHerramienta = :idHerramienta")
    , @NamedQuery(name = "Herramienta.findByFechaRegistro", query = "SELECT h FROM Herramienta h WHERE h.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Herramienta.findByNombre", query = "SELECT h FROM Herramienta h WHERE h.nombre = :nombre")
    , @NamedQuery(name = "Herramienta.findByEstado", query = "SELECT h FROM Herramienta h WHERE h.estado = :estado")})
public class Herramienta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_herramienta")
    private Integer idHerramienta;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Integer estado;

    public Herramienta() {
    }

    public Herramienta(Integer idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public Integer getIdHerramienta() {
        return idHerramienta;
    }

    public void setIdHerramienta(Integer idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHerramienta != null ? idHerramienta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Herramienta)) {
            return false;
        }
        Herramienta other = (Herramienta) object;
        if ((this.idHerramienta == null && other.idHerramienta != null) || (this.idHerramienta != null && !this.idHerramienta.equals(other.idHerramienta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Herramienta[ idHerramienta=" + idHerramienta + " ]";
    }
    
}
