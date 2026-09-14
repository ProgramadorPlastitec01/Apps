/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "formula_materia_prima")
@NamedQueries({
    @NamedQuery(name = "FormulaMateriaPrima.findAll", query = "SELECT f FROM FormulaMateriaPrima f"),
    @NamedQuery(name = "FormulaMateriaPrima.findByIdFormulaMateriaPrima", query = "SELECT f FROM FormulaMateriaPrima f WHERE f.idFormulaMateriaPrima = :idFormulaMateriaPrima"),
    @NamedQuery(name = "FormulaMateriaPrima.findByEstado", query = "SELECT f FROM FormulaMateriaPrima f WHERE f.estado = :estado"),
    @NamedQuery(name = "FormulaMateriaPrima.findByUsuarioRegistro", query = "SELECT f FROM FormulaMateriaPrima f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "FormulaMateriaPrima.findByFechaRegistro", query = "SELECT f FROM FormulaMateriaPrima f WHERE f.fechaRegistro = :fechaRegistro")})
public class FormulaMateriaPrima implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formula_materia_prima")
    private Integer idFormulaMateriaPrima;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_materia_prima", referencedColumnName = "id_materia_prima")
    @ManyToOne
    private MateriaPrima materiaPrima;
    @JoinColumn(name = "id_formula", referencedColumnName = "id_formula")
    @ManyToOne
    private Formula formula;

    public FormulaMateriaPrima() {
    }

    public FormulaMateriaPrima(Integer idFormulaMateriaPrima) {
        this.idFormulaMateriaPrima = idFormulaMateriaPrima;
    }

    public Integer getIdFormulaMateriaPrima() {
        return idFormulaMateriaPrima;
    }

    public void setIdFormulaMateriaPrima(Integer idFormulaMateriaPrima) {
        this.idFormulaMateriaPrima = idFormulaMateriaPrima;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulaMateriaPrima != null ? idFormulaMateriaPrima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaMateriaPrima)) {
            return false;
        }
        FormulaMateriaPrima other = (FormulaMateriaPrima) object;
        if ((this.idFormulaMateriaPrima == null && other.idFormulaMateriaPrima != null) || (this.idFormulaMateriaPrima != null && !this.idFormulaMateriaPrima.equals(other.idFormulaMateriaPrima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FormulaMateriaPrima[idFormulaMateriaPrima=" + idFormulaMateriaPrima + "]";
    }

}
