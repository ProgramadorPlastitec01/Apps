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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "reunion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reunion.findAll", query = "SELECT r FROM Reunion r"),
    @NamedQuery(name = "Reunion.findByIdReunion", query = "SELECT r FROM Reunion r WHERE r.idReunion = :idReunion"),
    @NamedQuery(name = "Reunion.findByConsecutivo", query = "SELECT r FROM Reunion r WHERE r.consecutivo = :consecutivo"),
    @NamedQuery(name = "Reunion.findByFechaReunion", query = "SELECT r FROM Reunion r WHERE r.fechaReunion = :fechaReunion"),
    @NamedQuery(name = "Reunion.findByFechaRegistro", query = "SELECT r FROM Reunion r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Reunion.findByUsuarioRegistro", query = "SELECT r FROM Reunion r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Reunion.findByEstado", query = "SELECT r FROM Reunion r WHERE r.estado = :estado")})
public class Reunion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reunion")
    private Integer idReunion;
    @Basic(optional = false)
    @Column(name = "consecutivo")
    private int consecutivo;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_reunion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReunion;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private int usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;

    public Reunion() {
    }

    public Reunion(Integer idReunion) {
        this.idReunion = idReunion;
    }

    public Reunion(Integer idReunion, int consecutivo, String descripcion, Date fechaReunion, Date fechaRegistro, int usuarioRegistro, int estado) {
        this.idReunion = idReunion;
        this.consecutivo = consecutivo;
        this.descripcion = descripcion;
        this.fechaReunion = fechaReunion;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
        this.estado = estado;
    }

    public Integer getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(Integer idReunion) {
        this.idReunion = idReunion;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaReunion() {
        return fechaReunion;
    }

    public void setFechaReunion(Date fechaReunion) {
        this.fechaReunion = fechaReunion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(int usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReunion != null ? idReunion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reunion)) {
            return false;
        }
        Reunion other = (Reunion) object;
        if ((this.idReunion == null && other.idReunion != null) || (this.idReunion != null && !this.idReunion.equals(other.idReunion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Reunion[ idReunion=" + idReunion + " ]";
    }
    
}
