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
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    , @NamedQuery(name = "Parametros.findByIdParametros", query = "SELECT p FROM Parametros p WHERE p.idParametros = :idParametros")
    , @NamedQuery(name = "Parametros.findByCategoria", query = "SELECT p FROM Parametros p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Parametros.findByValor", query = "SELECT p FROM Parametros p WHERE p.valor = :valor")
    , @NamedQuery(name = "Parametros.findByDescripcion", query = "SELECT p FROM Parametros p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Parametros.findByEstado", query = "SELECT p FROM Parametros p WHERE p.estado = :estado")
    , @NamedQuery(name = "Parametros.findByUsuarioRegistro", query = "SELECT p FROM Parametros p WHERE p.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Parametros.findByFechaRegistro", query = "SELECT p FROM Parametros p WHERE p.fechaRegistro = :fechaRegistro")})
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_parametros")
    private Integer idParametros;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "valor")
    private String valor;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private String estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Parametros() {
    }

    public Parametros(Integer idParametros) {
        this.idParametros = idParametros;
    }

    public Integer getIdParametros() {
        return idParametros;
    }

    public void setIdParametros(Integer idParametros) {
        this.idParametros = idParametros;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        hash += (idParametros != null ? idParametros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.idParametros == null && other.idParametros != null) || (this.idParametros != null && !this.idParametros.equals(other.idParametros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametros[ idParametros=" + idParametros + " ]";
    }
    
}
