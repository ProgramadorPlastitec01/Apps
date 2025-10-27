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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "aplicativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aplicativo.findAll", query = "SELECT a FROM Aplicativo a")
    , @NamedQuery(name = "Aplicativo.findByIdAplicativo", query = "SELECT a FROM Aplicativo a WHERE a.idAplicativo = :idAplicativo")
    , @NamedQuery(name = "Aplicativo.findByNombre", query = "SELECT a FROM Aplicativo a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Aplicativo.findByEncargado", query = "SELECT a FROM Aplicativo a WHERE a.encargado = :encargado")
    , @NamedQuery(name = "Aplicativo.findByEstado", query = "SELECT a FROM Aplicativo a WHERE a.estado = :estado")
    , @NamedQuery(name = "Aplicativo.findByUsuarioRegistro", query = "SELECT a FROM Aplicativo a WHERE a.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Aplicativo.findByFechaRegistro", query = "SELECT a FROM Aplicativo a WHERE a.fechaRegistro = :fechaRegistro")})
public class Aplicativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aplicativo")
    private Integer idAplicativo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "encargado")
    private String encargado;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Aplicativo() {
    }

    public Aplicativo(Integer idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public Integer getIdAplicativo() {
        return idAplicativo;
    }

    public void setIdAplicativo(Integer idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
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
        hash += (idAplicativo != null ? idAplicativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicativo)) {
            return false;
        }
        Aplicativo other = (Aplicativo) object;
        if ((this.idAplicativo == null && other.idAplicativo != null) || (this.idAplicativo != null && !this.idAplicativo.equals(other.idAplicativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Aplicativo[ idAplicativo=" + idAplicativo + " ]";
    }
    
}
