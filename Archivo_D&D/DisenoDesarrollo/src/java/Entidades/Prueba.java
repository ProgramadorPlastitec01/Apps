/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "prueba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prueba.findAll", query = "SELECT p FROM Prueba p")
    , @NamedQuery(name = "Prueba.findByIdPrueba", query = "SELECT p FROM Prueba p WHERE p.idPrueba = :idPrueba")
    , @NamedQuery(name = "Prueba.findByFchRegistro", query = "SELECT p FROM Prueba p WHERE p.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Prueba.findByUsuRegistro", query = "SELECT p FROM Prueba p WHERE p.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "Prueba.findByPrueba", query = "SELECT p FROM Prueba p WHERE p.prueba = :prueba")
    , @NamedQuery(name = "Prueba.findByTPrueba", query = "SELECT p FROM Prueba p WHERE p.tPrueba = :tPrueba")
    , @NamedQuery(name = "Prueba.findByTCategoria", query = "SELECT p FROM Prueba p WHERE p.tCategoria = :tCategoria")
    , @NamedQuery(name = "Prueba.findByEstado", query = "SELECT p FROM Prueba p WHERE p.estado = :estado")
    , @NamedQuery(name = "Prueba.findByDocumento", query = "SELECT p FROM Prueba p WHERE p.documento = :documento")})
public class Prueba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prueba")
    private Integer idPrueba;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "prueba")
    private String prueba;
    @Column(name = "t_prueba")
    private String tPrueba;
    @Column(name = "t_categoria")
    private String tCategoria;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Column(name = "criterio")
    private String criterio;
    @Column(name = "documento")
    private String documento;
    @OneToMany(mappedBy = "fkPrueba")
    private Collection<PPrueba> pPruebaCollection;

    public Prueba() {
    }

    public Prueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    public Prueba(Integer idPrueba, Date fchRegistro) {
        this.idPrueba = idPrueba;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
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

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public String getTPrueba() {
        return tPrueba;
    }

    public void setTPrueba(String tPrueba) {
        this.tPrueba = tPrueba;
    }

    public String getTCategoria() {
        return tCategoria;
    }

    public void setTCategoria(String tCategoria) {
        this.tCategoria = tCategoria;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @XmlTransient
    public Collection<PPrueba> getPPruebaCollection() {
        return pPruebaCollection;
    }

    public void setPPruebaCollection(Collection<PPrueba> pPruebaCollection) {
        this.pPruebaCollection = pPruebaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrueba != null ? idPrueba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prueba)) {
            return false;
        }
        Prueba other = (Prueba) object;
        if ((this.idPrueba == null && other.idPrueba != null) || (this.idPrueba != null && !this.idPrueba.equals(other.idPrueba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Prueba[ idPrueba=" + idPrueba + " ]";
    }
    
}
