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
@Table(name = "actividades")
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a"),
    @NamedQuery(name = "Actividades.findByIdActividades", query = "SELECT a FROM Actividades a WHERE a.idActividades = :idActividades"),
    @NamedQuery(name = "Actividades.findByIdProgramacionDetalle", query = "SELECT a FROM Actividades a WHERE a.idProgramacionDetalle = :idProgramacionDetalle"),
    @NamedQuery(name = "Actividades.findByAreaLista", query = "SELECT a FROM Actividades a WHERE a.areaLista = :areaLista"),
    @NamedQuery(name = "Actividades.findByFechaRegistro", query = "SELECT a FROM Actividades a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Actividades.findByUsuarioRegistro", query = "SELECT a FROM Actividades a WHERE a.usuarioRegistro = :usuarioRegistro")})
public class Actividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_actividades")
    private Integer idActividades;
    @Basic(optional = false)
    @Column(name = "id_programacion_detalle")
    private int idProgramacionDetalle;
    @Basic(optional = false)
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @Basic(optional = false)
    @Column(name = "area_lista")
    private int areaLista;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Actividades() {
    }

    public Actividades(Integer idActividades) {
        this.idActividades = idActividades;
    }

    public Actividades(Integer idActividades, int idProgramacionDetalle, String actividad, int areaLista, Date fechaRegistro, String usuarioRegistro) {
        this.idActividades = idActividades;
        this.idProgramacionDetalle = idProgramacionDetalle;
        this.actividad = actividad;
        this.areaLista = areaLista;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdActividades() {
        return idActividades;
    }

    public void setIdActividades(Integer idActividades) {
        this.idActividades = idActividades;
    }

    public int getIdProgramacionDetalle() {
        return idProgramacionDetalle;
    }

    public void setIdProgramacionDetalle(int idProgramacionDetalle) {
        this.idProgramacionDetalle = idProgramacionDetalle;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public int getAreaLista() {
        return areaLista;
    }

    public void setAreaLista(int areaLista) {
        this.areaLista = areaLista;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividades != null ? idActividades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.idActividades == null && other.idActividades != null) || (this.idActividades != null && !this.idActividades.equals(other.idActividades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Actividades[idActividades=" + idActividades + "]";
    }

}
