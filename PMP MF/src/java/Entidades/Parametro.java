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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "parametro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
    @NamedQuery(name = "Parametro.findByIdParametro", query = "SELECT p FROM Parametro p WHERE p.idParametro = :idParametro"),
    @NamedQuery(name = "Parametro.findByActividad", query = "SELECT p FROM Parametro p WHERE p.actividad = :actividad"),
    @NamedQuery(name = "Parametro.findByGrupo", query = "SELECT p FROM Parametro p WHERE p.grupo = :grupo"),
    @NamedQuery(name = "Parametro.findByTipoMtto", query = "SELECT p FROM Parametro p WHERE p.tipoMtto = :tipoMtto"),
    @NamedQuery(name = "Parametro.findByEjecucion", query = "SELECT p FROM Parametro p WHERE p.ejecucion = :ejecucion"),
    @NamedQuery(name = "Parametro.findByFrecuencia", query = "SELECT p FROM Parametro p WHERE p.frecuencia = :frecuencia"),
    @NamedQuery(name = "Parametro.findByUsuarioRegistro", query = "SELECT p FROM Parametro p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Parametro.findByFechaRegistro", query = "SELECT p FROM Parametro p WHERE p.fechaRegistro = :fechaRegistro")})
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "tipo_mtto")
    private String tipoMtto;
    @Column(name = "ejecucion")
    private String ejecucion;
    @Column(name = "frecuencia")
    private String frecuencia;
    @Lob
    @Column(name = "tipo_equipo")
    private String tipoEquipo;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Parametro() {
    }

    public Parametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTipoMtto() {
        return tipoMtto;
    }

    public void setTipoMtto(String tipoMtto) {
        this.tipoMtto = tipoMtto;
    }

    public String getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(String ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametro[ idParametro=" + idParametro + " ]";
    }
    
}
