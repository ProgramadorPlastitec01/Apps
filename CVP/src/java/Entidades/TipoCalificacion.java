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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "tipo_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCalificacion.findAll", query = "SELECT t FROM TipoCalificacion t"),
    @NamedQuery(name = "TipoCalificacion.findByIdTipoCalificacion", query = "SELECT t FROM TipoCalificacion t WHERE t.idTipoCalificacion = :idTipoCalificacion"),
    @NamedQuery(name = "TipoCalificacion.findByNombre", query = "SELECT t FROM TipoCalificacion t WHERE t.nombre = :nombre")})
public class TipoCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_calificacion")
    private Integer idTipoCalificacion;
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    public TipoCalificacion() {
    }

    public TipoCalificacion(Integer idTipoCalificacion) {
        this.idTipoCalificacion = idTipoCalificacion;
    }

    public Integer getIdTipoCalificacion() {
        return idTipoCalificacion;
    }

    public void setIdTipoCalificacion(Integer idTipoCalificacion) {
        this.idTipoCalificacion = idTipoCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCalificacion != null ? idTipoCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCalificacion)) {
            return false;
        }
        TipoCalificacion other = (TipoCalificacion) object;
        if ((this.idTipoCalificacion == null && other.idTipoCalificacion != null) || (this.idTipoCalificacion != null && !this.idTipoCalificacion.equals(other.idTipoCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoCalificacion[ idTipoCalificacion=" + idTipoCalificacion + " ]";
    }
    
}
