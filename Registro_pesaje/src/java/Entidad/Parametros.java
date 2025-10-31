/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

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
 * @author Programador.TI1
 */
@Entity
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    , @NamedQuery(name = "Parametros.findByIdParametro", query = "SELECT p FROM Parametros p WHERE p.idParametro = :idParametro")
    , @NamedQuery(name = "Parametros.findByCategoria", query = "SELECT p FROM Parametros p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Parametros.findByNombre", query = "SELECT p FROM Parametros p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Parametros.findByValor", query = "SELECT p FROM Parametros p WHERE p.valor = :valor")
    , @NamedQuery(name = "Parametros.findByEstado", query = "SELECT p FROM Parametros p WHERE p.estado = :estado")
    , @NamedQuery(name = "Parametros.findByUsuarioRegistro", query = "SELECT p FROM Parametros p WHERE p.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Parametros.findByFechaRegistros", query = "SELECT p FROM Parametros p WHERE p.fechaRegistros = :fechaRegistros")})
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private Integer valor;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registros")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistros;

    public Parametros() {
    }

    public Parametros(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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

    public Date getFechaRegistros() {
        return fechaRegistros;
    }

    public void setFechaRegistros(Date fechaRegistros) {
        this.fechaRegistros = fechaRegistros;
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
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Parametros[ idParametro=" + idParametro + " ]";
    }
    
}
