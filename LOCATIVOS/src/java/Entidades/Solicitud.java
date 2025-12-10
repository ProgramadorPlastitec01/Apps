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
 * @author Aprendiz.Sena1
 */
@Entity
@Table(name = "solicitud")
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findByIdSolicitudes", query = "SELECT s FROM Solicitud s WHERE s.idSolicitudes = :idSolicitudes"),
    @NamedQuery(name = "Solicitud.findByEstado", query = "SELECT s FROM Solicitud s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitud.findByFechaRegistro", query = "SELECT s FROM Solicitud s WHERE s.fechaRegistro = :fechaRegistro")})
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_solicitudes")
    private Integer idSolicitudes;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion_solicitud")
    private String descripcionSolicitud;
    @Basic(optional = false)
    @Lob
    @Column(name = "clasificacion_solicitud")
    private String clasificacionSolicitud;
    @Basic(optional = false)
    @Lob
    @Column(name = "adjunto_solicitud")
    private String adjuntoSolicitud;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_usuario_solicitud", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;

    public Solicitud() {
    }

    public Solicitud(Integer idSolicitudes) {
        this.idSolicitudes = idSolicitudes;
    }

    public Solicitud(Integer idSolicitudes, String descripcionSolicitud, String clasificacionSolicitud, String adjuntoSolicitud, int estado, Date fechaRegistro) {
        this.idSolicitudes = idSolicitudes;
        this.descripcionSolicitud = descripcionSolicitud;
        this.clasificacionSolicitud = clasificacionSolicitud;
        this.adjuntoSolicitud = adjuntoSolicitud;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdSolicitudes() {
        return idSolicitudes;
    }

    public void setIdSolicitudes(Integer idSolicitudes) {
        this.idSolicitudes = idSolicitudes;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public String getClasificacionSolicitud() {
        return clasificacionSolicitud;
    }

    public void setClasificacionSolicitud(String clasificacionSolicitud) {
        this.clasificacionSolicitud = clasificacionSolicitud;
    }

    public String getAdjuntoSolicitud() {
        return adjuntoSolicitud;
    }

    public void setAdjuntoSolicitud(String adjuntoSolicitud) {
        this.adjuntoSolicitud = adjuntoSolicitud;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudes != null ? idSolicitudes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.idSolicitudes == null && other.idSolicitudes != null) || (this.idSolicitudes != null && !this.idSolicitudes.equals(other.idSolicitudes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Solicitud[idSolicitudes=" + idSolicitudes + "]";
    }

}
