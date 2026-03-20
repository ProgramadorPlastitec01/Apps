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
@Table(name = "memoria_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemoriaC.findAll", query = "SELECT m FROM MemoriaC m")
    , @NamedQuery(name = "MemoriaC.findByIdMemoriaC", query = "SELECT m FROM MemoriaC m WHERE m.idMemoriaC = :idMemoriaC")
    , @NamedQuery(name = "MemoriaC.findByFchRegistro", query = "SELECT m FROM MemoriaC m WHERE m.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "MemoriaC.findByUsuRegistro", query = "SELECT m FROM MemoriaC m WHERE m.usuRegistro = :usuRegistro")})
public class MemoriaC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_memoria_c")
    private Integer idMemoriaC;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @JoinColumn(name = "fk_etapa", referencedColumnName = "id_etapa")
    @ManyToOne
    private Etapa fkEtapa;
    @JoinColumn(name = "fk_fase", referencedColumnName = "id_fase")
    @ManyToOne
    private Fase fkFase;
    @JoinColumn(name = "fk_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto fkProyecto;
    @OneToMany(mappedBy = "fkMemoriaC")
    private Collection<MemoriaD> memoriaDCollection;

    public MemoriaC() {
    }

    public MemoriaC(Integer idMemoriaC) {
        this.idMemoriaC = idMemoriaC;
    }

    public MemoriaC(Integer idMemoriaC, Date fchRegistro) {
        this.idMemoriaC = idMemoriaC;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdMemoriaC() {
        return idMemoriaC;
    }

    public void setIdMemoriaC(Integer idMemoriaC) {
        this.idMemoriaC = idMemoriaC;
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

    public Etapa getFkEtapa() {
        return fkEtapa;
    }

    public void setFkEtapa(Etapa fkEtapa) {
        this.fkEtapa = fkEtapa;
    }

    public Fase getFkFase() {
        return fkFase;
    }

    public void setFkFase(Fase fkFase) {
        this.fkFase = fkFase;
    }

    public Proyecto getFkProyecto() {
        return fkProyecto;
    }

    public void setFkProyecto(Proyecto fkProyecto) {
        this.fkProyecto = fkProyecto;
    }

    @XmlTransient
    public Collection<MemoriaD> getMemoriaDCollection() {
        return memoriaDCollection;
    }

    public void setMemoriaDCollection(Collection<MemoriaD> memoriaDCollection) {
        this.memoriaDCollection = memoriaDCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMemoriaC != null ? idMemoriaC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemoriaC)) {
            return false;
        }
        MemoriaC other = (MemoriaC) object;
        if ((this.idMemoriaC == null && other.idMemoriaC != null) || (this.idMemoriaC != null && !this.idMemoriaC.equals(other.idMemoriaC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MemoriaC[ idMemoriaC=" + idMemoriaC + " ]";
    }
    
}
