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
@Table(name = "prueba_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PruebaC.findAll", query = "SELECT p FROM PruebaC p")
    , @NamedQuery(name = "PruebaC.findByIdPruebaC", query = "SELECT p FROM PruebaC p WHERE p.idPruebaC = :idPruebaC")
    , @NamedQuery(name = "PruebaC.findByFchRegistro", query = "SELECT p FROM PruebaC p WHERE p.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "PruebaC.findByUsuRegistro", query = "SELECT p FROM PruebaC p WHERE p.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "PruebaC.findByProgramacion", query = "SELECT p FROM PruebaC p WHERE p.programacion = :programacion")
    , @NamedQuery(name = "PruebaC.findByFchInicial", query = "SELECT p FROM PruebaC p WHERE p.fchInicial = :fchInicial")
    , @NamedQuery(name = "PruebaC.findByConsecutivo", query = "SELECT p FROM PruebaC p WHERE p.consecutivo = :consecutivo")
    , @NamedQuery(name = "PruebaC.findByEstado", query = "SELECT p FROM PruebaC p WHERE p.estado = :estado")
    , @NamedQuery(name = "PruebaC.findByTProgramacion", query = "SELECT p FROM PruebaC p WHERE p.tProgramacion = :tProgramacion")
    , @NamedQuery(name = "PruebaC.findByFchVerifica", query = "SELECT p FROM PruebaC p WHERE p.fchVerifica = :fchVerifica")
    , @NamedQuery(name = "PruebaC.findByVerifica", query = "SELECT p FROM PruebaC p WHERE p.verifica = :verifica")})
public class PruebaC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prueba_c")
    private Integer idPruebaC;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "programacion")
    private String programacion;
    @Column(name = "fch_inicial")
    @Temporal(TemporalType.DATE)
    private Date fchInicial;
    @Column(name = "consecutivo")
    private String consecutivo;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "t_programacion")
    private String tProgramacion;
    @Column(name = "fch_verifica")
    @Temporal(TemporalType.DATE)
    private Date fchVerifica;
    @Column(name = "verifica")
    private String verifica;
    @OneToMany(mappedBy = "fkPruebaC")
    private Collection<PPrueba> pPruebaCollection;
    @JoinColumn(name = "fk_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto fkProyecto;

    public PruebaC() {
    }

    public PruebaC(Integer idPruebaC) {
        this.idPruebaC = idPruebaC;
    }

    public PruebaC(Integer idPruebaC, Date fchRegistro) {
        this.idPruebaC = idPruebaC;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdPruebaC() {
        return idPruebaC;
    }

    public void setIdPruebaC(Integer idPruebaC) {
        this.idPruebaC = idPruebaC;
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

    public String getProgramacion() {
        return programacion;
    }

    public void setProgramacion(String programacion) {
        this.programacion = programacion;
    }

    public Date getFchInicial() {
        return fchInicial;
    }

    public void setFchInicial(Date fchInicial) {
        this.fchInicial = fchInicial;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTProgramacion() {
        return tProgramacion;
    }

    public void setTProgramacion(String tProgramacion) {
        this.tProgramacion = tProgramacion;
    }

    public Date getFchVerifica() {
        return fchVerifica;
    }

    public void setFchVerifica(Date fchVerifica) {
        this.fchVerifica = fchVerifica;
    }

    public String getVerifica() {
        return verifica;
    }

    public void setVerifica(String verifica) {
        this.verifica = verifica;
    }

    @XmlTransient
    public Collection<PPrueba> getPPruebaCollection() {
        return pPruebaCollection;
    }

    public void setPPruebaCollection(Collection<PPrueba> pPruebaCollection) {
        this.pPruebaCollection = pPruebaCollection;
    }

    public Proyecto getFkProyecto() {
        return fkProyecto;
    }

    public void setFkProyecto(Proyecto fkProyecto) {
        this.fkProyecto = fkProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPruebaC != null ? idPruebaC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PruebaC)) {
            return false;
        }
        PruebaC other = (PruebaC) object;
        if ((this.idPruebaC == null && other.idPruebaC != null) || (this.idPruebaC != null && !this.idPruebaC.equals(other.idPruebaC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PruebaC[ idPruebaC=" + idPruebaC + " ]";
    }
    
}
