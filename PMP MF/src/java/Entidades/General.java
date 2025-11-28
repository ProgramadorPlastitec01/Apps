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
@Table(name = "general")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "General.findAll", query = "SELECT g FROM General g"),
    @NamedQuery(name = "General.findByIdGeneral", query = "SELECT g FROM General g WHERE g.idGeneral = :idGeneral"),
    @NamedQuery(name = "General.findByActividad", query = "SELECT g FROM General g WHERE g.actividad = :actividad"),
    @NamedQuery(name = "General.findByDescripcion", query = "SELECT g FROM General g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "General.findByFrecuencia", query = "SELECT g FROM General g WHERE g.frecuencia = :frecuencia"),
    @NamedQuery(name = "General.findByFecuenciaAlerta", query = "SELECT g FROM General g WHERE g.fecuenciaAlerta = :fecuenciaAlerta"),
    @NamedQuery(name = "General.findByEstado", query = "SELECT g FROM General g WHERE g.estado = :estado"),
    @NamedQuery(name = "General.findByUsuarioRegistro", query = "SELECT g FROM General g WHERE g.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "General.findByFechaRegistro", query = "SELECT g FROM General g WHERE g.fechaRegistro = :fechaRegistro")})
public class General implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_general")
    private Integer idGeneral;
    @Column(name = "actividad")
    private Integer actividad;
    @Column(name = "descripcion")
    private Integer descripcion;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @Column(name = "fecuencia_alerta")
    private Integer fecuenciaAlerta;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @Column(name = "fecha_registro")
    private Integer fechaRegistro;

    public General() {
    }

    public General(Integer idGeneral) {
        this.idGeneral = idGeneral;
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

    public Integer getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Integer descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getFecuenciaAlerta() {
        return fecuenciaAlerta;
    }

    public void setFecuenciaAlerta(Integer fecuenciaAlerta) {
        this.fecuenciaAlerta = fecuenciaAlerta;
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
        hash += (idGeneral != null ? idGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof General)) {
            return false;
        }
        General other = (General) object;
        if ((this.idGeneral == null && other.idGeneral != null) || (this.idGeneral != null && !this.idGeneral.equals(other.idGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.General[ idGeneral=" + idGeneral + " ]";
    }
    
}
