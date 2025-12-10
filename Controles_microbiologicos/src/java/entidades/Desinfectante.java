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
@Table(name = "desinfectante")
@NamedQueries({
    @NamedQuery(name = "Desinfectante.findAll", query = "SELECT d FROM Desinfectante d"),
    @NamedQuery(name = "Desinfectante.findByIdDesinfectante", query = "SELECT d FROM Desinfectante d WHERE d.idDesinfectante = :idDesinfectante"),
    @NamedQuery(name = "Desinfectante.findByDescripcion", query = "SELECT d FROM Desinfectante d WHERE d.descripcion = :descripcion")})
public class Desinfectante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDesinfectante")
    private Integer idDesinfectante;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "desinfectante")
    private List<AnalisisPorArea> analisisPorAreaList;

    public Desinfectante() {
    }

    public Desinfectante(Integer idDesinfectante) {
        this.idDesinfectante = idDesinfectante;
    }

    public Desinfectante(Integer idDesinfectante, String descripcion) {
        this.idDesinfectante = idDesinfectante;
        this.descripcion = descripcion;
    }

    public Integer getIdDesinfectante() {
        return idDesinfectante;
    }

    public void setIdDesinfectante(Integer idDesinfectante) {
        this.idDesinfectante = idDesinfectante;
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
        hash += (idDesinfectante != null ? idDesinfectante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Desinfectante)) {
            return false;
        }
        Desinfectante other = (Desinfectante) object;
        if ((this.idDesinfectante == null && other.idDesinfectante != null) || (this.idDesinfectante != null && !this.idDesinfectante.equals(other.idDesinfectante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Desinfectante[idDesinfectante=" + idDesinfectante + "]";
    }

}
