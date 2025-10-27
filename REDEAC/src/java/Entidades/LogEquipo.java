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
import javax.persistence.Lob;
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
@Table(name = "log_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogEquipo.findAll", query = "SELECT l FROM LogEquipo l")
    , @NamedQuery(name = "LogEquipo.findByIdLogEquipo", query = "SELECT l FROM LogEquipo l WHERE l.idLogEquipo = :idLogEquipo")
    , @NamedQuery(name = "LogEquipo.findByIdEquipo", query = "SELECT l FROM LogEquipo l WHERE l.idEquipo = :idEquipo")
    , @NamedQuery(name = "LogEquipo.findByNombre", query = "SELECT l FROM LogEquipo l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "LogEquipo.findByResponsableOld", query = "SELECT l FROM LogEquipo l WHERE l.responsableOld = :responsableOld")
    , @NamedQuery(name = "LogEquipo.findByResponsableNew", query = "SELECT l FROM LogEquipo l WHERE l.responsableNew = :responsableNew")
    , @NamedQuery(name = "LogEquipo.findByTipoEquipoOld", query = "SELECT l FROM LogEquipo l WHERE l.tipoEquipoOld = :tipoEquipoOld")
    , @NamedQuery(name = "LogEquipo.findByTipoEquipoNew", query = "SELECT l FROM LogEquipo l WHERE l.tipoEquipoNew = :tipoEquipoNew")
    , @NamedQuery(name = "LogEquipo.findByIdAreaOld", query = "SELECT l FROM LogEquipo l WHERE l.idAreaOld = :idAreaOld")
    , @NamedQuery(name = "LogEquipo.findByIdAreaNew", query = "SELECT l FROM LogEquipo l WHERE l.idAreaNew = :idAreaNew")
    , @NamedQuery(name = "LogEquipo.findByCargoOld", query = "SELECT l FROM LogEquipo l WHERE l.cargoOld = :cargoOld")
    , @NamedQuery(name = "LogEquipo.findByCargoNew", query = "SELECT l FROM LogEquipo l WHERE l.cargoNew = :cargoNew")
    , @NamedQuery(name = "LogEquipo.findByEstadoOld", query = "SELECT l FROM LogEquipo l WHERE l.estadoOld = :estadoOld")
    , @NamedQuery(name = "LogEquipo.findByEstadoNew", query = "SELECT l FROM LogEquipo l WHERE l.estadoNew = :estadoNew")
    , @NamedQuery(name = "LogEquipo.findByFechaMovimientoOld", query = "SELECT l FROM LogEquipo l WHERE l.fechaMovimientoOld = :fechaMovimientoOld")
    , @NamedQuery(name = "LogEquipo.findByFechaMovimientoNew", query = "SELECT l FROM LogEquipo l WHERE l.fechaMovimientoNew = :fechaMovimientoNew")
    , @NamedQuery(name = "LogEquipo.findByUsuarioRegistroOld", query = "SELECT l FROM LogEquipo l WHERE l.usuarioRegistroOld = :usuarioRegistroOld")
    , @NamedQuery(name = "LogEquipo.findByUsuarioRegistroNew", query = "SELECT l FROM LogEquipo l WHERE l.usuarioRegistroNew = :usuarioRegistroNew")
    , @NamedQuery(name = "LogEquipo.findByFechaRegistroOld", query = "SELECT l FROM LogEquipo l WHERE l.fechaRegistroOld = :fechaRegistroOld")
    , @NamedQuery(name = "LogEquipo.findByFechaRegistroNew", query = "SELECT l FROM LogEquipo l WHERE l.fechaRegistroNew = :fechaRegistroNew")})
