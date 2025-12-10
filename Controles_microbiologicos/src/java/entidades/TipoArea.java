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
@Table(name = "tipo_area")
@NamedQueries({
    @NamedQuery(name = "TipoArea.findAll", query = "SELECT t FROM TipoArea t"),
    @NamedQuery(name = "TipoArea.findByIdTipoArea", query = "SELECT t FROM TipoArea t WHERE t.idTipoArea = :idTipoArea"),
    @NamedQuery(name = "TipoArea.findByDescripcion", query = "SELECT t FROM TipoArea t WHERE t.descripcion = :descripcion")})
public class TipoArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipo_Area")
    private Integer idTipoArea;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoArea")
    private List<AnalisisPorArea> analisisPorAreaList;

    public TipoArea() {
    }

    public TipoArea(Integer idTipoArea) {
        this.idTipoArea = idTipoArea;
    }

    public TipoArea(Integer idTipoArea, String descripcion) {
        this.idTipoArea = idTipoArea;
        this.descripcion = descripcion;
    }

    public Integer getIdTipoArea() {
        return idTipoArea;
    }

    public void setIdTipoArea(Integer idTipoArea) {
        this.idTipoArea = idTipoArea;
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
        hash += (idTipoArea != null ? idTipoArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoArea)) {
            return false;
        }
        TipoArea other = (TipoArea) object;
        if ((this.idTipoArea == null && other.idTipoArea != null) || (this.idTipoArea != null && !this.idTipoArea.equals(other.idTipoArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoArea[idTipoArea=" + idTipoArea + "]";
    }

}
