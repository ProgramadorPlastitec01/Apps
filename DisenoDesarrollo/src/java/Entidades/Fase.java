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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "fase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fase.findAll", query = "SELECT f FROM Fase f")
    , @NamedQuery(name = "Fase.findByIdFase", query = "SELECT f FROM Fase f WHERE f.idFase = :idFase")
    , @NamedQuery(name = "Fase.findByFchRegistro", query = "SELECT f FROM Fase f WHERE f.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Fase.findByUsuRegistro", query = "SELECT f FROM Fase f WHERE f.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "Fase.findByLetra", query = "SELECT f FROM Fase f WHERE f.letra = :letra")
    , @NamedQuery(name = "Fase.findByEstado", query = "SELECT f FROM Fase f WHERE f.estado = :estado")
    , @NamedQuery(name = "Fase.findByNorma", query = "SELECT f FROM Fase f WHERE f.norma = :norma")})
public class Fase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fase")
    private Integer idFase;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "letra")
    private String letra;
    @Lob
    @Column(name = "fase")
    private String fase;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "norma")
    private String norma;
    @JoinColumn(name = "fk_etapa", referencedColumnName = "id_etapa")
    @ManyToOne
    private Etapa fkEtapa;
    @OneToMany(mappedBy = "fkFase")
    private Collection<MemoriaC> memoriaCCollection;

    public Fase() {
    }

    public Fase(Integer idFase) {
        this.idFase = idFase;
    }

    public Fase(Integer idFase, Date fchRegistro) {
        this.idFase = idFase;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdFase() {
        return idFase;
    }

    public void setIdFase(Integer idFase) {
        this.idFase = idFase;
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

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public Etapa getFkEtapa() {
        return fkEtapa;
    }

    public void setFkEtapa(Etapa fkEtapa) {
        this.fkEtapa = fkEtapa;
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
        hash += (idFase != null ? idFase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fase)) {
            return false;
        }
        Fase other = (Fase) object;
        if ((this.idFase == null && other.idFase != null) || (this.idFase != null && !this.idFase.equals(other.idFase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Fase[ idFase=" + idFase + " ]";
    }
    
}
