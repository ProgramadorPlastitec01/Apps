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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "orden_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByIdOrdenTrabajo", query = "SELECT o FROM OrdenTrabajo o WHERE o.idOrdenTrabajo = :idOrdenTrabajo"),
    @NamedQuery(name = "OrdenTrabajo.findByNumero", query = "SELECT o FROM OrdenTrabajo o WHERE o.numero = :numero"),
    @NamedQuery(name = "OrdenTrabajo.findByTipoMtto", query = "SELECT o FROM OrdenTrabajo o WHERE o.tipoMtto = :tipoMtto"),
    @NamedQuery(name = "OrdenTrabajo.findByIdEquipo", query = "SELECT o FROM OrdenTrabajo o WHERE o.idEquipo = :idEquipo"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioProgramacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioProgramacion = :usuarioProgramacion"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaProgramacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaProgramacion = :fechaProgramacion"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioEjecucion", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioEjecucion = :usuarioEjecucion"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaEjecucion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioVerificacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioVerificacion = :usuarioVerificacion"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaVerificacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaVerificacion = :fechaVerificacion"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioReciboPrf", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioReciboPrf = :usuarioReciboPrf"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaReciboPrf", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaReciboPrf = :fechaReciboPrf"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioReciboGc", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioReciboGc = :usuarioReciboGc"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaReciboGc", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaReciboGc = :fechaReciboGc"),
    @NamedQuery(name = "OrdenTrabajo.findByTiempoEstimado", query = "SELECT o FROM OrdenTrabajo o WHERE o.tiempoEstimado = :tiempoEstimado"),
    @NamedQuery(name = "OrdenTrabajo.findByEstado", query = "SELECT o FROM OrdenTrabajo o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioRegistro", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaRegistro", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaRegistro = :fechaRegistro")})
public class OrdenTrabajo implements Serializable {

    @OneToMany(mappedBy = "idOrdenTrabajo")
    private Collection<OrdenTrabajoDetalle> ordenTrabajoDetalleCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_trabajo")
    private Integer idOrdenTrabajo;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "tipo_mtto")
    private Integer tipoMtto;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "usuario_programacion")
    private String usuarioProgramacion;
    @Column(name = "fecha_programacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    @Column(name = "usuario_ejecucion")
    private String usuarioEjecucion;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "usuario_verificacion")
    private String usuarioVerificacion;
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;
    @Column(name = "usuario_recibo_prf")
    private String usuarioReciboPrf;
    @Column(name = "fecha_recibo_prf")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReciboPrf;
    @Column(name = "usuario_recibo_gc")
    private String usuarioReciboGc;
    @Column(name = "fecha_recibo_gc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReciboGc;
    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getTipoMtto() {
        return tipoMtto;
    }

    public void setTipoMtto(Integer tipoMtto) {
        this.tipoMtto = tipoMtto;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getUsuarioProgramacion() {
        return usuarioProgramacion;
    }

    public void setUsuarioProgramacion(String usuarioProgramacion) {
        this.usuarioProgramacion = usuarioProgramacion;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public String getUsuarioEjecucion() {
        return usuarioEjecucion;
    }

    public void setUsuarioEjecucion(String usuarioEjecucion) {
        this.usuarioEjecucion = usuarioEjecucion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getUsuarioVerificacion() {
        return usuarioVerificacion;
    }

    public void setUsuarioVerificacion(String usuarioVerificacion) {
        this.usuarioVerificacion = usuarioVerificacion;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public String getUsuarioReciboPrf() {
        return usuarioReciboPrf;
    }

    public void setUsuarioReciboPrf(String usuarioReciboPrf) {
        this.usuarioReciboPrf = usuarioReciboPrf;
    }

    public Date getFechaReciboPrf() {
        return fechaReciboPrf;
    }

    public void setFechaReciboPrf(Date fechaReciboPrf) {
        this.fechaReciboPrf = fechaReciboPrf;
    }

    public String getUsuarioReciboGc() {
        return usuarioReciboGc;
    }

    public void setUsuarioReciboGc(String usuarioReciboGc) {
        this.usuarioReciboGc = usuarioReciboGc;
    }

    public Date getFechaReciboGc() {
        return fechaReciboGc;
    }

    public void setFechaReciboGc(Date fechaReciboGc) {
        this.fechaReciboGc = fechaReciboGc;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
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
        hash += (idOrdenTrabajo != null ? idOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo other = (OrdenTrabajo) object;
        if ((this.idOrdenTrabajo == null && other.idOrdenTrabajo != null) || (this.idOrdenTrabajo != null && !this.idOrdenTrabajo.equals(other.idOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OrdenTrabajo[ idOrdenTrabajo=" + idOrdenTrabajo + " ]";
    }

    @XmlTransient
    public Collection<OrdenTrabajoDetalle> getOrdenTrabajoDetalleCollection() {
        return ordenTrabajoDetalleCollection;
    }

    public void setOrdenTrabajoDetalleCollection(Collection<OrdenTrabajoDetalle> ordenTrabajoDetalleCollection) {
        this.ordenTrabajoDetalleCollection = ordenTrabajoDetalleCollection;
    }
    
}
