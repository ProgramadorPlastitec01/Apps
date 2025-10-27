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
@Table(name = "actividad_general")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadGeneral.findAll", query = "SELECT a FROM ActividadGeneral a")
    , @NamedQuery(name = "ActividadGeneral.findByIdActividad", query = "SELECT a FROM ActividadGeneral a WHERE a.idActividad = :idActividad")
    , @NamedQuery(name = "ActividadGeneral.findByFechaInicio", query = "SELECT a FROM ActividadGeneral a WHERE a.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "ActividadGeneral.findByFechaFin", query = "SELECT a FROM ActividadGeneral a WHERE a.fechaFin = :fechaFin")
    , @NamedQuery(name = "ActividadGeneral.findByBitacora", query = "SELECT a FROM ActividadGeneral a WHERE a.bitacora = :bitacora")
    , @NamedQuery(name = "ActividadGeneral.findByFechaRegistro", query = "SELECT a FROM ActividadGeneral a WHERE a.fechaRegistro = :fechaRegistro")})
public class ActividadGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad")
    private Integer idActividad;
    @Basic(optional = false)
    @Lob
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @Basic(optional = false)
    @Column(name = "bitacora")
    private short bitacora;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "usuario_registro", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioRegistro;

    public ActividadGeneral() {
    }

    public ActividadGeneral(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public ActividadGeneral(Integer idActividad, String asunto, Date fechaInicio, Date fechaFin, String actividad, short bitacora, Date fechaRegistro) {
        this.idActividad = idActividad;
        this.asunto = asunto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.actividad = actividad;
        this.bitacora = bitacora;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
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

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public short getBitacora() {
        return bitacora;
    }

    public void setBitacora(short bitacora) {
        this.bitacora = bitacora;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadGeneral)) {
            return false;
        }
        ActividadGeneral other = (ActividadGeneral) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ActividadGeneral[ idActividad=" + idActividad + " ]";
    }
    
}
