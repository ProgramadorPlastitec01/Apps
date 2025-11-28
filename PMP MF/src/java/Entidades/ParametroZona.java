/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "parametro_zona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroZona.findAll", query = "SELECT p FROM ParametroZona p"),
    @NamedQuery(name = "ParametroZona.findByIdParametroZona", query = "SELECT p FROM ParametroZona p WHERE p.idParametroZona = :idParametroZona"),
    @NamedQuery(name = "ParametroZona.findByIdZona", query = "SELECT p FROM ParametroZona p WHERE p.idZona = :idZona"),
    @NamedQuery(name = "ParametroZona.findByActividad", query = "SELECT p FROM ParametroZona p WHERE p.actividad = :actividad"),
    @NamedQuery(name = "ParametroZona.findByEstado", query = "SELECT p FROM ParametroZona p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroZona.findByUsuarioRegistro", query = "SELECT p FROM ParametroZona p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ParametroZona.findByFechaRegistro", query = "SELECT p FROM ParametroZona p WHERE p.fechaRegistro = :fechaRegistro")})
public class ParametroZona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro_zona")
    private Integer idParametroZona;
    @Column(name = "id_zona")
    private Integer idZona;
    @Column(name = "actividad")
    private Integer actividad;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @Column(name = "fecha_registro")
    private Integer fechaRegistro;

    public ParametroZona() {
    }

    public ParametroZona(Integer idParametroZona) {
        this.idParametroZona = idParametroZona;
    }

    public Integer getIdParametroZona() {
        return idParametroZona;
    }

    public void setIdParametroZona(Integer idParametroZona) {
        this.idParametroZona = idParametroZona;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Integer usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Integer fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametroZona != null ? idParametroZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroZona)) {
            return false;
        }
        ParametroZona other = (ParametroZona) object;
        if ((this.idParametroZona == null && other.idParametroZona != null) || (this.idParametroZona != null && !this.idParametroZona.equals(other.idParametroZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametroZona[ idParametroZona=" + idParametroZona + " ]";
    }
    
}
