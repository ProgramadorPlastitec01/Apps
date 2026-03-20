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
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdCategoria", query = "SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria")
    , @NamedQuery(name = "Categoria.findByFchRegistro", query = "SELECT c FROM Categoria c WHERE c.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Categoria.findByUsuRegistro", query = "SELECT c FROM Categoria c WHERE c.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "Categoria.findByTCategoria", query = "SELECT c FROM Categoria c WHERE c.tCategoria = :tCategoria")
    , @NamedQuery(name = "Categoria.findByCategoria", query = "SELECT c FROM Categoria c WHERE c.categoria = :categoria")
    , @NamedQuery(name = "Categoria.findByTCampo", query = "SELECT c FROM Categoria c WHERE c.tCampo = :tCampo")
    , @NamedQuery(name = "Categoria.findByEstado", query = "SELECT c FROM Categoria c WHERE c.estado = :estado")
    , @NamedQuery(name = "Categoria.findByTArchivo", query = "SELECT c FROM Categoria c WHERE c.tArchivo = :tArchivo")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "t_categoria")
    private String tCategoria;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "t_campo")
    private String tCampo;
    @Lob
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "t_archivo")
    private Integer tArchivo;

    public Categoria() {
    }

    public Categoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria(Integer idCategoria, Date fchRegistro) {
        this.idCategoria = idCategoria;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public String getTCategoria() {
        return tCategoria;
    }

    public void setTCategoria(String tCategoria) {
        this.tCategoria = tCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTCampo() {
        return tCampo;
    }

    public void setTCampo(String tCampo) {
        this.tCampo = tCampo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTArchivo() {
        return tArchivo;
    }

    public void setTArchivo(Integer tArchivo) {
        this.tArchivo = tArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Categoria[ idCategoria=" + idCategoria + " ]";
    }
    
}
