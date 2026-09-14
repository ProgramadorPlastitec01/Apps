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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "materia_prima")
@NamedQueries({
    @NamedQuery(name = "MateriaPrima.findAll", query = "SELECT m FROM MateriaPrima m"),
    @NamedQuery(name = "MateriaPrima.findByIdMateriaPrima", query = "SELECT m FROM MateriaPrima m WHERE m.idMateriaPrima = :idMateriaPrima"),
    @NamedQuery(name = "MateriaPrima.findByNombreMateria", query = "SELECT m FROM MateriaPrima m WHERE m.nombreMateria = :nombreMateria"),
    @NamedQuery(name = "MateriaPrima.findByEstado", query = "SELECT m FROM MateriaPrima m WHERE m.estado = :estado"),
    @NamedQuery(name = "MateriaPrima.findByUsuarioRegistro", query = "SELECT m FROM MateriaPrima m WHERE m.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "MateriaPrima.findByFechaRegistro", query = "SELECT m FROM MateriaPrima m WHERE m.fechaRegistro = :fechaRegistro")})
public class MateriaPrima implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_materia_prima")
    private Integer idMateriaPrima;
    @Column(name = "nombre_materia")
    private String nombreMateria;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "materiaPrima")
    private Collection<FormulaMateriaPrima> formulaMateriaPrimaCollection;
    @JoinColumn(name = "id_tipo_materia_prima", referencedColumnName = "id_tipo_materia_prima")
    @ManyToOne
    private TipoMateriaPrima tipoMateriaPrima;

    public MateriaPrima() {
    }

    public MateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public MateriaPrima(Integer idMateriaPrima, short estado, String usuarioRegistro, Date fechaRegistro) {
        this.idMateriaPrima = idMateriaPrima;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
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

    public Collection<FormulaMateriaPrima> getFormulaMateriaPrimaCollection() {
        return formulaMateriaPrimaCollection;
    }

    public void setFormulaMateriaPrimaCollection(Collection<FormulaMateriaPrima> formulaMateriaPrimaCollection) {
        this.formulaMateriaPrimaCollection = formulaMateriaPrimaCollection;
    }

    public TipoMateriaPrima getTipoMateriaPrima() {
        return tipoMateriaPrima;
    }

    public void setTipoMateriaPrima(TipoMateriaPrima tipoMateriaPrima) {
        this.tipoMateriaPrima = tipoMateriaPrima;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateriaPrima != null ? idMateriaPrima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaPrima)) {
            return false;
        }
        MateriaPrima other = (MateriaPrima) object;
        if ((this.idMateriaPrima == null && other.idMateriaPrima != null) || (this.idMateriaPrima != null && !this.idMateriaPrima.equals(other.idMateriaPrima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MateriaPrima[idMateriaPrima=" + idMateriaPrima + "]";
    }

}
