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
@Table(name = "parametro_general")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroGeneral.findAll", query = "SELECT p FROM ParametroGeneral p"),
    @NamedQuery(name = "ParametroGeneral.findByIdParametroGeneral", query = "SELECT p FROM ParametroGeneral p WHERE p.idParametroGeneral = :idParametroGeneral"),
    @NamedQuery(name = "ParametroGeneral.findByIdGeneral", query = "SELECT p FROM ParametroGeneral p WHERE p.idGeneral = :idGeneral"),
    @NamedQuery(name = "ParametroGeneral.findByActividad", query = "SELECT p FROM ParametroGeneral p WHERE p.actividad = :actividad"),
    @NamedQuery(name = "ParametroGeneral.findByEstado", query = "SELECT p FROM ParametroGeneral p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroGeneral.findByUsuarioRegistro", query = "SELECT p FROM ParametroGeneral p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ParametroGeneral.findByFechaRegistro", query = "SELECT p FROM ParametroGeneral p WHERE p.fechaRegistro = :fechaRegistro")})
public class ParametroGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro_general")
    private Integer idParametroGeneral;
    @Column(name = "id_general")
    private Integer idGeneral;
    @Column(name = "actividad")
    private Integer actividad;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @Column(name = "fecha_registro")
    private Integer fechaRegistro;

    public ParametroGeneral() {
    }

    public ParametroGeneral(Integer idParametroGeneral) {
        this.idParametroGeneral = idParametroGeneral;
    }

    public Integer getIdParametroGeneral() {
        return idParametroGeneral;
    }

    public void setIdParametroGeneral(Integer idParametroGeneral) {
        this.idParametroGeneral = idParametroGeneral;
    }

    public Integer getIdGeneral() {
        return idGeneral;
    }

    public void setIdGeneral(Integer idGeneral) {
        this.idGeneral = idGeneral;
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
        hash += (idParametroGeneral != null ? idParametroGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroGeneral)) {
            return false;
        }
        ParametroGeneral other = (ParametroGeneral) object;
        if ((this.idParametroGeneral == null && other.idParametroGeneral != null) || (this.idParametroGeneral != null && !this.idParametroGeneral.equals(other.idParametroGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametroGeneral[ idParametroGeneral=" + idParametroGeneral + " ]";
    }
    
}
