/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author a.sistemas2
 */
@Entity
@Table(name = "area_muestrada")
@NamedQueries({
    @NamedQuery(name = "AreaMuestrada.findAll", query = "SELECT a FROM AreaMuestrada a"),
    @NamedQuery(name = "AreaMuestrada.findByIdAreaMuestrada", query = "SELECT a FROM AreaMuestrada a WHERE a.idAreaMuestrada = :idAreaMuestrada"),
    @NamedQuery(name = "AreaMuestrada.findByDescripcion", query = "SELECT a FROM AreaMuestrada a WHERE a.descripcion = :descripcion")})
public class AreaMuestrada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArea_Muestrada")
    private Integer idAreaMuestrada;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaMuestrada")
    private List<AnalisisPorArea> analisisPorAreaList;

    public AreaMuestrada() {
    }

    public AreaMuestrada(Integer idAreaMuestrada) {
        this.idAreaMuestrada = idAreaMuestrada;
    }

    public AreaMuestrada(Integer idAreaMuestrada, String descripcion) {
        this.idAreaMuestrada = idAreaMuestrada;
        this.descripcion = descripcion;
    }

    public Integer getIdAreaMuestrada() {
        return idAreaMuestrada;
    }

    public void setIdAreaMuestrada(Integer idAreaMuestrada) {
        this.idAreaMuestrada = idAreaMuestrada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AnalisisPorArea> getAnalisisPorAreaList() {
        return analisisPorAreaList;
    }

    public void setAnalisisPorAreaList(List<AnalisisPorArea> analisisPorAreaList) {
        this.analisisPorAreaList = analisisPorAreaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAreaMuestrada != null ? idAreaMuestrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaMuestrada)) {
            return false;
        }
        AreaMuestrada other = (AreaMuestrada) object;
        if ((this.idAreaMuestrada == null && other.idAreaMuestrada != null) || (this.idAreaMuestrada != null && !this.idAreaMuestrada.equals(other.idAreaMuestrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.AreaMuestrada[idAreaMuestrada=" + idAreaMuestrada + "]";
    }

}
