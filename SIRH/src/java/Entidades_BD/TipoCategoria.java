/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "tipo_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCategoria.findAll", query = "SELECT t FROM TipoCategoria t"),
    @NamedQuery(name = "TipoCategoria.findByIdTipoCategoria", query = "SELECT t FROM TipoCategoria t WHERE t.idTipoCategoria = :idTipoCategoria"),
    @NamedQuery(name = "TipoCategoria.findByNombre", query = "SELECT t FROM TipoCategoria t WHERE t.nombre = :nombre")})
public class TipoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_categoria")
    private Integer idTipoCategoria;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoCategoria")
    private Collection<Categoria> categoriaCollection;

    public TipoCategoria() {
    }

    public TipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public Integer getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Categoria> getCategoriaCollection() {
        return categoriaCollection;
    }

    public void setCategoriaCollection(Collection<Categoria> categoriaCollection) {
        this.categoriaCollection = categoriaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCategoria != null ? idTipoCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCategoria)) {
            return false;
        }
        TipoCategoria other = (TipoCategoria) object;
        if ((this.idTipoCategoria == null && other.idTipoCategoria != null) || (this.idTipoCategoria != null && !this.idTipoCategoria.equals(other.idTipoCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.TipoCategoria[ idTipoCategoria=" + idTipoCategoria + " ]";
    }
    
}
