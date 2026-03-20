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
@Table(name = "p_prueba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PPrueba.findAll", query = "SELECT p FROM PPrueba p")
    , @NamedQuery(name = "PPrueba.findByIdPPrueba", query = "SELECT p FROM PPrueba p WHERE p.idPPrueba = :idPPrueba")
    , @NamedQuery(name = "PPrueba.findByFchRegistro", query = "SELECT p FROM PPrueba p WHERE p.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "PPrueba.findByUsuRegistro", query = "SELECT p FROM PPrueba p WHERE p.usuRegistro = :usuRegistro")})
public class PPrueba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_p_prueba")
    private Integer idPPrueba;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @JoinColumn(name = "fk_prueba_c", referencedColumnName = "id_prueba_c")
    @ManyToOne
    private PruebaC fkPruebaC;
    @JoinColumn(name = "fk_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto fkProyecto;
    @JoinColumn(name = "fk_prueba", referencedColumnName = "id_prueba")
    @ManyToOne
    private Prueba fkPrueba;

    public PPrueba() {
    }

    public PPrueba(Integer idPPrueba) {
        this.idPPrueba = idPPrueba;
    }

    public PPrueba(Integer idPPrueba, Date fchRegistro) {
        this.idPPrueba = idPPrueba;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdPPrueba() {
        return idPPrueba;
    }

    public void setIdPPrueba(Integer idPPrueba) {
        this.idPPrueba = idPPrueba;
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

    public PruebaC getFkPruebaC() {
        return fkPruebaC;
    }

    public void setFkPruebaC(PruebaC fkPruebaC) {
        this.fkPruebaC = fkPruebaC;
    }

    public Proyecto getFkProyecto() {
        return fkProyecto;
    }

    public void setFkProyecto(Proyecto fkProyecto) {
        this.fkProyecto = fkProyecto;
    }

    public Prueba getFkPrueba() {
        return fkPrueba;
    }

    public void setFkPrueba(Prueba fkPrueba) {
        this.fkPrueba = fkPrueba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPPrueba != null ? idPPrueba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PPrueba)) {
            return false;
        }
        PPrueba other = (PPrueba) object;
        if ((this.idPPrueba == null && other.idPPrueba != null) || (this.idPPrueba != null && !this.idPPrueba.equals(other.idPPrueba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PPrueba[ idPPrueba=" + idPPrueba + " ]";
    }
    
}
