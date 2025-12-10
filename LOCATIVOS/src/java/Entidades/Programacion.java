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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Aprendiz.Sena1
 */
@Entity
@Table(name = "programacion")
@NamedQueries({
    @NamedQuery(name = "Programacion.findAll", query = "SELECT p FROM Programacion p"),
    @NamedQuery(name = "Programacion.findByIdProgramacion", query = "SELECT p FROM Programacion p WHERE p.idProgramacion = :idProgramacion"),
    @NamedQuery(name = "Programacion.findByFechaInicio", query = "SELECT p FROM Programacion p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Programacion.findByFechaFin", query = "SELECT p FROM Programacion p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "Programacion.findByEstado", query = "SELECT p FROM Programacion p WHERE p.estado = :estado"),
    @NamedQuery(name = "Programacion.findByFechaRegistro", query = "SELECT p FROM Programacion p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Programacion.findByUsuarioRegistro", query = "SELECT p FROM Programacion p WHERE p.usuarioRegistro = :usuarioRegistro")})
public class Programacion implements Serializable {
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
    @Lob
    @Column(name = "responsable_interno")
    private String responsableInterno;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @OneToMany(mappedBy = "programacion")
    private Collection<ActividadesAdicionales> actividadesAdicionalesCollection;

    public Programacion() {
    }

    public Programacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Programacion(Integer idProgramacion, String nombreProgramacion, String responsableInterno, Date fechaInicio, Date fechaFin, String observacion, int estado, Date fechaRegistro, String usuarioRegistro) {
        this.idProgramacion = idProgramacion;
        this.nombreProgramacion = nombreProgramacion;
        this.responsableInterno = responsableInterno;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observacion = observacion;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
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

    public String getResponsableInterno() {
        return responsableInterno;
    }

    public void setResponsableInterno(String responsableInterno) {
        this.responsableInterno = responsableInterno;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Collection<ActividadesAdicionales> getActividadesAdicionalesCollection() {
        return actividadesAdicionalesCollection;
    }

    public void setActividadesAdicionalesCollection(Collection<ActividadesAdicionales> actividadesAdicionalesCollection) {
        this.actividadesAdicionalesCollection = actividadesAdicionalesCollection;
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
        return "Entidades.Programacion[idProgramacion=" + idProgramacion + "]";
    }

}
