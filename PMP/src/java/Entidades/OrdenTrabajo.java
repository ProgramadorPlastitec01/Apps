/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "orden_trabajo")
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByIdOrdenTrabajo", query = "SELECT o FROM OrdenTrabajo o WHERE o.idOrdenTrabajo = :idOrdenTrabajo"),
    @NamedQuery(name = "OrdenTrabajo.findByNumeroOrden", query = "SELECT o FROM OrdenTrabajo o WHERE o.numeroOrden = :numeroOrden"),
    @NamedQuery(name = "OrdenTrabajo.findByHorometroMtto", query = "SELECT o FROM OrdenTrabajo o WHERE o.horometroMtto = :horometroMtto"),
    @NamedQuery(name = "OrdenTrabajo.findByTiempoEstimado", query = "SELECT o FROM OrdenTrabajo o WHERE o.tiempoEstimado = :tiempoEstimado"),
    @NamedQuery(name = "OrdenTrabajo.findByProgramadoPor", query = "SELECT o FROM OrdenTrabajo o WHERE o.programadoPor = :programadoPor"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaProgramado", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaProgramado = :fechaProgramado"),
    @NamedQuery(name = "OrdenTrabajo.findByEjecutadoPor", query = "SELECT o FROM OrdenTrabajo o WHERE o.ejecutadoPor = :ejecutadoPor"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaEjecutado", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaEjecutado = :fechaEjecutado"),
    @NamedQuery(name = "OrdenTrabajo.findByRevisadoPor", query = "SELECT o FROM OrdenTrabajo o WHERE o.revisadoPor = :revisadoPor"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaRevisado", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaRevisado = :fechaRevisado"),
    @NamedQuery(name = "OrdenTrabajo.findByParoProduccion", query = "SELECT o FROM OrdenTrabajo o WHERE o.paroProduccion = :paroProduccion"),
    @NamedQuery(name = "OrdenTrabajo.findByEstado", query = "SELECT o FROM OrdenTrabajo o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenTrabajo.findByProgramacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.programacion = :programacion"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuarioRegistro", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaRegistro", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaRegistro = :fechaRegistro")})
public class OrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_trabajo")
    private Integer idOrdenTrabajo;
    @Basic(optional = false)
    @Column(name = "numero_orden")
    private int numeroOrden;
    @Basic(optional = false)
    @Column(name = "horometro_mtto")
    private int horometroMtto;
    @Basic(optional = false)
    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;
    @Basic(optional = false)
    @Column(name = "programado_por")
    private String programadoPor;
    @Basic(optional = false)
    @Column(name = "fecha_programado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramado;
    @Basic(optional = false)
    @Column(name = "ejecutado_por")
    private String ejecutadoPor;
    @Basic(optional = false)
    @Column(name = "fecha_ejecutado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecutado;
    @Basic(optional = false)
    @Column(name = "revisado_por")
    private String revisadoPor;
    @Basic(optional = false)
    @Column(name = "fecha_revisado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevisado;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "paro_produccion")
    private String paroProduccion;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @Column(name = "programacion")
    private short programacion;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false)
    private Equipo equipo;
    @OneToMany(mappedBy = "ordenTrabajo")
    private Collection<NovedadOrden> novedadOrdenCollection;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public OrdenTrabajo(Integer idOrdenTrabajo, int numeroOrden, int horometroMtto, String tiempoEstimado, String programadoPor, Date fechaProgramado, String ejecutadoPor, Date fechaEjecutado, String revisadoPor, Date fechaRevisado, String observaciones, String paroProduccion, short estado, short programacion, String usuarioRegistro, Date fechaRegistro) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.numeroOrden = numeroOrden;
        this.horometroMtto = horometroMtto;
        this.tiempoEstimado = tiempoEstimado;
        this.programadoPor = programadoPor;
        this.fechaProgramado = fechaProgramado;
        this.ejecutadoPor = ejecutadoPor;
        this.fechaEjecutado = fechaEjecutado;
        this.revisadoPor = revisadoPor;
        this.fechaRevisado = fechaRevisado;
        this.observaciones = observaciones;
        this.paroProduccion = paroProduccion;
        this.estado = estado;
        this.programacion = programacion;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public int getHorometroMtto() {
        return horometroMtto;
    }

    public void setHorometroMtto(int horometroMtto) {
        this.horometroMtto = horometroMtto;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getProgramadoPor() {
        return programadoPor;
    }

    public void setProgramadoPor(String programadoPor) {
        this.programadoPor = programadoPor;
    }

    public Date getFechaProgramado() {
        return fechaProgramado;
    }

    public void setFechaProgramado(Date fechaProgramado) {
        this.fechaProgramado = fechaProgramado;
    }

    public String getEjecutadoPor() {
        return ejecutadoPor;
    }

    public void setEjecutadoPor(String ejecutadoPor) {
        this.ejecutadoPor = ejecutadoPor;
    }

    public Date getFechaEjecutado() {
        return fechaEjecutado;
    }

    public void setFechaEjecutado(Date fechaEjecutado) {
        this.fechaEjecutado = fechaEjecutado;
    }

    public String getRevisadoPor() {
        return revisadoPor;
    }

    public void setRevisadoPor(String revisadoPor) {
        this.revisadoPor = revisadoPor;
    }

    public Date getFechaRevisado() {
        return fechaRevisado;
    }

    public void setFechaRevisado(Date fechaRevisado) {
        this.fechaRevisado = fechaRevisado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getParoProduccion() {
        return paroProduccion;
    }

    public void setParoProduccion(String paroProduccion) {
        this.paroProduccion = paroProduccion;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public short getProgramacion() {
        return programacion;
    }

    public void setProgramacion(short programacion) {
        this.programacion = programacion;
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

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Collection<NovedadOrden> getNovedadOrdenCollection() {
        return novedadOrdenCollection;
    }

    public void setNovedadOrdenCollection(Collection<NovedadOrden> novedadOrdenCollection) {
        this.novedadOrdenCollection = novedadOrdenCollection;
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
        return "Entidades.OrdenTrabajo[idOrdenTrabajo=" + idOrdenTrabajo + "]";
    }

}
