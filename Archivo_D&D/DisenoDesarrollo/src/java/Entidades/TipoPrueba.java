/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "tipo_prueba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPrueba.findAll", query = "SELECT t FROM TipoPrueba t")
    , @NamedQuery(name = "TipoPrueba.findByIdTipoPrueba", query = "SELECT t FROM TipoPrueba t WHERE t.idTipoPrueba = :idTipoPrueba")
    , @NamedQuery(name = "TipoPrueba.findByTipoPrueba", query = "SELECT t FROM TipoPrueba t WHERE t.tipoPrueba = :tipoPrueba")
    , @NamedQuery(name = "TipoPrueba.findBySigla", query = "SELECT t FROM TipoPrueba t WHERE t.sigla = :sigla")})
public class TipoPrueba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_prueba")
    private Integer idTipoPrueba;
    @Column(name = "tipo_prueba")
    private String tipoPrueba;
    @Column(name = "sigla")
    private String sigla;

    public TipoPrueba() {
    }

    public TipoPrueba(Integer idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    public Integer getIdTipoPrueba() {
        return idTipoPrueba;
    }

    public void setIdTipoPrueba(Integer idTipoPrueba) {
        this.idTipoPrueba = idTipoPrueba;
    }

    public String getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(String tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPrueba != null ? idTipoPrueba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPrueba)) {
            return false;
        }
        TipoPrueba other = (TipoPrueba) object;
        if ((this.idTipoPrueba == null && other.idTipoPrueba != null) || (this.idTipoPrueba != null && !this.idTipoPrueba.equals(other.idTipoPrueba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoPrueba[ idTipoPrueba=" + idTipoPrueba + " ]";
    }
    
}
