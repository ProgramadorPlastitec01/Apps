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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Aprendiz.Sena1
 */
@Entity
@Table(name = "programacion_detalle")
@NamedQueries({
    @NamedQuery(name = "ProgramacionDetalle.findAll", query = "SELECT p FROM ProgramacionDetalle p"),
    @NamedQuery(name = "ProgramacionDetalle.findByIdProgramacion", query = "SELECT p FROM ProgramacionDetalle p WHERE p.idProgramacion = :idProgramacion"),
    @NamedQuery(name = "ProgramacionDetalle.findByConsecutivo", query = "SELECT p FROM ProgramacionDetalle p WHERE p.consecutivo = :consecutivo"),
    @NamedQuery(name = "ProgramacionDetalle.findByIdSolicitud", query = "SELECT p FROM ProgramacionDetalle p WHERE p.idSolicitud = :idSolicitud"),
    @NamedQuery(name = "ProgramacionDetalle.findByIdProveedor", query = "SELECT p FROM ProgramacionDetalle p WHERE p.idProveedor = :idProveedor"),
    @NamedQuery(name = "ProgramacionDetalle.findByEstado", query = "SELECT p FROM ProgramacionDetalle p WHERE p.estado = :estado"),
    @NamedQuery(name = "ProgramacionDetalle.findByFechaInicio", query = "SELECT p FROM ProgramacionDetalle p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ProgramacionDetalle.findByFechaFin", query = "SELECT p FROM ProgramacionDetalle p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "ProgramacionDetalle.findByFechaRegistro", query = "SELECT p FROM ProgramacionDetalle p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ProgramacionDetalle.findByUsuarioRegistro", query = "SELECT p FROM ProgramacionDetalle p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ProgramacionDetalle.findByIdUsuarioEntrega", query = "SELECT p FROM ProgramacionDetalle p WHERE p.idUsuarioEntrega = :idUsuarioEntrega"),
    @NamedQuery(name = "ProgramacionDetalle.findByFechaEntrega", query = "SELECT p FROM ProgramacionDetalle p WHERE p.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "ProgramacionDetalle.findByAdjuntoEntrega", query = "SELECT p FROM ProgramacionDetalle p WHERE p.adjuntoEntrega = :adjuntoEntrega"),
    @NamedQuery(name = "ProgramacionDetalle.findByClasificacionEntrega", query = "SELECT p FROM ProgramacionDetalle p WHERE p.clasificacionEntrega = :clasificacionEntrega"),
    @NamedQuery(name = "ProgramacionDetalle.findByIdUsuarioRecibe", query = "SELECT p FROM ProgramacionDetalle p WHERE p.idUsuarioRecibe = :idUsuarioRecibe"),
    @NamedQuery(name = "ProgramacionDetalle.findByFechaRecibe", query = "SELECT p FROM ProgramacionDetalle p WHERE p.fechaRecibe = :fechaRecibe"),
    @NamedQuery(name = "ProgramacionDetalle.findByAdjuntoRecibe", query = "SELECT p FROM ProgramacionDetalle p WHERE p.adjuntoRecibe = :adjuntoRecibe"),
    @NamedQuery(name = "ProgramacionDetalle.findByClasificacionRecibe", query = "SELECT p FROM ProgramacionDetalle p WHERE p.clasificacionRecibe = :clasificacionRecibe")})
public class ProgramacionDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_programacion")
    private Integer idProgramacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre_programacion")
    private String nombreProgramacion;
    @Basic(optional = false)
    @Column(name = "consecutivo")
    private int consecutivo;
    @Basic(optional = false)
    @Column(name = "id_solicitud")
    private int idSolicitud;
    @Basic(optional = false)
    @Column(name = "id_proveedor")
    private int idProveedor;
    @Basic(optional = false)
    @Lob
    @Column(name = "ubicacion_final")
    private String ubicacionFinal;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Lob
    @Column(name = "nota")
    private String nota;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "id_usuario_entrega")
    private int idUsuarioEntrega;
    @Basic(optional = false)
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Lob
    @Column(name = "descripcion_entrega")
    private String descripcionEntrega;
    @Column(name = "adjunto_entrega")
    private String adjuntoEntrega;
    @Column(name = "clasificacion_entrega")
    private String clasificacionEntrega;
    @Basic(optional = false)
    @Column(name = "id_usuario_recibe")
    private int idUsuarioRecibe;
    @Column(name = "fecha_recibe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibe;
    @Lob
    @Column(name = "descripcion_recibe")
    private String descripcionRecibe;
    @Column(name = "adjunto_recibe")
    private String adjuntoRecibe;
    @Column(name = "clasificacion_recibe")
    private String clasificacionRecibe;

    public ProgramacionDetalle() {
    }

    public ProgramacionDetalle(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public ProgramacionDetalle(Integer idProgramacion, String nombreProgramacion, int consecutivo, int idSolicitud, int idProveedor, String ubicacionFinal, int estado, Date fechaInicio, Date fechaFin, Date fechaRegistro, int idUsuarioEntrega, Date fechaEntrega, int idUsuarioRecibe) {
        this.idProgramacion = idProgramacion;
        this.nombreProgramacion = nombreProgramacion;
        this.consecutivo = consecutivo;
        this.idSolicitud = idSolicitud;
        this.idProveedor = idProveedor;
        this.ubicacionFinal = ubicacionFinal;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaRegistro = fechaRegistro;
        this.idUsuarioEntrega = idUsuarioEntrega;
        this.fechaEntrega = fechaEntrega;
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public String getNombreProgramacion() {
        return nombreProgramacion;
    }

    public void setNombreProgramacion(String nombreProgramacion) {
        this.nombreProgramacion = nombreProgramacion;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getUbicacionFinal() {
        return ubicacionFinal;
    }

    public void setUbicacionFinal(String ubicacionFinal) {
        this.ubicacionFinal = ubicacionFinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public int getIdUsuarioEntrega() {
        return idUsuarioEntrega;
    }

    public void setIdUsuarioEntrega(int idUsuarioEntrega) {
        this.idUsuarioEntrega = idUsuarioEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcionEntrega() {
        return descripcionEntrega;
    }

    public void setDescripcionEntrega(String descripcionEntrega) {
        this.descripcionEntrega = descripcionEntrega;
    }

    public String getAdjuntoEntrega() {
        return adjuntoEntrega;
    }

    public void setAdjuntoEntrega(String adjuntoEntrega) {
        this.adjuntoEntrega = adjuntoEntrega;
    }

    public String getClasificacionEntrega() {
        return clasificacionEntrega;
    }

    public void setClasificacionEntrega(String clasificacionEntrega) {
        this.clasificacionEntrega = clasificacionEntrega;
    }

    public int getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(int idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public Date getFechaRecibe() {
        return fechaRecibe;
    }

    public void setFechaRecibe(Date fechaRecibe) {
        this.fechaRecibe = fechaRecibe;
    }

    public String getDescripcionRecibe() {
        return descripcionRecibe;
    }

    public void setDescripcionRecibe(String descripcionRecibe) {
        this.descripcionRecibe = descripcionRecibe;
    }

    public String getAdjuntoRecibe() {
        return adjuntoRecibe;
    }

    public void setAdjuntoRecibe(String adjuntoRecibe) {
        this.adjuntoRecibe = adjuntoRecibe;
    }

    public String getClasificacionRecibe() {
        return clasificacionRecibe;
    }

    public void setClasificacionRecibe(String clasificacionRecibe) {
        this.clasificacionRecibe = clasificacionRecibe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramacion != null ? idProgramacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionDetalle)) {
            return false;
        }
        ProgramacionDetalle other = (ProgramacionDetalle) object;
        if ((this.idProgramacion == null && other.idProgramacion != null) || (this.idProgramacion != null && !this.idProgramacion.equals(other.idProgramacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ProgramacionDetalle[idProgramacion=" + idProgramacion + "]";
    }

}