public class LogEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log_equipo")
    private Integer idLogEquipo;
    @Basic(optional = false)
    @Column(name = "id_equipo")
    private int idEquipo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "responsable_old")
    private String responsableOld;
    @Basic(optional = false)
    @Column(name = "responsable_new")
    private String responsableNew;
    @Basic(optional = false)
    @Column(name = "tipo_equipo_old")
    private String tipoEquipoOld;
    @Basic(optional = false)
    @Column(name = "tipo_equipo_new")
    private String tipoEquipoNew;
    @Basic(optional = false)
    @Column(name = "id_area_old")
    private int idAreaOld;
    @Basic(optional = false)
    @Column(name = "id_area_new")
    private int idAreaNew;
    @Basic(optional = false)
    @Column(name = "cargo_old")
    private String cargoOld;
    @Basic(optional = false)
    @Column(name = "cargo_new")
    private String cargoNew;
    @Basic(optional = false)
    @Column(name = "estado_old")
    private String estadoOld;
    @Basic(optional = false)
    @Column(name = "estado_new")
    private String estadoNew;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones_old")
    private String observacionesOld;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones_new")
    private String observacionesNew;
    @Basic(optional = false)
    @Column(name = "fecha_movimiento_old")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimientoOld;
    @Basic(optional = false)
    @Column(name = "fecha_movimiento_new")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimientoNew;
    @Basic(optional = false)
    @Column(name = "usuario_registro_old")
    private String usuarioRegistroOld;
    @Basic(optional = false)
    @Column(name = "usuario_registro_new")
    private String usuarioRegistroNew;
    @Basic(optional = false)
    @Column(name = "fecha_registro_old")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroOld;
    @Basic(optional = false)
    @Column(name = "fecha_registro_new")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroNew;

    public LogEquipo() {
    }

    public LogEquipo(Integer idLogEquipo) {
        this.idLogEquipo = idLogEquipo;
    }

    public LogEquipo(Integer idLogEquipo, int idEquipo, String nombre, String responsableOld, String responsableNew, String tipoEquipoOld, String tipoEquipoNew, int idAreaOld, int idAreaNew, String cargoOld, String cargoNew, String estadoOld, String estadoNew, String observacionesOld, String observacionesNew, Date fechaMovimientoOld, Date fechaMovimientoNew, String usuarioRegistroOld, String usuarioRegistroNew, Date fechaRegistroOld, Date fechaRegistroNew) {
        this.idLogEquipo = idLogEquipo;
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.responsableOld = responsableOld;
        this.responsableNew = responsableNew;
        this.tipoEquipoOld = tipoEquipoOld;
        this.tipoEquipoNew = tipoEquipoNew;
        this.idAreaOld = idAreaOld;
        this.idAreaNew = idAreaNew;
        this.cargoOld = cargoOld;
        this.cargoNew = cargoNew;
        this.estadoOld = estadoOld;
        this.estadoNew = estadoNew;
        this.observacionesOld = observacionesOld;
        this.observacionesNew = observacionesNew;
        this.fechaMovimientoOld = fechaMovimientoOld;
        this.fechaMovimientoNew = fechaMovimientoNew;
        this.usuarioRegistroOld = usuarioRegistroOld;
        this.usuarioRegistroNew = usuarioRegistroNew;
        this.fechaRegistroOld = fechaRegistroOld;
        this.fechaRegistroNew = fechaRegistroNew;
    }

    public Integer getIdLogEquipo() {
        return idLogEquipo;
    }

    public void setIdLogEquipo(Integer idLogEquipo) {
        this.idLogEquipo = idLogEquipo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResponsableOld() {
        return responsableOld;
    }

    public void setResponsableOld(String responsableOld) {
        this.responsableOld = responsableOld;
    }

    public String getResponsableNew() {
        return responsableNew;
    }

    public void setResponsableNew(String responsableNew) {
        this.responsableNew = responsableNew;
    }

    public String getTipoEquipoOld() {
        return tipoEquipoOld;
    }

    public void setTipoEquipoOld(String tipoEquipoOld) {
        this.tipoEquipoOld = tipoEquipoOld;
    }

    public String getTipoEquipoNew() {
        return tipoEquipoNew;
    }

    public void setTipoEquipoNew(String tipoEquipoNew) {
        this.tipoEquipoNew = tipoEquipoNew;
    }

    public int getIdAreaOld() {
        return idAreaOld;
    }

    public void setIdAreaOld(int idAreaOld) {
        this.idAreaOld = idAreaOld;
    }

    public int getIdAreaNew() {
        return idAreaNew;
    }

    public void setIdAreaNew(int idAreaNew) {
        this.idAreaNew = idAreaNew;
    }

    public String getCargoOld() {
        return cargoOld;
    }

    public void setCargoOld(String cargoOld) {
        this.cargoOld = cargoOld;
    }

    public String getCargoNew() {
        return cargoNew;
    }

    public void setCargoNew(String cargoNew) {
        this.cargoNew = cargoNew;
    }

    public String getEstadoOld() {
        return estadoOld;
    }

    public void setEstadoOld(String estadoOld) {
        this.estadoOld = estadoOld;
    }

    public String getEstadoNew() {
        return estadoNew;
    }

    public void setEstadoNew(String estadoNew) {
        this.estadoNew = estadoNew;
    }

    public String getObservacionesOld() {
        return observacionesOld;
    }

    public void setObservacionesOld(String observacionesOld) {
        this.observacionesOld = observacionesOld;
    }

    public String getObservacionesNew() {
        return observacionesNew;
    }

    public void setObservacionesNew(String observacionesNew) {
        this.observacionesNew = observacionesNew;
    }

    public Date getFechaMovimientoOld() {
        return fechaMovimientoOld;
    }

    public void setFechaMovimientoOld(Date fechaMovimientoOld) {
        this.fechaMovimientoOld = fechaMovimientoOld;
    }

    public Date getFechaMovimientoNew() {
        return fechaMovimientoNew;
    }

    public void setFechaMovimientoNew(Date fechaMovimientoNew) {
        this.fechaMovimientoNew = fechaMovimientoNew;
    }

    public String getUsuarioRegistroOld() {
        return usuarioRegistroOld;
    }

    public void setUsuarioRegistroOld(String usuarioRegistroOld) {
        this.usuarioRegistroOld = usuarioRegistroOld;
    }

    public String getUsuarioRegistroNew() {
        return usuarioRegistroNew;
    }

    public void setUsuarioRegistroNew(String usuarioRegistroNew) {
        this.usuarioRegistroNew = usuarioRegistroNew;
    }

    public Date getFechaRegistroOld() {
        return fechaRegistroOld;
    }

    public void setFechaRegistroOld(Date fechaRegistroOld) {
        this.fechaRegistroOld = fechaRegistroOld;
    }

    public Date getFechaRegistroNew() {
        return fechaRegistroNew;
    }

    public void setFechaRegistroNew(Date fechaRegistroNew) {
        this.fechaRegistroNew = fechaRegistroNew;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogEquipo != null ? idLogEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogEquipo)) {
            return false;
        }
        LogEquipo other = (LogEquipo) object;
        if ((this.idLogEquipo == null && other.idLogEquipo != null) || (this.idLogEquipo != null && !this.idLogEquipo.equals(other.idLogEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LogEquipo[ idLogEquipo=" + idLogEquipo + " ]";
    }
    
}
