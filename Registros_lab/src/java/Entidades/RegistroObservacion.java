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
@Table(name = "registro_observacion")
@NamedQueries({
    @NamedQuery(name = "RegistroObservacion.findAll", query = "SELECT r FROM RegistroObservacion r"),
    @NamedQuery(name = "RegistroObservacion.findByIdRegistroObservacion", query = "SELECT r FROM RegistroObservacion r WHERE r.idRegistroObservacion = :idRegistroObservacion"),
    @NamedQuery(name = "RegistroObservacion.findByAsunto", query = "SELECT r FROM RegistroObservacion r WHERE r.asunto = :asunto"),
    @NamedQuery(name = "RegistroObservacion.findByTipoObservacion", query = "SELECT r FROM RegistroObservacion r WHERE r.tipoObservacion = :tipoObservacion"),
    @NamedQuery(name = "RegistroObservacion.findByUsuarioRegistro", query = "SELECT r FROM RegistroObservacion r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroObservacion.findByFechaRegistro", query = "SELECT r FROM RegistroObservacion r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroObservacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_observacion")
    private Integer idRegistroObservacion;
    @Column(name = "asunto")
    private String asunto;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipo_observacion")
    private String tipoObservacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;

    public RegistroObservacion() {
    }

    public RegistroObservacion(Integer idRegistroObservacion) {
        this.idRegistroObservacion = idRegistroObservacion;
    }

    public Integer getIdRegistroObservacion() {
        return idRegistroObservacion;
    }

    public void setIdRegistroObservacion(Integer idRegistroObservacion) {
        this.idRegistroObservacion = idRegistroObservacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoObservacion() {
        return tipoObservacion;
    }

    public void setTipoObservacion(String tipoObservacion) {
        this.tipoObservacion = tipoObservacion;
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

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroObservacion != null ? idRegistroObservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroObservacion)) {
            return false;
        }
        RegistroObservacion other = (RegistroObservacion) object;
        if ((this.idRegistroObservacion == null && other.idRegistroObservacion != null) || (this.idRegistroObservacion != null && !this.idRegistroObservacion.equals(other.idRegistroObservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroObservacion[idRegistroObservacion=" + idRegistroObservacion + "]";
    }

}
