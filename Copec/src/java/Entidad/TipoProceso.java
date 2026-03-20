/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

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
 * @author prog.sistemas2
 */
@Entity
@Table(name = "tipo_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProceso.findAll", query = "SELECT t FROM TipoProceso t"),
    @NamedQuery(name = "TipoProceso.findByIdTipoProceso", query = "SELECT t FROM TipoProceso t WHERE t.idTipoProceso = :idTipoProceso"),
    @NamedQuery(name = "TipoProceso.findByProceso", query = "SELECT t FROM TipoProceso t WHERE t.proceso = :proceso"),
    @NamedQuery(name = "TipoProceso.findByEstado", query = "SELECT t FROM TipoProceso t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoProceso.findByFchRegistro", query = "SELECT t FROM TipoProceso t WHERE t.fchRegistro = :fchRegistro")})
public class TipoProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_proceso")
    private Integer idTipoProceso;
    @Column(name = "proceso")
    private String proceso;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Basic(optional = false)
    @Lob
    @Column(name = "usu_registro")
    private String usuRegistro;
    @OneToMany(mappedBy = "idTipoProceso")
    private Collection<Pregunta> preguntaCollection;

    public TipoProceso() {
    }

    public TipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public TipoProceso(Integer idTipoProceso, int estado, Date fchRegistro, String usuRegistro) {
        this.idTipoProceso = idTipoProceso;
        this.estado = estado;
        this.fchRegistro = fchRegistro;
        this.usuRegistro = usuRegistro;
    }

    public Integer getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    @XmlTransient
    public Collection<Pregunta> getPreguntaCollection() {
        return preguntaCollection;
    }

    public void setPreguntaCollection(Collection<Pregunta> preguntaCollection) {
        this.preguntaCollection = preguntaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoProceso != null ? idTipoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProceso)) {
            return false;
        }
        TipoProceso other = (TipoProceso) object;
        if ((this.idTipoProceso == null && other.idTipoProceso != null) || (this.idTipoProceso != null && !this.idTipoProceso.equals(other.idTipoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.TipoProceso[ idTipoProceso=" + idTipoProceso + " ]";
    }
    
}
