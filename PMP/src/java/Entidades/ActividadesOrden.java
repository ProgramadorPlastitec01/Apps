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
 * @author asistemas2
 */
@Entity
@Table(name = "actividades_orden")
@NamedQueries({
    @NamedQuery(name = "ActividadesOrden.findAll", query = "SELECT a FROM ActividadesOrden a"),
    @NamedQuery(name = "ActividadesOrden.findByIdAcvidadesOrden", query = "SELECT a FROM ActividadesOrden a WHERE a.idAcvidadesOrden = :idAcvidadesOrden"),
    @NamedQuery(name = "ActividadesOrden.findByTiempo", query = "SELECT a FROM ActividadesOrden a WHERE a.tiempo = :tiempo"),
    @NamedQuery(name = "ActividadesOrden.findByEstado", query = "SELECT a FROM ActividadesOrden a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActividadesOrden.findByUsuarioRegistro", query = "SELECT a FROM ActividadesOrden a WHERE a.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ActividadesOrden.findByFechaRegistro", query = "SELECT a FROM ActividadesOrden a WHERE a.fechaRegistro = :fechaRegistro")})
public class ActividadesOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acvidades_orden")
    private Integer idAcvidadesOrden;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "tiempo")
    private double tiempo;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne(optional = false)
    private OrdenTrabajo ordenTrabajo;
    @JoinColumn(name = "id_actividades", referencedColumnName = "id_actividad")
    @ManyToOne(optional = false)
    private Actividad actividad;

    public ActividadesOrden() {
    }

    public ActividadesOrden(Integer idAcvidadesOrden) {
        this.idAcvidadesOrden = idAcvidadesOrden;
    }

    public ActividadesOrden(Integer idAcvidadesOrden, String observaciones, double tiempo, short estado, String usuarioRegistro, Date fechaRegistro) {
        this.idAcvidadesOrden = idAcvidadesOrden;
        this.observaciones = observaciones;
        this.tiempo = tiempo;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdAcvidadesOrden() {
        return idAcvidadesOrden;
    }

    public void setIdAcvidadesOrden(Integer idAcvidadesOrden) {
        this.idAcvidadesOrden = idAcvidadesOrden;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
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
        hash += (idAcvidadesOrden != null ? idAcvidadesOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadesOrden)) {
            return false;
        }
        ActividadesOrden other = (ActividadesOrden) object;
        if ((this.idAcvidadesOrden == null && other.idAcvidadesOrden != null) || (this.idAcvidadesOrden != null && !this.idAcvidadesOrden.equals(other.idAcvidadesOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ActividadesOrden[idAcvidadesOrden=" + idAcvidadesOrden + "]";
    }

}
