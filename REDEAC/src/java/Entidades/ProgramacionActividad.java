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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "programacion_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramacionActividad.findAll", query = "SELECT p FROM ProgramacionActividad p")
    , @NamedQuery(name = "ProgramacionActividad.findByIdProgramacionActividad", query = "SELECT p FROM ProgramacionActividad p WHERE p.idProgramacionActividad = :idProgramacionActividad")
    , @NamedQuery(name = "ProgramacionActividad.findBySemana", query = "SELECT p FROM ProgramacionActividad p WHERE p.semana = :semana")
    , @NamedQuery(name = "ProgramacionActividad.findByFechaRegistro", query = "SELECT p FROM ProgramacionActividad p WHERE p.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "ProgramacionActividad.findByUsuarioRegistro", query = "SELECT p FROM ProgramacionActividad p WHERE p.usuarioRegistro = :usuarioRegistro")})
public class ProgramacionActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_programacion_actividad")
    private Integer idProgramacionActividad;
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "semana")
    private String semana;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProgramacionActividad")
    private Collection<SeguimientoActividad> seguimientoActividadCollection;

    public ProgramacionActividad() {
    }

    public ProgramacionActividad(Integer idProgramacionActividad) {
        this.idProgramacionActividad = idProgramacionActividad;
    }

    public Integer getIdProgramacionActividad() {
        return idProgramacionActividad;
    }

    public void setIdProgramacionActividad(Integer idProgramacionActividad) {
        this.idProgramacionActividad = idProgramacionActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
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

    @XmlTransient
    public Collection<SeguimientoActividad> getSeguimientoActividadCollection() {
        return seguimientoActividadCollection;
    }

    public void setSeguimientoActividadCollection(Collection<SeguimientoActividad> seguimientoActividadCollection) {
        this.seguimientoActividadCollection = seguimientoActividadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramacionActividad != null ? idProgramacionActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionActividad)) {
            return false;
        }
        ProgramacionActividad other = (ProgramacionActividad) object;
        if ((this.idProgramacionActividad == null && other.idProgramacionActividad != null) || (this.idProgramacionActividad != null && !this.idProgramacionActividad.equals(other.idProgramacionActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ProgramacionActividad[ idProgramacionActividad=" + idProgramacionActividad + " ]";
    }
    
}
