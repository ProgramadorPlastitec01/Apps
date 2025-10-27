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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "seguimiento_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguimientoActividad.findAll", query = "SELECT s FROM SeguimientoActividad s")
    , @NamedQuery(name = "SeguimientoActividad.findByIdSeguimientoActividad", query = "SELECT s FROM SeguimientoActividad s WHERE s.idSeguimientoActividad = :idSeguimientoActividad")
    , @NamedQuery(name = "SeguimientoActividad.findByActividad", query = "SELECT s FROM SeguimientoActividad s WHERE s.actividad = :actividad")
    , @NamedQuery(name = "SeguimientoActividad.findByEquipo", query = "SELECT s FROM SeguimientoActividad s WHERE s.equipo = :equipo")
    , @NamedQuery(name = "SeguimientoActividad.findByFechaActividad", query = "SELECT s FROM SeguimientoActividad s WHERE s.fechaActividad = :fechaActividad")
    , @NamedQuery(name = "SeguimientoActividad.findByResponsable", query = "SELECT s FROM SeguimientoActividad s WHERE s.responsable = :responsable")
    , @NamedQuery(name = "SeguimientoActividad.findByFechaVerificacion", query = "SELECT s FROM SeguimientoActividad s WHERE s.fechaVerificacion = :fechaVerificacion")
    , @NamedQuery(name = "SeguimientoActividad.findByUsuarioVerifica", query = "SELECT s FROM SeguimientoActividad s WHERE s.usuarioVerifica = :usuarioVerifica")
    , @NamedQuery(name = "SeguimientoActividad.findByFechaRegistro", query = "SELECT s FROM SeguimientoActividad s WHERE s.fechaRegistro = :fechaRegistro")})
public class SeguimientoActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguimiento_actividad")
    private Integer idSeguimientoActividad;
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "equipo")
    private String equipo;
    @Lob
    @Column(name = "verificacion_antes")
    private String verificacionAntes;
    @Column(name = "fecha_actividad")
    @Temporal(TemporalType.DATE)
    private Date fechaActividad;
    @Column(name = "responsable")
    private String responsable;
    @Lob
    @Column(name = "verificacion_despues")
    private String verificacionDespues;
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVerificacion;
    @Column(name = "usuario_verifica")
    private String usuarioVerifica;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_programacion_actividad", referencedColumnName = "id_programacion_actividad")
    @ManyToOne(optional = false)
    private ProgramacionActividad idProgramacionActividad;

    public SeguimientoActividad() {
    }

    public SeguimientoActividad(Integer idSeguimientoActividad) {
        this.idSeguimientoActividad = idSeguimientoActividad;
    }

    public Integer getIdSeguimientoActividad() {
        return idSeguimientoActividad;
    }

    public void setIdSeguimientoActividad(Integer idSeguimientoActividad) {
        this.idSeguimientoActividad = idSeguimientoActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getVerificacionAntes() {
        return verificacionAntes;
    }

    public void setVerificacionAntes(String verificacionAntes) {
        this.verificacionAntes = verificacionAntes;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getVerificacionDespues() {
        return verificacionDespues;
    }

    public void setVerificacionDespues(String verificacionDespues) {
        this.verificacionDespues = verificacionDespues;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public String getUsuarioVerifica() {
        return usuarioVerifica;
    }

    public void setUsuarioVerifica(String usuarioVerifica) {
        this.usuarioVerifica = usuarioVerifica;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public ProgramacionActividad getIdProgramacionActividad() {
        return idProgramacionActividad;
    }

    public void setIdProgramacionActividad(ProgramacionActividad idProgramacionActividad) {
        this.idProgramacionActividad = idProgramacionActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimientoActividad != null ? idSeguimientoActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeguimientoActividad)) {
            return false;
        }
        SeguimientoActividad other = (SeguimientoActividad) object;
        if ((this.idSeguimientoActividad == null && other.idSeguimientoActividad != null) || (this.idSeguimientoActividad != null && !this.idSeguimientoActividad.equals(other.idSeguimientoActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SeguimientoActividad[ idSeguimientoActividad=" + idSeguimientoActividad + " ]";
    }
    
}
