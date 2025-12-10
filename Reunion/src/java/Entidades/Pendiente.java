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
@Table(name = "pendiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pendiente.findAll", query = "SELECT p FROM Pendiente p"),
    @NamedQuery(name = "Pendiente.findByIdPendiente", query = "SELECT p FROM Pendiente p WHERE p.idPendiente = :idPendiente"),
    @NamedQuery(name = "Pendiente.findByIdReunion", query = "SELECT p FROM Pendiente p WHERE p.idReunion = :idReunion"),
    @NamedQuery(name = "Pendiente.findByAsunto", query = "SELECT p FROM Pendiente p WHERE p.asunto = :asunto"),
    @NamedQuery(name = "Pendiente.findByUsuarioRegistro", query = "SELECT p FROM Pendiente p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Pendiente.findByFechaRegistro", query = "SELECT p FROM Pendiente p WHERE p.fechaRegistro = :fechaRegistro")})
public class Pendiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pendiente")
    private Integer idPendiente;
    @Basic(optional = false)
    @Column(name = "id_reunion")
    private int idReunion;
    @Basic(optional = false)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Lob
    @Column(name = "responsables")
    private String responsables;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private int usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Pendiente() {
    }

    public Pendiente(Integer idPendiente) {
        this.idPendiente = idPendiente;
    }

    public Pendiente(Integer idPendiente, int idReunion, String asunto, String descripcion, String responsables, int usuarioRegistro, Date fechaRegistro) {
        this.idPendiente = idPendiente;
        this.idReunion = idReunion;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.responsables = responsables;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdPendiente() {
        return idPendiente;
    }

    public void setIdPendiente(Integer idPendiente) {
        this.idPendiente = idPendiente;
    }

    public int getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(int idReunion) {
        this.idReunion = idReunion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsables() {
        return responsables;
    }

    public void setResponsables(String responsables) {
        this.responsables = responsables;
    }

    public int getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(int usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPendiente != null ? idPendiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pendiente)) {
            return false;
        }
        Pendiente other = (Pendiente) object;
        if ((this.idPendiente == null && other.idPendiente != null) || (this.idPendiente != null && !this.idPendiente.equals(other.idPendiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pendiente[ idPendiente=" + idPendiente + " ]";
    }
    
}
