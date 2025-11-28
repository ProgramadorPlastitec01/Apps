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
@Table(name = "zona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zona.findAll", query = "SELECT z FROM Zona z"),
    @NamedQuery(name = "Zona.findByIdZona", query = "SELECT z FROM Zona z WHERE z.idZona = :idZona"),
    @NamedQuery(name = "Zona.findByZona", query = "SELECT z FROM Zona z WHERE z.zona = :zona"),
    @NamedQuery(name = "Zona.findByFrecuencia", query = "SELECT z FROM Zona z WHERE z.frecuencia = :frecuencia"),
    @NamedQuery(name = "Zona.findByFrecuenciaAlerta", query = "SELECT z FROM Zona z WHERE z.frecuenciaAlerta = :frecuenciaAlerta"),
    @NamedQuery(name = "Zona.findByUsuarioRegistro", query = "SELECT z FROM Zona z WHERE z.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Zona.findByFechaRegistro", query = "SELECT z FROM Zona z WHERE z.fechaRegistro = :fechaRegistro")})
public class Zona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_zona")
    private Integer idZona;
    @Basic(optional = false)
    @Column(name = "zona")
    private String zona;
    @Basic(optional = false)
    @Column(name = "frecuencia")
    private int frecuencia;
    @Basic(optional = false)
    @Column(name = "frecuencia_alerta")
    private int frecuenciaAlerta;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Zona() {
    }

    public Zona(Integer idZona) {
        this.idZona = idZona;
    }

    public Zona(Integer idZona, String zona, int frecuencia, int frecuenciaAlerta, String usuarioRegistro, Date fechaRegistro) {
        this.idZona = idZona;
        this.zona = zona;
        this.frecuencia = frecuencia;
        this.frecuenciaAlerta = frecuenciaAlerta;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getFrecuenciaAlerta() {
        return frecuenciaAlerta;
    }

    public void setFrecuenciaAlerta(int frecuenciaAlerta) {
        this.frecuenciaAlerta = frecuenciaAlerta;
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
        hash += (idZona != null ? idZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Zona[ idZona=" + idZona + " ]";
    }
    
}
