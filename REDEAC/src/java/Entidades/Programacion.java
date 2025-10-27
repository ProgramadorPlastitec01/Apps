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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "programacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programacion.findAll", query = "SELECT p FROM Programacion p")
    , @NamedQuery(name = "Programacion.findByIdProgramacion", query = "SELECT p FROM Programacion p WHERE p.idProgramacion = :idProgramacion")
    , @NamedQuery(name = "Programacion.findByFechaRegistro", query = "SELECT p FROM Programacion p WHERE p.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Programacion.findByUsuarioRegistro", query = "SELECT p FROM Programacion p WHERE p.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Programacion.findByMes", query = "SELECT p FROM Programacion p WHERE p.mes = :mes")
    , @NamedQuery(name = "Programacion.findByCopias", query = "SELECT p FROM Programacion p WHERE p.copias = :copias")
    , @NamedQuery(name = "Programacion.findByIdEquipo", query = "SELECT p FROM Programacion p WHERE p.idEquipo = :idEquipo")
    , @NamedQuery(name = "Programacion.findByIdUsuario", query = "SELECT p FROM Programacion p WHERE p.idUsuario = :idUsuario")})
public class Programacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_programacion")
    private Integer idProgramacion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "mes")
    private String mes;
    @Column(name = "copias")
    private Integer copias;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idProgramacion")
    private Collection<Calificacion> calificacionCollection;

    public Programacion() {
    }

    public Programacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
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

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getCopias() {
        return copias;
    }

    public void setCopias(Integer copias) {
        this.copias = copias;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public Collection<Calificacion> getCalificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
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
        if (!(object instanceof Programacion)) {
            return false;
        }
        Programacion other = (Programacion) object;
        if ((this.idProgramacion == null && other.idProgramacion != null) || (this.idProgramacion != null && !this.idProgramacion.equals(other.idProgramacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Programacion[ idProgramacion=" + idProgramacion + " ]";
    }
    
}
