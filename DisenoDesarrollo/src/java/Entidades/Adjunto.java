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
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "adjunto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adjunto.findAll", query = "SELECT a FROM Adjunto a")
    , @NamedQuery(name = "Adjunto.findByIdAdjunto", query = "SELECT a FROM Adjunto a WHERE a.idAdjunto = :idAdjunto")
    , @NamedQuery(name = "Adjunto.findByTipo", query = "SELECT a FROM Adjunto a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "Adjunto.findByEtapa", query = "SELECT a FROM Adjunto a WHERE a.etapa = :etapa")
    , @NamedQuery(name = "Adjunto.findByAdjunto", query = "SELECT a FROM Adjunto a WHERE a.adjunto = :adjunto")
    , @NamedQuery(name = "Adjunto.findByFechaRegistro", query = "SELECT a FROM Adjunto a WHERE a.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Adjunto.findByFechaVerificacion", query = "SELECT a FROM Adjunto a WHERE a.fechaVerificacion = :fechaVerificacion")
    , @NamedQuery(name = "Adjunto.findByUsuarioRegistro", query = "SELECT a FROM Adjunto a WHERE a.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Adjunto.findByUsuarioVerificacion", query = "SELECT a FROM Adjunto a WHERE a.usuarioVerificacion = :usuarioVerificacion")})
public class Adjunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adjunto")
    private Integer idAdjunto;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "etapa")
    private String etapa;
    @Lob
    @Column(name = "fase")
    private String fase;
    @Column(name = "adjunto")
    private String adjunto;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Lob
    @Column(name = "verificacion")
    private String verificacion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "usuario_verificacion")
    private String usuarioVerificacion;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto idProyecto;

    public Adjunto() {
    }

    public Adjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(String verificacion) {
        this.verificacion = verificacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getUsuarioVerificacion() {
        return usuarioVerificacion;
    }

    public void setUsuarioVerificacion(String usuarioVerificacion) {
        this.usuarioVerificacion = usuarioVerificacion;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdjunto != null ? idAdjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ((this.idAdjunto == null && other.idAdjunto != null) || (this.idAdjunto != null && !this.idAdjunto.equals(other.idAdjunto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Adjunto[ idAdjunto=" + idAdjunto + " ]";
    }
    
}
