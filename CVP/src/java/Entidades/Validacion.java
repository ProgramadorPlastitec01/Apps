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
@Table(name = "validacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Validacion.findAll", query = "SELECT v FROM Validacion v"),
    @NamedQuery(name = "Validacion.findByIdValidacion", query = "SELECT v FROM Validacion v WHERE v.idValidacion = :idValidacion"),
    @NamedQuery(name = "Validacion.findByFechaValidacion", query = "SELECT v FROM Validacion v WHERE v.fechaValidacion = :fechaValidacion"),
    @NamedQuery(name = "Validacion.findByEstado", query = "SELECT v FROM Validacion v WHERE v.estado = :estado"),
    @NamedQuery(name = "Validacion.findByUsuarioRegistro", query = "SELECT v FROM Validacion v WHERE v.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Validacion.findByFechaRegistro", query = "SELECT v FROM Validacion v WHERE v.fechaRegistro = :fechaRegistro")})
public class Validacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_validacion")
    private Integer idValidacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "contenido")
    private String contenido;
    @Lob
    @Column(name = "informe")
    private String informe;
    @Column(name = "fecha_validacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Validacion() {
    }

    public Validacion(Integer idValidacion) {
        this.idValidacion = idValidacion;
    }

    public Validacion(Integer idValidacion, String contenido) {
        this.idValidacion = idValidacion;
        this.contenido = contenido;
    }

    public Integer getIdValidacion() {
        return idValidacion;
    }

    public void setIdValidacion(Integer idValidacion) {
        this.idValidacion = idValidacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValidacion != null ? idValidacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Validacion)) {
            return false;
        }
        Validacion other = (Validacion) object;
        if ((this.idValidacion == null && other.idValidacion != null) || (this.idValidacion != null && !this.idValidacion.equals(other.idValidacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Validacion[ idValidacion=" + idValidacion + " ]";
    }
    
}
