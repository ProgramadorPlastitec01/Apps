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
@Table(name = "etapa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e")
    , @NamedQuery(name = "Etapa.findByIdEtapa", query = "SELECT e FROM Etapa e WHERE e.idEtapa = :idEtapa")
    , @NamedQuery(name = "Etapa.findByFchRegistro", query = "SELECT e FROM Etapa e WHERE e.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Etapa.findByUsuRegistro", query = "SELECT e FROM Etapa e WHERE e.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "Etapa.findByNumero", query = "SELECT e FROM Etapa e WHERE e.numero = :numero")
    , @NamedQuery(name = "Etapa.findByNorma", query = "SELECT e FROM Etapa e WHERE e.norma = :norma")
    , @NamedQuery(name = "Etapa.findByEstado", query = "SELECT e FROM Etapa e WHERE e.estado = :estado")})
public class Etapa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_etapa")
    private Integer idEtapa;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "numero")
    private String numero;
    @Lob
    @Column(name = "etapa")
    private String etapa;
    @Column(name = "norma")
    private String norma;
    @Lob
    @Column(name = "guia_norma")
    private String guiaNorma;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(mappedBy = "fkEtapa")
    private Collection<Fase> faseCollection;
    @OneToMany(mappedBy = "fkEtapa")
    private Collection<MemoriaC> memoriaCCollection;

    public Etapa() {
    }

    public Etapa(Integer idEtapa) {
        this.idEtapa = idEtapa;
    }

    public Etapa(Integer idEtapa, Date fchRegistro) {
        this.idEtapa = idEtapa;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(Integer idEtapa) {
        this.idEtapa = idEtapa;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public String getGuiaNorma() {
        return guiaNorma;
    }

    public void setGuiaNorma(String guiaNorma) {
        this.guiaNorma = guiaNorma;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Fase> getFaseCollection() {
        return faseCollection;
    }

    public void setFaseCollection(Collection<Fase> faseCollection) {
        this.faseCollection = faseCollection;
    }

    @XmlTransient
    public Collection<MemoriaC> getMemoriaCCollection() {
        return memoriaCCollection;
    }

    public void setMemoriaCCollection(Collection<MemoriaC> memoriaCCollection) {
        this.memoriaCCollection = memoriaCCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtapa != null ? idEtapa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etapa)) {
            return false;
        }
        Etapa other = (Etapa) object;
        if ((this.idEtapa == null && other.idEtapa != null) || (this.idEtapa != null && !this.idEtapa.equals(other.idEtapa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Etapa[ idEtapa=" + idEtapa + " ]";
    }
    
}
