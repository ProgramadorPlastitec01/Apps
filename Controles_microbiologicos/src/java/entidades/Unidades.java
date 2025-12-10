/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author a.sistemas2
 */
@Entity
@Table(name = "unidades")
@NamedQueries({
    @NamedQuery(name = "Unidades.findAll", query = "SELECT u FROM Unidades u"),
    @NamedQuery(name = "Unidades.findByIdUnidades", query = "SELECT u FROM Unidades u WHERE u.idUnidades = :idUnidades"),
    @NamedQuery(name = "Unidades.findByDescripcion", query = "SELECT u FROM Unidades u WHERE u.descripcion = :descripcion")})
public class Unidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUnidades")
    private Integer idUnidades;
    @Column(name = "Descripcion")
    private String descripcion;

    public Unidades() {
    }

    public Unidades(Integer idUnidades) {
        this.idUnidades = idUnidades;
    }

    public Integer getIdUnidades() {
        return idUnidades;
    }

    public void setIdUnidades(Integer idUnidades) {
        this.idUnidades = idUnidades;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidades != null ? idUnidades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidades)) {
            return false;
        }
        Unidades other = (Unidades) object;
        if ((this.idUnidades == null && other.idUnidades != null) || (this.idUnidades != null && !this.idUnidades.equals(other.idUnidades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Unidades[idUnidades=" + idUnidades + "]";
    }

}
