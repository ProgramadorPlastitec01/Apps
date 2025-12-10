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
@Table(name = "proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
    , @NamedQuery(name = "Proyecto.findByIdProyecto", query = "SELECT p FROM Proyecto p WHERE p.idProyecto = :idProyecto")
    , @NamedQuery(name = "Proyecto.findByFchRegistro", query = "SELECT p FROM Proyecto p WHERE p.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Proyecto.findByUsuRegistro", query = "SELECT p FROM Proyecto p WHERE p.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "Proyecto.findByFchEntrada", query = "SELECT p FROM Proyecto p WHERE p.fchEntrada = :fchEntrada")
    , @NamedQuery(name = "Proyecto.findByFchSalida", query = "SELECT p FROM Proyecto p WHERE p.fchSalida = :fchSalida")
    , @NamedQuery(name = "Proyecto.findByNumero", query = "SELECT p FROM Proyecto p WHERE p.numero = :numero")
    , @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado")
    , @NamedQuery(name = "Proyecto.findByTipoProyecto", query = "SELECT p FROM Proyecto p WHERE p.tipoProyecto = :tipoProyecto")
    , @NamedQuery(name = "Proyecto.findByTEntrada", query = "SELECT p FROM Proyecto p WHERE p.tEntrada = :tEntrada")
    , @NamedQuery(name = "Proyecto.findByAdjunto", query = "SELECT p FROM Proyecto p WHERE p.adjunto = :adjunto")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fch_entrada")
    @Temporal(TemporalType.DATE)
    private Date fchEntrada;
    @Column(name = "fch_salida")
    private String fchSalida;
    @Column(name = "numero")
    private String numero;
    @Lob
    @Column(name = "proyecto")
    private String proyecto;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Column(name = "participe")
    private String participe;
    @Lob
    @Column(name = "uso_previsto")
    private String usoPrevisto;
    @Column(name = "tipo_proyecto")
    private Short tipoProyecto;
    @Column(name = "t_entrada")
    private String tEntrada;
    @Column(name = "adjunto")
    private String adjunto;
    @OneToMany(mappedBy = "fkProyecto")
    private Collection<PPrueba> pPruebaCollection;
    @OneToMany(mappedBy = "fkProyecto")
    private Collection<HerramentalC> herramentalCCollection;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<Adjunto> adjuntoCollection;
    @OneToMany(mappedBy = "fkProyecto")
    private Collection<MemoriaC> memoriaCCollection;
    @OneToMany(mappedBy = "fkProyecto")
    private Collection<FormulaC> formulaCCollection;
    @OneToMany(mappedBy = "fkProyecto")
    private Collection<PruebaC> pruebaCCollection;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto(Integer idProyecto, Date fchRegistro) {
        this.idProyecto = idProyecto;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
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

    public Date getFchEntrada() {
        return fchEntrada;
    }

    public void setFchEntrada(Date fchEntrada) {
        this.fchEntrada = fchEntrada;
    }

    public String getFchSalida() {
        return fchSalida;
    }

    public void setFchSalida(String fchSalida) {
        this.fchSalida = fchSalida;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getParticipe() {
        return participe;
    }

    public void setParticipe(String participe) {
        this.participe = participe;
    }

    public String getUsoPrevisto() {
        return usoPrevisto;
    }

    public void setUsoPrevisto(String usoPrevisto) {
        this.usoPrevisto = usoPrevisto;
    }

    public Short getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(Short tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getTEntrada() {
        return tEntrada;
    }

    public void setTEntrada(String tEntrada) {
        this.tEntrada = tEntrada;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    @XmlTransient
    public Collection<PPrueba> getPPruebaCollection() {
        return pPruebaCollection;
    }

    public void setPPruebaCollection(Collection<PPrueba> pPruebaCollection) {
        this.pPruebaCollection = pPruebaCollection;
    }

    @XmlTransient
    public Collection<HerramentalC> getHerramentalCCollection() {
        return herramentalCCollection;
    }

    public void setHerramentalCCollection(Collection<HerramentalC> herramentalCCollection) {
        this.herramentalCCollection = herramentalCCollection;
    }

    @XmlTransient
    public Collection<Adjunto> getAdjuntoCollection() {
        return adjuntoCollection;
    }

    public void setAdjuntoCollection(Collection<Adjunto> adjuntoCollection) {
        this.adjuntoCollection = adjuntoCollection;
    }

    @XmlTransient
    public Collection<MemoriaC> getMemoriaCCollection() {
        return memoriaCCollection;
    }

    public void setMemoriaCCollection(Collection<MemoriaC> memoriaCCollection) {
        this.memoriaCCollection = memoriaCCollection;
    }

    @XmlTransient
    public Collection<FormulaC> getFormulaCCollection() {
        return formulaCCollection;
    }

    public void setFormulaCCollection(Collection<FormulaC> formulaCCollection) {
        this.formulaCCollection = formulaCCollection;
    }

    @XmlTransient
    public Collection<PruebaC> getPruebaCCollection() {
        return pruebaCCollection;
    }

    public void setPruebaCCollection(Collection<PruebaC> pruebaCCollection) {
        this.pruebaCCollection = pruebaCCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Proyecto[ idProyecto=" + idProyecto + " ]";
    }
    
}
