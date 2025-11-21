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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "tipo_parametro")
@NamedQueries({
    @NamedQuery(name = "TipoParametro.findAll", query = "SELECT t FROM TipoParametro t"),
    @NamedQuery(name = "TipoParametro.findByIdTipoParametro", query = "SELECT t FROM TipoParametro t WHERE t.idTipoParametro = :idTipoParametro"),
    @NamedQuery(name = "TipoParametro.findByNombre", query = "SELECT t FROM TipoParametro t WHERE t.nombre = :nombre")})
public class TipoParametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_parametro")
    private Integer idTipoParametro;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "tipoParametro")
    private Collection<Parametro> parametroCollection;

    public TipoParametro() {
    }

    public TipoParametro(Integer idTipoParametro) {
        this.idTipoParametro = idTipoParametro;
    }

    public Integer getIdTipoParametro() {
        return idTipoParametro;
    }

    public void setIdTipoParametro(Integer idTipoParametro) {
        this.idTipoParametro = idTipoParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Parametro> getParametroCollection() {
        return parametroCollection;
    }

    public void setParametroCollection(Collection<Parametro> parametroCollection) {
        this.parametroCollection = parametroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoParametro != null ? idTipoParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoParametro)) {
            return false;
        }
        TipoParametro other = (TipoParametro) object;
        if ((this.idTipoParametro == null && other.idTipoParametro != null) || (this.idTipoParametro != null && !this.idTipoParametro.equals(other.idTipoParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoParametro[idTipoParametro=" + idTipoParametro + "]";
    }

}
