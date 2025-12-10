/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "actividad_orden")
@NamedQueries({
    @NamedQuery(name = "ActividadOrden.findAll", query = "SELECT a FROM ActividadOrden a"),
    @NamedQuery(name = "ActividadOrden.findByIdActividadOrden", query = "SELECT a FROM ActividadOrden a WHERE a.idActividadOrden = :idActividadOrden"),
    @NamedQuery(name = "ActividadOrden.findByTiempo", query = "SELECT a FROM ActividadOrden a WHERE a.tiempo = :tiempo"),
    @NamedQuery(name = "ActividadOrden.findByEstado", query = "SELECT a FROM ActividadOrden a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActividadOrden.findByUsuarioRegistro", query = "SELECT a FROM ActividadOrden a WHERE a.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ActividadOrden.findByFechaRegistro", query = "SELECT a FROM ActividadOrden a WHERE a.fechaRegistro = :fechaRegistro")})
public class ActividadOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_orden")
    private Integer idActividadOrden;
    @Column(name = "tiempo")
    private Double tiempo;
    @Column(name = "estado")
    private Short estado;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne
    private OrdenTrabajo ordenTrabajo;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad")
    @ManyToOne
    private Actividad actividad;

    public ActividadOrden() {
    }

    public ActividadOrden(Integer idActividadOrden) {
        this.idActividadOrden = idActividadOrden;
    }

    public Integer getIdActividadOrden() {
        return idActividadOrden;
    }

    public void setIdActividadOrden(Integer idActividadOrden) {
        this.idActividadOrden = idActividadOrden;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadOrden != null ? idActividadOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadOrden)) {
            return false;
        }
        ActividadOrden other = (ActividadOrden) object;
        if ((this.idActividadOrden == null && other.idActividadOrden != null) || (this.idActividadOrden != null && !this.idActividadOrden.equals(other.idActividadOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ActividadOrden[idActividadOrden=" + idActividadOrden + "]";
    }

}
