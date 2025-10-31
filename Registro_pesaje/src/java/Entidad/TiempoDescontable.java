/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

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
@Table(name = "tiempo_descontable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiempoDescontable.findAll", query = "SELECT t FROM TiempoDescontable t")
    , @NamedQuery(name = "TiempoDescontable.findByIdtiempoDescontable", query = "SELECT t FROM TiempoDescontable t WHERE t.idtiempoDescontable = :idtiempoDescontable")
    , @NamedQuery(name = "TiempoDescontable.findByTiempoDescontavble", query = "SELECT t FROM TiempoDescontable t WHERE t.tiempoDescontavble = :tiempoDescontavble")
    , @NamedQuery(name = "TiempoDescontable.findByCantidadtiempoDescontable", query = "SELECT t FROM TiempoDescontable t WHERE t.cantidadtiempoDescontable = :cantidadtiempoDescontable")
    , @NamedQuery(name = "TiempoDescontable.findByEstado", query = "SELECT t FROM TiempoDescontable t WHERE t.estado = :estado")
    , @NamedQuery(name = "TiempoDescontable.findByUsuarioRegistro", query = "SELECT t FROM TiempoDescontable t WHERE t.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "TiempoDescontable.findByFechaRegistro", query = "SELECT t FROM TiempoDescontable t WHERE t.fechaRegistro = :fechaRegistro")})
public class TiempoDescontable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tiempoDescontable")
    private Integer idtiempoDescontable;
    @Column(name = "tiempoDescontavble")
    private String tiempoDescontavble;
    @Column(name = "cantidad_tiempoDescontable")
    private String cantidadtiempoDescontable;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "Estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public TiempoDescontable() {
    }

    public TiempoDescontable(Integer idtiempoDescontable) {
        this.idtiempoDescontable = idtiempoDescontable;
    }

    public Integer getIdtiempoDescontable() {
        return idtiempoDescontable;
    }

    public void setIdtiempoDescontable(Integer idtiempoDescontable) {
        this.idtiempoDescontable = idtiempoDescontable;
    }

    public String getTiempoDescontavble() {
        return tiempoDescontavble;
    }

    public void setTiempoDescontavble(String tiempoDescontavble) {
        this.tiempoDescontavble = tiempoDescontavble;
    }

    public String getCantidadtiempoDescontable() {
        return cantidadtiempoDescontable;
    }

    public void setCantidadtiempoDescontable(String cantidadtiempoDescontable) {
        this.cantidadtiempoDescontable = cantidadtiempoDescontable;
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

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiempoDescontable != null ? idtiempoDescontable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiempoDescontable)) {
            return false;
        }
        TiempoDescontable other = (TiempoDescontable) object;
        if ((this.idtiempoDescontable == null && other.idtiempoDescontable != null) || (this.idtiempoDescontable != null && !this.idtiempoDescontable.equals(other.idtiempoDescontable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.TiempoDescontable[ idtiempoDescontable=" + idtiempoDescontable + " ]";
    }
    
}
