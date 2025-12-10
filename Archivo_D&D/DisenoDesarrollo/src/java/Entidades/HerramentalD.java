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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "herramental_d")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HerramentalD.findAll", query = "SELECT h FROM HerramentalD h")
    , @NamedQuery(name = "HerramentalD.findByIdHerramentalD", query = "SELECT h FROM HerramentalD h WHERE h.idHerramentalD = :idHerramentalD")
    , @NamedQuery(name = "HerramentalD.findByFchRegistro", query = "SELECT h FROM HerramentalD h WHERE h.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "HerramentalD.findByUsuRegistro", query = "SELECT h FROM HerramentalD h WHERE h.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "HerramentalD.findByTipo", query = "SELECT h FROM HerramentalD h WHERE h.tipo = :tipo")
    , @NamedQuery(name = "HerramentalD.findByCategoria", query = "SELECT h FROM HerramentalD h WHERE h.categoria = :categoria")
    , @NamedQuery(name = "HerramentalD.findByTTitulo", query = "SELECT h FROM HerramentalD h WHERE h.tTitulo = :tTitulo")
    , @NamedQuery(name = "HerramentalD.findByAdjunto", query = "SELECT h FROM HerramentalD h WHERE h.adjunto = :adjunto")})
public class HerramentalD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_herramental_d")
    private Integer idHerramentalD;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "t_titulo")
    private String tTitulo;
    @Lob
    @Column(name = "texto")
    private String texto;
    @Column(name = "adjunto")
    private String adjunto;
    @JoinColumn(name = "fk_herramental_c", referencedColumnName = "id_herramental_c")
    @ManyToOne
    private HerramentalC fkHerramentalC;

    public HerramentalD() {
    }

    public HerramentalD(Integer idHerramentalD) {
        this.idHerramentalD = idHerramentalD;
    }

    public HerramentalD(Integer idHerramentalD, Date fchRegistro) {
        this.idHerramentalD = idHerramentalD;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdHerramentalD() {
        return idHerramentalD;
    }

    public void setIdHerramentalD(Integer idHerramentalD) {
        this.idHerramentalD = idHerramentalD;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTTitulo() {
        return tTitulo;
    }

    public void setTTitulo(String tTitulo) {
        this.tTitulo = tTitulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public HerramentalC getFkHerramentalC() {
        return fkHerramentalC;
    }

    public void setFkHerramentalC(HerramentalC fkHerramentalC) {
        this.fkHerramentalC = fkHerramentalC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHerramentalD != null ? idHerramentalD.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HerramentalD)) {
            return false;
        }
        HerramentalD other = (HerramentalD) object;
        if ((this.idHerramentalD == null && other.idHerramentalD != null) || (this.idHerramentalD != null && !this.idHerramentalD.equals(other.idHerramentalD))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HerramentalD[ idHerramentalD=" + idHerramentalD + " ]";
    }
    
}
