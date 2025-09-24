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
 * @author Programador.TI1
 */
@Entity
@Table(name = "correo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correo.findAll", query = "SELECT c FROM Correo c")
    , @NamedQuery(name = "Correo.findByIdCorreo", query = "SELECT c FROM Correo c WHERE c.idCorreo = :idCorreo")
    , @NamedQuery(name = "Correo.findByFuncion", query = "SELECT c FROM Correo c WHERE c.funcion = :funcion")
    , @NamedQuery(name = "Correo.findByEmisor", query = "SELECT c FROM Correo c WHERE c.emisor = :emisor")
    , @NamedQuery(name = "Correo.findByPassword", query = "SELECT c FROM Correo c WHERE c.password = :password")
    , @NamedQuery(name = "Correo.findByHost", query = "SELECT c FROM Correo c WHERE c.host = :host")
    , @NamedQuery(name = "Correo.findByPort", query = "SELECT c FROM Correo c WHERE c.port = :port")
    , @NamedQuery(name = "Correo.findByEstado", query = "SELECT c FROM Correo c WHERE c.estado = :estado")
    , @NamedQuery(name = "Correo.findByUsuarioRegistro", query = "SELECT c FROM Correo c WHERE c.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Correo.findByFechaRegistro", query = "SELECT c FROM Correo c WHERE c.fechaRegistro = :fechaRegistro")})
public class Correo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo")
    private Integer idCorreo;
    @Basic(optional = false)
    @Column(name = "funcion")
    private String funcion;
    @Column(name = "emisor")
    private String emisor;
    @Column(name = "password")
    private String password;
    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private Integer port;
    @Lob
    @Column(name = "receptor")
    private String receptor;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Correo() {
    }

    public Correo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public Correo(Integer idCorreo, String funcion) {
        this.idCorreo = idCorreo;
        this.funcion = funcion;
    }

    public Integer getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
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
        hash += (idCorreo != null ? idCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correo)) {
            return false;
        }
        Correo other = (Correo) object;
        if ((this.idCorreo == null && other.idCorreo != null) || (this.idCorreo != null && !this.idCorreo.equals(other.idCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Correo[ idCorreo=" + idCorreo + " ]";
    }
    
}
