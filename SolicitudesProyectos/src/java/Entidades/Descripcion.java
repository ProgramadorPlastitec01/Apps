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
@Table(name = "descripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descripcion.findAll", query = "SELECT d FROM Descripcion d")
    , @NamedQuery(name = "Descripcion.findByIdDescripcion", query = "SELECT d FROM Descripcion d WHERE d.idDescripcion = :idDescripcion")
    , @NamedQuery(name = "Descripcion.findByFechaRegistro", query = "SELECT d FROM Descripcion d WHERE d.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Descripcion.findByDescripcion", query = "SELECT d FROM Descripcion d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "Descripcion.findByEstado", query = "SELECT d FROM Descripcion d WHERE d.estado = :estado")})
public class Descripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_descripcion")
    private Integer idDescripcion;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private Integer estado;

    public Descripcion() {
    }

    public Descripcion(Integer idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    public Descripcion(Integer idDescripcion, Date fechaRegistro, String descripcion) {
        this.idDescripcion = idDescripcion;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
    }

    public Integer getIdDescripcion() {
        return idDescripcion;
    }

    public void setIdDescripcion(Integer idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idDescripcion != null ? idDescripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descripcion)) {
            return false;
        }
        Descripcion other = (Descripcion) object;
        if ((this.idDescripcion == null && other.idDescripcion != null) || (this.idDescripcion != null && !this.idDescripcion.equals(other.idDescripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Descripcion[ idDescripcion=" + idDescripcion + " ]";
    }
    
}
