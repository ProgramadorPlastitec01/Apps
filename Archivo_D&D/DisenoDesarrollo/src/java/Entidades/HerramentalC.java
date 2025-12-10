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
@Table(name = "herramental_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HerramentalC.findAll", query = "SELECT h FROM HerramentalC h")
    , @NamedQuery(name = "HerramentalC.findByIdHerramentalC", query = "SELECT h FROM HerramentalC h WHERE h.idHerramentalC = :idHerramentalC")
    , @NamedQuery(name = "HerramentalC.findByFchRegistro", query = "SELECT h FROM HerramentalC h WHERE h.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "HerramentalC.findByUsuRegistro", query = "SELECT h FROM HerramentalC h WHERE h.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "HerramentalC.findByHerramental", query = "SELECT h FROM HerramentalC h WHERE h.herramental = :herramental")
    , @NamedQuery(name = "HerramentalC.findByNumeroHerramental", query = "SELECT h FROM HerramentalC h WHERE h.numeroHerramental = :numeroHerramental")
    , @NamedQuery(name = "HerramentalC.findByNumeroPlano", query = "SELECT h FROM HerramentalC h WHERE h.numeroPlano = :numeroPlano")
    , @NamedQuery(name = "HerramentalC.findByFchSolicitud", query = "SELECT h FROM HerramentalC h WHERE h.fchSolicitud = :fchSolicitud")
    , @NamedQuery(name = "HerramentalC.findByTiempoEstimado", query = "SELECT h FROM HerramentalC h WHERE h.tiempoEstimado = :tiempoEstimado")
    , @NamedQuery(name = "HerramentalC.findByTipo", query = "SELECT h FROM HerramentalC h WHERE h.tipo = :tipo")
    , @NamedQuery(name = "HerramentalC.findByNumeroTipo", query = "SELECT h FROM HerramentalC h WHERE h.numeroTipo = :numeroTipo")
    , @NamedQuery(name = "HerramentalC.findByAprobo", query = "SELECT h FROM HerramentalC h WHERE h.aprobo = :aprobo")
    , @NamedQuery(name = "HerramentalC.findByEstado", query = "SELECT h FROM HerramentalC h WHERE h.estado = :estado")
    , @NamedQuery(name = "HerramentalC.findByVersionR", query = "SELECT h FROM HerramentalC h WHERE h.versionR = :versionR")})
public class HerramentalC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_herramental_c")
    private Integer idHerramentalC;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "herramental")
    private String herramental;
    @Column(name = "numero_herramental")
    private String numeroHerramental;
    @Column(name = "numero_plano")
    private String numeroPlano;
    @Column(name = "fch_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fchSolicitud;
    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "numero_tipo")
    private String numeroTipo;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "aprobo")
    private String aprobo;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "version_r")
    private String versionR;
    @JoinColumn(name = "fk_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto fkProyecto;
    @OneToMany(mappedBy = "fkHerramentalC")
    private Collection<HerramentalD> herramentalDCollection;

    public HerramentalC() {
    }

    public HerramentalC(Integer idHerramentalC) {
        this.idHerramentalC = idHerramentalC;
    }

    public Integer getIdHerramentalC() {
        return idHerramentalC;
    }

    public void setIdHerramentalC(Integer idHerramentalC) {
        this.idHerramentalC = idHerramentalC;
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

    public String getHerramental() {
        return herramental;
    }

    public void setHerramental(String herramental) {
        this.herramental = herramental;
    }

    public String getNumeroHerramental() {
        return numeroHerramental;
    }

    public void setNumeroHerramental(String numeroHerramental) {
        this.numeroHerramental = numeroHerramental;
    }

    public String getNumeroPlano() {
        return numeroPlano;
    }

    public void setNumeroPlano(String numeroPlano) {
        this.numeroPlano = numeroPlano;
    }

    public Date getFchSolicitud() {
        return fchSolicitud;
    }

    public void setFchSolicitud(Date fchSolicitud) {
        this.fchSolicitud = fchSolicitud;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroTipo() {
        return numeroTipo;
    }

    public void setNumeroTipo(String numeroTipo) {
        this.numeroTipo = numeroTipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getAprobo() {
        return aprobo;
    }

    public void setAprobo(String aprobo) {
        this.aprobo = aprobo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getVersionR() {
        return versionR;
    }

    public void setVersionR(String versionR) {
        this.versionR = versionR;
    }

    public Proyecto getFkProyecto() {
        return fkProyecto;
    }

    public void setFkProyecto(Proyecto fkProyecto) {
        this.fkProyecto = fkProyecto;
    }

    @XmlTransient
    public Collection<HerramentalD> getHerramentalDCollection() {
        return herramentalDCollection;
    }

    public void setHerramentalDCollection(Collection<HerramentalD> herramentalDCollection) {
        this.herramentalDCollection = herramentalDCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHerramentalC != null ? idHerramentalC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HerramentalC)) {
            return false;
        }
        HerramentalC other = (HerramentalC) object;
        if ((this.idHerramentalC == null && other.idHerramentalC != null) || (this.idHerramentalC != null && !this.idHerramentalC.equals(other.idHerramentalC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HerramentalC[ idHerramentalC=" + idHerramentalC + " ]";
    }
    
}
