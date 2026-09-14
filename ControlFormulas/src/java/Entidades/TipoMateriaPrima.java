/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "tipo_materia_prima")
@NamedQueries({
    @NamedQuery(name = "TipoMateriaPrima.findAll", query = "SELECT t FROM TipoMateriaPrima t"),
    @NamedQuery(name = "TipoMateriaPrima.findByIdTipoMateriaPrima", query = "SELECT t FROM TipoMateriaPrima t WHERE t.idTipoMateriaPrima = :idTipoMateriaPrima"),
    @NamedQuery(name = "TipoMateriaPrima.findByNombreTipo", query = "SELECT t FROM TipoMateriaPrima t WHERE t.nombreTipo = :nombreTipo")})
public class TipoMateriaPrima implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_materia_prima")
    private Integer idTipoMateriaPrima;
    @Column(name = "nombre_tipo")
    private String nombreTipo;
    @OneToMany(mappedBy = "tipoMateriaPrima")
    private Collection<MateriaPrima> materiaPrimaCollection;

    public TipoMateriaPrima() {
    }

    public TipoMateriaPrima(Integer idTipoMateriaPrima) {
        this.idTipoMateriaPrima = idTipoMateriaPrima;
    }

    public Integer getIdTipoMateriaPrima() {
        return idTipoMateriaPrima;
    }

    public void setIdTipoMateriaPrima(Integer idTipoMateriaPrima) {
        this.idTipoMateriaPrima = idTipoMateriaPrima;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Collection<MateriaPrima> getMateriaPrimaCollection() {
        return materiaPrimaCollection;
    }

    public void setMateriaPrimaCollection(Collection<MateriaPrima> materiaPrimaCollection) {
        this.materiaPrimaCollection = materiaPrimaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoMateriaPrima != null ? idTipoMateriaPrima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMateriaPrima)) {
            return false;
        }
        TipoMateriaPrima other = (TipoMateriaPrima) object;
        if ((this.idTipoMateriaPrima == null && other.idTipoMateriaPrima != null) || (this.idTipoMateriaPrima != null && !this.idTipoMateriaPrima.equals(other.idTipoMateriaPrima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoMateriaPrima[idTipoMateriaPrima=" + idTipoMateriaPrima + "]";
    }

}
