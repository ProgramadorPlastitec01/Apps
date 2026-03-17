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
 * @author Programador.TI1
 */
@Entity
@Table(name = "log_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogSolicitud.findAll", query = "SELECT l FROM LogSolicitud l")
    , @NamedQuery(name = "LogSolicitud.findByIdLog", query = "SELECT l FROM LogSolicitud l WHERE l.idLog = :idLog")
    , @NamedQuery(name = "LogSolicitud.findByIdSolicitud", query = "SELECT l FROM LogSolicitud l WHERE l.idSolicitud = :idSolicitud")
    , @NamedQuery(name = "LogSolicitud.findByPrioridadNew", query = "SELECT l FROM LogSolicitud l WHERE l.prioridadNew = :prioridadNew")
    , @NamedQuery(name = "LogSolicitud.findByPrioridadOld", query = "SELECT l FROM LogSolicitud l WHERE l.prioridadOld = :prioridadOld")
    , @NamedQuery(name = "LogSolicitud.findByFichaNew", query = "SELECT l FROM LogSolicitud l WHERE l.fichaNew = :fichaNew")
    , @NamedQuery(name = "LogSolicitud.findByFichaOld", query = "SELECT l FROM LogSolicitud l WHERE l.fichaOld = :fichaOld")
    , @NamedQuery(name = "LogSolicitud.findByPlanoNew", query = "SELECT l FROM LogSolicitud l WHERE l.planoNew = :planoNew")
    , @NamedQuery(name = "LogSolicitud.findByPlanoOld", query = "SELECT l FROM LogSolicitud l WHERE l.planoOld = :planoOld")
    , @NamedQuery(name = "LogSolicitud.findByPiezaNew", query = "SELECT l FROM LogSolicitud l WHERE l.piezaNew = :piezaNew")
    , @NamedQuery(name = "LogSolicitud.findByPiezaOld", query = "SELECT l FROM LogSolicitud l WHERE l.piezaOld = :piezaOld")
    , @NamedQuery(name = "LogSolicitud.findByCantidadNew", query = "SELECT l FROM LogSolicitud l WHERE l.cantidadNew = :cantidadNew")
    , @NamedQuery(name = "LogSolicitud.findByCantidadOld", query = "SELECT l FROM LogSolicitud l WHERE l.cantidadOld = :cantidadOld")
    , @NamedQuery(name = "LogSolicitud.findBySolicitante", query = "SELECT l FROM LogSolicitud l WHERE l.solicitante = :solicitante")
    , @NamedQuery(name = "LogSolicitud.findByFechaManejo", query = "SELECT l FROM LogSolicitud l WHERE l.fechaManejo = :fechaManejo")
    , @NamedQuery(name = "LogSolicitud.findByUsuarioManejo", query = "SELECT l FROM LogSolicitud l WHERE l.usuarioManejo = :usuarioManejo")})
public class LogSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log")
    private Integer idLog;
    @Column(name = "id_solicitud")
    private Integer idSolicitud;
    @Column(name = "prioridad_new")
    private String prioridadNew;
    @Column(name = "prioridad_old")
    private String prioridadOld;
    @Column(name = "ficha_new")
    private String fichaNew;
    @Column(name = "ficha_old")
    private String fichaOld;
    @Column(name = "plano_new")
    private String planoNew;
    @Column(name = "plano_old")
    private String planoOld;
    @Column(name = "pieza_new")
    private String piezaNew;
    @Column(name = "pieza_old")
    private String piezaOld;
    @Column(name = "cantidad_new")
    private String cantidadNew;
    @Column(name = "cantidad_old")
    private String cantidadOld;
    @Lob
    @Column(name = "descripcion_new")
    private String descripcionNew;
    @Lob
    @Column(name = "descripcion_old")
    private String descripcionOld;
    @Column(name = "solicitante")
    private String solicitante;
    @Lob
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "fecha_manejo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaManejo;
    @Column(name = "usuario_manejo")
    private String usuarioManejo;

    public LogSolicitud() {
    }

    public LogSolicitud(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getPrioridadNew() {
        return prioridadNew;
    }

    public void setPrioridadNew(String prioridadNew) {
        this.prioridadNew = prioridadNew;
    }

    public String getPrioridadOld() {
        return prioridadOld;
    }

    public void setPrioridadOld(String prioridadOld) {
        this.prioridadOld = prioridadOld;
    }

    public String getFichaNew() {
        return fichaNew;
    }

    public void setFichaNew(String fichaNew) {
        this.fichaNew = fichaNew;
    }

    public String getFichaOld() {
        return fichaOld;
    }

    public void setFichaOld(String fichaOld) {
        this.fichaOld = fichaOld;
    }

    public String getPlanoNew() {
        return planoNew;
    }

    public void setPlanoNew(String planoNew) {
        this.planoNew = planoNew;
    }

    public String getPlanoOld() {
        return planoOld;
    }

    public void setPlanoOld(String planoOld) {
        this.planoOld = planoOld;
    }

    public String getPiezaNew() {
        return piezaNew;
    }

    public void setPiezaNew(String piezaNew) {
        this.piezaNew = piezaNew;
    }

    public String getPiezaOld() {
        return piezaOld;
    }

    public void setPiezaOld(String piezaOld) {
        this.piezaOld = piezaOld;
    }

    public String getCantidadNew() {
        return cantidadNew;
    }

    public void setCantidadNew(String cantidadNew) {
        this.cantidadNew = cantidadNew;
    }

    public String getCantidadOld() {
        return cantidadOld;
    }

    public void setCantidadOld(String cantidadOld) {
        this.cantidadOld = cantidadOld;
    }

    public String getDescripcionNew() {
        return descripcionNew;
    }

    public void setDescripcionNew(String descripcionNew) {
        this.descripcionNew = descripcionNew;
    }

    public String getDescripcionOld() {
        return descripcionOld;
    }

    public void setDescripcionOld(String descripcionOld) {
        this.descripcionOld = descripcionOld;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFechaManejo() {
        return fechaManejo;
    }

    public void setFechaManejo(Date fechaManejo) {
        this.fechaManejo = fechaManejo;
    }

    public String getUsuarioManejo() {
        return usuarioManejo;
    }

    public void setUsuarioManejo(String usuarioManejo) {
        this.usuarioManejo = usuarioManejo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogSolicitud)) {
            return false;
        }
        LogSolicitud other = (LogSolicitud) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LogSolicitud[ idLog=" + idLog + " ]";
    }
    
}
