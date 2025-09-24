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
 * @author Programador.TI1
 */
@Entity
@Table(name = "registro_despeje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroDespeje.findAll", query = "SELECT r FROM RegistroDespeje r")
    , @NamedQuery(name = "RegistroDespeje.findByIdDespeje", query = "SELECT r FROM RegistroDespeje r WHERE r.idDespeje = :idDespeje")
    , @NamedQuery(name = "RegistroDespeje.findByEstado", query = "SELECT r FROM RegistroDespeje r WHERE r.estado = :estado")
    , @NamedQuery(name = "RegistroDespeje.findByUsuarioRegistro", query = "SELECT r FROM RegistroDespeje r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "RegistroDespeje.findByFechaRegistro", query = "SELECT r FROM RegistroDespeje r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroDespeje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_despeje")
    private Integer idDespeje;
    @Lob
    @Column(name = "formato")
    private String formato;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro idRegistro;

    public RegistroDespeje() {
    }

    public RegistroDespeje(Integer idDespeje) {
        this.idDespeje = idDespeje;
    }

    public Integer getIdDespeje() {
        return idDespeje;
    }

    public void setIdDespeje(Integer idDespeje) {
        this.idDespeje = idDespeje;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Registro getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Registro idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDespeje != null ? idDespeje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDespeje)) {
            return false;
        }
        RegistroDespeje other = (RegistroDespeje) object;
        if ((this.idDespeje == null && other.idDespeje != null) || (this.idDespeje != null && !this.idDespeje.equals(other.idDespeje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroDespeje[ idDespeje=" + idDespeje + " ]";
    }
    
}
