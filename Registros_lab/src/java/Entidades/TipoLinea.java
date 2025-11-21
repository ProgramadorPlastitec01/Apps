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
@Table(name = "tipo_linea")
@NamedQueries({
    @NamedQuery(name = "TipoLinea.findAll", query = "SELECT t FROM TipoLinea t"),
    @NamedQuery(name = "TipoLinea.findByIdTipoLinea", query = "SELECT t FROM TipoLinea t WHERE t.idTipoLinea = :idTipoLinea"),
    @NamedQuery(name = "TipoLinea.findByNombre", query = "SELECT t FROM TipoLinea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoLinea.findByTipoRegistro", query = "SELECT t FROM TipoLinea t WHERE t.tipoRegistro = :tipoRegistro")})
public class TipoLinea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_linea")
    private Integer idTipoLinea;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo_registro")
    private String tipoRegistro;
    @OneToMany(mappedBy = "tipoLinea")
    private Collection<Parametro> parametroCollection;
    @OneToMany(mappedBy = "tipoLinea")
    private Collection<Linea> lineaCollection;

    public TipoLinea() {
    }

    public TipoLinea(Integer idTipoLinea) {
        this.idTipoLinea = idTipoLinea;
    }

    public Integer getIdTipoLinea() {
        return idTipoLinea;
    }

    public void setIdTipoLinea(Integer idTipoLinea) {
        this.idTipoLinea = idTipoLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Collection<Parametro> getParametroCollection() {
        return parametroCollection;
    }

    public void setParametroCollection(Collection<Parametro> parametroCollection) {
        this.parametroCollection = parametroCollection;
    }

    public Collection<Linea> getLineaCollection() {
        return lineaCollection;
    }

    public void setLineaCollection(Collection<Linea> lineaCollection) {
        this.lineaCollection = lineaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoLinea != null ? idTipoLinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoLinea)) {
            return false;
        }
        TipoLinea other = (TipoLinea) object;
        if ((this.idTipoLinea == null && other.idTipoLinea != null) || (this.idTipoLinea != null && !this.idTipoLinea.equals(other.idTipoLinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoLinea[idTipoLinea=" + idTipoLinea + "]";
    }

}
