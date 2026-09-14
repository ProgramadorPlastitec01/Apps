/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "formula")
@NamedQueries({
    @NamedQuery(name = "Formula.findAll", query = "SELECT f FROM Formula f"),
    @NamedQuery(name = "Formula.findByIdFormula", query = "SELECT f FROM Formula f WHERE f.idFormula = :idFormula"),
    @NamedQuery(name = "Formula.findByNombreFormula", query = "SELECT f FROM Formula f WHERE f.nombreFormula = :nombreFormula"),
    @NamedQuery(name = "Formula.findByEstado", query = "SELECT f FROM Formula f WHERE f.estado = :estado"),
    @NamedQuery(name = "Formula.findByUsuarioRegistro", query = "SELECT f FROM Formula f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Formula.findByFechaRegitro", query = "SELECT f FROM Formula f WHERE f.fechaRegitro = :fechaRegitro")})
public class Formula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formula")
    private Integer idFormula;
    @Column(name = "nombre_formula")
    private String nombreFormula;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_regitro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegitro;
    @OneToMany(mappedBy = "formula")
    private Collection<Registro> registroCollection;
    @OneToMany(mappedBy = "formula")
    private Collection<FormulaMateriaPrima> formulaMateriaPrimaCollection;

    public Formula() {
    }

    public Formula(Integer idFormula) {
        this.idFormula = idFormula;
    }

    public Integer getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(Integer idFormula) {
        this.idFormula = idFormula;
    }

    public String getNombreFormula() {
        return nombreFormula;
    }

    public void setNombreFormula(String nombreFormula) {
        this.nombreFormula = nombreFormula;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegitro() {
        return fechaRegitro;
    }

    public void setFechaRegitro(Date fechaRegitro) {
        this.fechaRegitro = fechaRegitro;
    }

    public Collection<Registro> getRegistroCollection() {
        return registroCollection;
    }

    public void setRegistroCollection(Collection<Registro> registroCollection) {
        this.registroCollection = registroCollection;
    }

    public Collection<FormulaMateriaPrima> getFormulaMateriaPrimaCollection() {
        return formulaMateriaPrimaCollection;
    }

    public void setFormulaMateriaPrimaCollection(Collection<FormulaMateriaPrima> formulaMateriaPrimaCollection) {
        this.formulaMateriaPrimaCollection = formulaMateriaPrimaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormula != null ? idFormula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formula)) {
            return false;
        }
        Formula other = (Formula) object;
        if ((this.idFormula == null && other.idFormula != null) || (this.idFormula != null && !this.idFormula.equals(other.idFormula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formula[idFormula=" + idFormula + "]";
    }

}
